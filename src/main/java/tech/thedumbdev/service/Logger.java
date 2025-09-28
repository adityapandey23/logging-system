package tech.thedumbdev.service;

import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.FileStore;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Logger {
    private static volatile Logger logger;
    private DataStore dataStore;
    private Set<Log> logSet;
    private Queue<Set<Log>> logProcessingQueue;
    private ExecutorService service;

    private Logger(DataStore dataStore) {
        this.dataStore = (dataStore == null) ? new FileStore() : dataStore;
        this.logSet = new HashSet<>();
        this.logProcessingQueue = new ArrayDeque<>();
        this.service = Executors.newFixedThreadPool(10);
    }

    public static void initLogger(DataStore dataStore) {
        logger = new Logger(dataStore);
    }

    public static Logger getLogger() {
        return logger;
    }

    public void addLog(Log log) {
        synchronized (Logger.class) {
            if (logger == null) {
                throw new IllegalStateException("Logger has not been initialized yet");
            }
            long timestamp = java.time.Instant.now().getEpochSecond();
            Thread currentThread = Thread.currentThread();
            StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

            StringBuilder stringBuilder = new StringBuilder();
            for(StackTraceElement stackTraceElement : stackTraceElements) {
                stringBuilder.append(stackTraceElement.toString()).append("\n");
            }

            log.setTimestamp(timestamp);
            log.setThreadName(currentThread.getName());
            log.setThreadId(Long.toString(currentThread.getId()));
            log.setStackTrace(stringBuilder.toString());
            log.setSeverity((log.getSeverity() == null) ? Severity.UNDEFINED : log.getSeverity());
            // Should call the service if the logs is of High or Critical Severity

            logSet.add(log);
        }
    }

    public void appendLog() {
        synchronized (Logger.class) {
            if (logger == null) {
                throw new IllegalStateException("Logger has not been initialized yet");
            }

            Set<Log> logSetCopy = logSet.stream().map(Log::new).collect(Collectors.toSet()); // Deep copy
            logProcessingQueue.add(logSetCopy);
            flushLogSet();

            service.submit(() -> {
                Set<Log> logs = logProcessingQueue.peek();
                logProcessingQueue.remove();
                try {
                    dataStore.appendLog(logs);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void flushLogSet() {
        logSet.clear();
    }

    private void flushLogProcessingQueue() {
        logProcessingQueue.clear();
    }

    private void deleteLog() {
        // File size based
    }

    public void shutdown() {
        try {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) this.service;
            executor.shutdown();
            boolean isCompleted = executor.awaitTermination(10, TimeUnit.SECONDS);
            if(!isCompleted) {
                throw new RuntimeException("Executor shutdown timed out");
            }

            if (dataStore instanceof  FileStore) {
                ((FileStore) dataStore).fileClose();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
