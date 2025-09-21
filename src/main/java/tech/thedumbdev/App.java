package tech.thedumbdev;

import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.service.Logger;
/**
 * Hello hooman
 */
public class App {
    public static void main(String[] args) {
        Logger.initInstance(null);
        Logger.addLog(new Log("This is log 1", Severity.WARN));
        Logger.addLog(new Log("This is log 2", Severity.LOW));
        Logger.addLog(new Log("This is log 3", Severity.MEDIUM));
        Logger.addLog(new Log("This is log 4", Severity.HIGH));
        Logger.addLog(new Log("This is log 5", Severity.CRITICAL));

        // Giving some time to finish the I/O
        try {
            System.out.println("Imma sleep");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Good morning fellas");

    }
}
