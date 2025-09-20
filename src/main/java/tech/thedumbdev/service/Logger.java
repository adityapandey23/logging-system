package tech.thedumbdev.service;

import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.FileStore;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.utils.DeepCopy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Logger {
    private static Logger logger = null;
    private DataStore filestore = new FileStore(); // TODO: Replace this with the DataStore interface
    private Set<Log> logTrackSet = new HashSet<>();
    private Queue<Set<Log>> logProcessingQueue = new ArrayDeque<>();
    ExecutorService service = Executors.newFixedThreadPool(10);

    public static Logger getInstance() {
        if (logger == null) {
            return new Logger();
        }
        return logger;
    }

    public void addLog(Log log) { // Could make this in Strategy pattern like, addLogMap, addLogSet etc
        synchronized (Logger.class) { // One operation at a time
            Timestamp timestamp = new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            Thread currentThread = Thread.currentThread();
            StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

            StringBuilder builder = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                builder.append(stackTraceElement.toString()).append("\n");
            }

            String stackTraceString = builder.toString();

            log.setTimestamp(timestamp);
            log.setThreadId(Long.toString(currentThread.getId()));
            log.setThreadName(currentThread.getName());
            log.setStackTrace(stackTraceString);
            log.setSeverity(Severity.UNDEFINED); // As the other one wasn't working

            put(logTrackSet, log);
        }
    }

    public void appendLog() {
        // Get the logs from the set
        // Put that into the queue
        synchronized (Logger.class) {
            try {
                Set<Log> logTrackSetCopied = DeepCopy.deepCopy(logTrackSet);
                put(logProcessingQueue, logTrackSetCopied); // Pushing all the logs into the queue
                flushLogTrackSet(); // Shallow copy error, the set from the queue will also get erased
                service.submit(() -> {
                    // Perform storing it into the Datastore
                    try {
                        filestore.appendLog(logProcessingQueue.poll());
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e); // Will handle this differently
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void flushLogTrackSet() {
        logTrackSet.clear();
    }

    private void flushLogProcessingQueue() {
        logProcessingQueue.clear();
    }

    private <T> void put(Collection<T> collection, T item) {
        collection.add(item);
    }

    private void deleteLog() {};

}
