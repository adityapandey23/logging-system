package tech.thedumbdev;

import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.service.Logger;
/**
 * Hello hooman
 */
public class App {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.addLog(new Log("This is a test log 1"));
        logger.addLog(new Log("This is a test log 2"));
        logger.addLog(new Log("This is a test log 3"));
        logger.addLog(new Log("This is a test log 4"));
        logger.addLog(new Log("This is a test log 5"));
        logger.appendLog();
        try {
            System.out.println("Imma sleep");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Good morning fellas");

    }
}
