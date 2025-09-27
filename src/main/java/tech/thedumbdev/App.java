package tech.thedumbdev;

import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.service.Logger;

import java.io.File;

public class App {
    public static void main(String[] args) {
        Logger.initLogger(null);
        Logger instance = Logger.getLogger();

        instance.addLog(new Log("This is log 1", Severity.WARN));
        instance.addLog(new Log("This is log 2", Severity.LOW));
        instance.addLog(new Log("This is log 3", Severity.MEDIUM));
        instance.addLog(new Log("This is log 4", Severity.HIGH));
        instance.addLog(new Log("This is log 5", Severity.CRITICAL));

        instance.appendLog();

        instance.addLog(new Log("This is log 6", Severity.WARN));
        instance.addLog(new Log("This is log 7", Severity.LOW));
        instance.addLog(new Log("This is log 8", Severity.MEDIUM));
        instance.addLog(new Log("This is log 9", Severity.HIGH));
        instance.addLog(new Log("This is log 10", Severity.CRITICAL));

        instance.appendLog();
        instance.shutdown();
    }
}
