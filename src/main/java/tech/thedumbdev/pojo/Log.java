package tech.thedumbdev.pojo;

import tech.thedumbdev.enums.Severity;

import java.io.Serializable;

public class Log implements Serializable {
    private String data;
    private long timestamp;
    private String threadId;
    private String threadName;
    private Severity severity;
    private String stackTrace;

    public Log() {}

    public Log(String data) {
        this.data = data;
    }

    public Log(String data, Severity severity) { // By default, we'll have the UNDEFINED
        this.data = data;
        this.severity = severity;
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
