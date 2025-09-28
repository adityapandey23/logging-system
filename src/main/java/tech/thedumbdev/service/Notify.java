package tech.thedumbdev.service;

import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;

import java.util.HashMap;
import java.util.Map;

public class Notify {
    private Map<String, Integer> errorCount;

    private final Map<Severity, Integer> EMAIL_THRESHOLDS = Map.of(
            Severity.LOW, 20,
            Severity.MEDIUM, 15,
            Severity.HIGH, 10,
            Severity.CRITICAL, 5
    );

    public Notify() {
        this.errorCount = new HashMap<>();
        for(Severity severity : Severity.values()) {
            this.errorCount.put(severity.toString(), 0);
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

    private void sendEmail(Severity severity) {
        // TODO: Add publish event to the SNS which can then use Lambda and SES to send the email
        System.out.println("I'll send email don't worry");
    }
}
