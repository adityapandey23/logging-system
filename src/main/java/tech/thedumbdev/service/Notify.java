package tech.thedumbdev.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notify {
    private Map<String, Integer> errorCount;
    private SnsClient snsClient;
    private String topicARN;
    private String email;

    private final Map<Severity, Integer> EMAIL_THRESHOLDS = Map.of(
            Severity.LOW, 20,
            Severity.MEDIUM, 15,
            Severity.HIGH, 10,
            Severity.CRITICAL, 5
    );
    /*
        - In this, we'll only send notification once we go beyond a certain number of error for a particular severity
        - For low severity we expect at least 20 errors before we send an email notification
        - For medium severity we expect at least 15 errors before we send an email notification
        - For high severity we expect at least 10 errors before we send an email notification
        - For critical severity we expect at least 5 errors before we send an email notification
     */

    public Notify(Region region, String topicARN, String email, String accessKey, String secretKey) {
        this.errorCount = new HashMap<>();
        for(Severity severity : Severity.values()) {
            this.errorCount.put(severity.toString(), 0);
        }

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        this.snsClient = SnsClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        this.topicARN = topicARN;
        this.email = email;
    }

    private void sendEmail(Severity severity) {
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());

        String subject = String.format("ALERT: %s severity detected in your application", severity.toString());

        String jsonPayload = String.format("{ \"timestamp\": \"%s\" }", timestamp);

        System.out.println(jsonPayload);


        Map<String, MessageAttributeValue> attributes = new HashMap<>();

        // Just in case we want to do filtering based on severity on the notification service side i.e. SNS
        attributes.put(
                "severity",
                MessageAttributeValue
                        .builder()
                        .dataType("String")
                        .stringValue(severity.getValue())
                        .build()
        );

        attributes.put("to",
                MessageAttributeValue.builder()
                        .dataType("String")
                        .stringValue(this.email)
                        .build()
        );

        attributes.put("subject",
                MessageAttributeValue.builder()
                        .dataType("String")
                        .stringValue(subject)
                        .build()
        );

        try {
            PublishRequest req = PublishRequest.builder()
                    .topicArn(this.topicARN)
                    .message(jsonPayload)
                    .messageAttributes(attributes)
                    .build();

            PublishResponse res = snsClient.publish(req);
            System.out.println("SNS message published " + res.messageId() + " for severity " + severity);
        } catch (Exception e) {
            throw new RuntimeException("Unable to publish message on Simple Notification Service", e);
        }
    }

    public void addError(Log error) {
        Severity severity = error.getSeverity();
        String key = severity.toString();
        int count = this.errorCount.get(key) + 1;
        this.errorCount.put(key, count);
        int threshold = EMAIL_THRESHOLDS.get(severity);

        if (count >= threshold) {
            sendEmail(severity);
            this.errorCount.put(key, count % threshold);
        }
    }
}
