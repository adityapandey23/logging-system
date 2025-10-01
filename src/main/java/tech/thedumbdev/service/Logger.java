package tech.thedumbdev.service;

import tech.thedumbdev.data.DataStore;
import tech.thedumbdev.data.FileStore;
import tech.thedumbdev.data.exceptions.FileStoreException;
import tech.thedumbdev.enums.Severity;
import tech.thedumbdev.pojo.Log;

import java.io.IOException;
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
    private Notify notify;

    private Logger(DataStore dataStore, Notify notify) {
        try {
            this.dataStore = (dataStore == null) ? new FileStore() : dataStore; // Defaults to FileStore
            this.logSet = new HashSet<>();
            this.logProcessingQueue = new ArrayDeque<>();
            this.service = Executors.newFixedThreadPool(10);
            this.notify = notify;

        } catch (FileStoreException e) {
            throw new RuntimeException("Failed to initialize the logger service", e);
        }
    }

    public static void initLogger(DataStore dataStore, Notify notify) {
        logger = new Logger(dataStore, notify);
    }

    public static Logger getLogger() {
        return logger;
    }

    public void addLog(Log log) {
        synchronized (Logger.class) {
            if (logger == null) {
                throw new RuntimeException("Logger has not been initialized yet");
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

            if(
                    this.notify != null &&
                            log.getSeverity() != Severity.UNDEFINED &&
                            log.getSeverity() != Severity.WARN
            ) {
                notify.addError(log);
            }

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
                } catch (Exception e) {
                    throw new RuntimeException("Unable to append logs", e);
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

    public void shutdown() {
        try {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) this.service;
            executor.shutdown();
            boolean isCompleted = executor.awaitTermination(10, TimeUnit.SECONDS);
            if(!isCompleted) {
                throw new RuntimeException("Executor shutdown timed out");
            }

            dataStore.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
