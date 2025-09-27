package tech.thedumbdev.pojo;

import tech.thedumbdev.enums.Severity;

import java.io.Serial;
import java.io.Serializable;

public class Log implements Serializable {
    @Serial
    private final static long serialVersionUID = 1234L;

    private String data;
    private long timestamp;
    private String threadId;
    private String threadName;
    private Severity severity;
    private String stackTrace;

    public Log() {}

    public Log(String data) {
        this.data = data;
        this.severity = Severity.UNDEFINED; // Default severity
    }

    public Log(String data, Severity severity) {
        this.data = data;
        this.severity = severity;
    }

    // Copy Constructor
    public Log(Log other) {
        this.data = other.data;
        this.timestamp = other.timestamp;
        this.threadId = other.threadId;
        this.threadName = other.threadName;
        this.severity = other.severity;
        this.stackTrace = other.stackTrace;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
