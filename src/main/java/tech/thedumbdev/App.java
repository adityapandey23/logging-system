package tech.thedumbdev;

import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.ElasticStore;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.service.Logger;
import tech.thedumbdev.service.Notify;


public class App {
    public static void main(String[] args) {
        Notify notify = new Notify();
        Logger.initLogger(null, notify);
        Logger instance = Logger.getLogger();

        instance.addLog(new Log("This is log_1 odd", Severity.CRITICAL));
        instance.addLog(new Log("This is log_2 even", Severity.CRITICAL));
        instance.addLog(new Log("This is log_3 odd", Severity.CRITICAL));

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("This is log_4 even", Severity.CRITICAL));
        instance.addLog(new Log("This is log_5 odd", Severity.CRITICAL));
        instance.addLog(new Log("This is log_6 even", Severity.UNDEFINED));

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.appendLog();


        instance.addLog(new Log("This is log_7 odd", Severity.WARN));
        instance.addLog(new Log("This is log_8 even", Severity.LOW));
        instance.addLog(new Log("This is log_9 odd", Severity.MEDIUM));

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.addLog(new Log("This is log_10 even", Severity.HIGH));
        instance.addLog(new Log("This is log_11 odd", Severity.CRITICAL));
        instance.addLog(new Log("This is log_12 even", Severity.UNDEFINED));

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance.appendLog();
        instance.shutdown();


//        DataStore dataStore = new ElasticStore("http://localhost:9200", "");
//        Logger.initLogger(dataStore);
//        Logger instance = Logger.getLogger();
//
//        instance.addLog(new Log("This is log_1 odd", Severity.WARN));
//        instance.addLog(new Log("This is log_2 even", Severity.LOW));
//        instance.addLog(new Log("This is log_3 odd", Severity.MEDIUM));
//
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        instance.addLog(new Log("This is log_4 even", Severity.HIGH));
//        instance.addLog(new Log("This is log_5 odd", Severity.CRITICAL));
//        instance.addLog(new Log("This is log_6 even", Severity.UNDEFINED));
//
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        instance.appendLog();
//
//
//        instance.addLog(new Log("This is log_7 odd", Severity.WARN));
//        instance.addLog(new Log("This is log_8 even", Severity.LOW));
//        instance.addLog(new Log("This is log_9 odd", Severity.MEDIUM));
//
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        instance.addLog(new Log("This is log_10 even", Severity.HIGH));
//        instance.addLog(new Log("This is log_11 odd", Severity.CRITICAL));
//        instance.addLog(new Log("This is log_12 even", Severity.UNDEFINED));
//
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        instance.appendLog();
//        instance.shutdown();
    }
}
