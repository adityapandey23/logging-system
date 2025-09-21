package tech.thedumbdev.service;
// !ALERT: This supports only one type of logger at a time

import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.FileStore;
import tech.thedumbdev.pojo.Log;
import tech.thedumbdev.utils.DeepCopy;

import java.util.*;
import java.util.concurrent.*;

public class Logger {
    private static volatile Logger logger;
    private static DataStore dataStore;
    private static Set<Log> logSet;
    private static Queue<Set<Log>> logProcessingQueue;
    private static ExecutorService service;

    Logger() {}

    // If you don't define what datastore you want, we'll give you FileStore by default
    public static void initInstance(DataStore dataStore) {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                    Logger.dataStore = dataStore;
                    Logger.logSet = new HashSet<>();
                    Logger.logProcessingQueue = new LinkedBlockingQueue<>();
                    Logger.service = Executors.newFixedThreadPool(10);
                }
            }
        }
    }

    public static void addLog(Log log) throws RuntimeException { // TODO: Implement this in static fashion
        if (logger == null) {
            throw new RuntimeException("logger is null, please call Logger.initInstance(datastore)");
        }

        synchronized (Logger.class) {
            long timestamp = java.time.Instant.now().toEpochMilli();
            Thread currentThread = Thread.currentThread();
            StackTraceElement[] stackTrace = currentThread.getStackTrace();

            StringBuilder stackTraceBuilder = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTrace) {
                stackTraceBuilder.append(stackTraceElement.toString()).append("\n");
            }

            String stackTraceString = stackTraceBuilder.toString();

            log.setTimestamp(timestamp);
            log.setThreadId(Long.toString(currentThread.getId()));
            log.setThreadName(currentThread.getName());
            log.setStackTrace(stackTraceString);

            put(logSet, log);
        }
    }

    public static void appendLog() throws RuntimeException { // TODO: Implement this in static fashion
        if (logger == null) {
            throw new RuntimeException("logger is null, please call Logger.initInstance(datastore)");
        }

        synchronized (Logger.class) {
            try {
                Set<Log> logSetCopy = DeepCopy.deepCopy(logSet); // To avoid shallow copy error
                put(logProcessingQueue, logSetCopy);
                flushLogSet();
                service.submit(() -> {
                    try {
                        dataStore.appendLog(logProcessingQueue.peek());
                        logProcessingQueue.poll();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void flushLogSet() {
        logSet.clear();
    }

    private static void flushLogProcessingQueue() {
        logProcessingQueue.clear();
    }

    private static <T> void put(Collection<T> collection, T item) {
        collection.add(item);
    }

    private static void deleteLogs() {
        // Figure out the size logic
    }

    public static void close() {
        // make sure the processing queue is empty
        // Kill the thread pool
        // And disconnect from the elastic search instance
    }

}
