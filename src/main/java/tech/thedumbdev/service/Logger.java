package tech.thedumbdev.service;

import tech.thedumbdev.data.Datastore;
import tech.thedumbdev.pojo.Log;

import java.util.Queue;
import java.util.Set;

public class Logger {
    private static Logger logger = null;
    private Datastore filestore;
    private Set<Log> logTrackSet; // Capacity
    private Queue<Set<Log>> logrocessingQueue; // Capacity
    // should use Semaphore

    public static Logger getInstance() {
        if (logger == null) {
            return new Logger();
        }
        return logger;
    }

    // We'll handle the exceptions here
    // The ones that are caused while doing operations on the Datastore
    public void addLog(Log log) {} // Add timestamp and thread information
    public void appendLog() {}
    private void flushLogProcessingQueue() {}
    private void put() {} // Will make this generic
    private void deleteLog() {};
}
