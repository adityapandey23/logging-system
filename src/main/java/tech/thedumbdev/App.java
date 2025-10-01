package tech.thedumbdev;

import software.amazon.awssdk.regions.Region;
import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.ElasticStore;
import tech.thedumbdev.data.FileStore;
import tech.thedumbdev.data.exceptions.ElasticStoreException;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.service.Logger;
import tech.thedumbdev.service.Notify;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        // ============================================================
        // Example 1: FileStore without Notification service
        // ============================================================
        /*
        Logger.initLogger(null, null);
        Logger instance = Logger.getLogger();

        instance.addLog(new Log("FileStore: Log 1", Severity.LOW));
        instance.addLog(new Log("FileStore: Log 2", Severity.MEDIUM));
        instance.addLog(new Log("FileStore: Log 3", Severity.HIGH));

        // This is not necessary, just added this so that we can see a time difference in between logs
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("FileStore: Log 4", Severity.CRITICAL));
        instance.addLog(new Log("FileStore: Log 5", Severity.WARN));

        instance.appendLog();
        instance.shutdown();
        */

        // ============================================================
        // Example 2: FileStore with Notification service
        // ============================================================
        /*
        Scanner sc = new Scanner(System.in);

        // Notification service init
        System.out.println("Enter Region");
        String region = sc.nextLine();
        System.out.println("Enter Topic ARN");
        String topicARN = sc.nextLine();
        System.out.println("Enter Email");
        String email = sc.nextLine();
        System.out.println("Enter Access Key");
        String accessKey = sc.nextLine();
        System.out.println("Enter Secret Key");
        String secretKey = sc.nextLine();
        Notify notify = new Notify(Region.of(region), topicARN, email, accessKey, secretKey);

        Logger.initLogger(null, notify);
        Logger instance = Logger.getLogger();

        instance.addLog(new Log("FileStore with Notify: Log 1", Severity.CRITICAL));
        instance.addLog(new Log("FileStore with Notify: Log 2", Severity.HIGH));
        instance.addLog(new Log("FileStore with Notify: Log 3", Severity.MEDIUM));

        // This is not necessary, just added this so that we can see a time difference in between logs
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("FileStore with Notify: Log 4", Severity.LOW));
        instance.addLog(new Log("FileStore with Notify: Log 5", Severity.WARN));

        instance.appendLog();
        instance.shutdown();
        */

        // ============================================================
        // Example 3: ElasticStore without Notification service
        // ============================================================
        /*
        try {
            DataStore dataStore = new ElasticStore("http://localhost:9200", "");
            Logger.initLogger(dataStore, null);
        } catch (ElasticStoreException e) {
            throw new RuntimeException("Failed to create elastic store connection");
        }

        Logger instance = Logger.getLogger();

        instance.addLog(new Log("ElasticStore: Log 1", Severity.WARN));
        instance.addLog(new Log("ElasticStore: Log 2", Severity.LOW));
        instance.addLog(new Log("ElasticStore: Log 3", Severity.MEDIUM));

        // This is not necessary, just added this so that we can see a time difference in between logs
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("ElasticStore: Log 4", Severity.HIGH));
        instance.addLog(new Log("ElasticStore: Log 5", Severity.CRITICAL));

        instance.appendLog();
        instance.shutdown();
        */

        // ============================================================
        // Example 4: ElasticStore with Notification service
        // ============================================================
        /*
        Scanner sc = new Scanner(System.in);

        // Notification service init
        System.out.println("Enter Region");
        String region = sc.nextLine();
        System.out.println("Enter Topic ARN");
        String topicARN = sc.nextLine();
        System.out.println("Enter Email");
        String email = sc.nextLine();
        System.out.println("Enter Access Key");
        String accessKey = sc.nextLine();
        System.out.println("Enter Secret Key");
        String secretKey = sc.nextLine();
        Notify notify = new Notify(Region.of(region), topicARN, email, accessKey, secretKey);

        try {
            DataStore dataStore = new ElasticStore("http://localhost:9200", "");
            Logger.initLogger(dataStore, notify);
        } catch (ElasticStoreException e) {
            throw new RuntimeException("Failed to create elastic store connection");
        }

        Logger instance = Logger.getLogger();

        instance.addLog(new Log("ElasticStore with Notify: Log 1", Severity.CRITICAL));
        instance.addLog(new Log("ElasticStore with Notify: Log 2", Severity.HIGH));
        instance.addLog(new Log("ElasticStore with Notify: Log 3", Severity.MEDIUM));

        // This is not necessary, just added this so that we can see a time difference in between logs
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("ElasticStore with Notify: Log 4", Severity.LOW));
        instance.addLog(new Log("ElasticStore with Notify: Log 5", Severity.WARN));

        instance.appendLog();
        instance.shutdown();
        */



        // ---------------------------------------------------------------------
        // NOTE:
        // - All four examples are intentionally commented out. Uncomment the block
        // you want to run (only one at a time is recommended for demos).
        // - Replace placeholder AWS credentials / ARNs / endpoints with real values
        // before enabling notification-enabled examples.
        // ---------------------------------------------------------------------
    }
}
