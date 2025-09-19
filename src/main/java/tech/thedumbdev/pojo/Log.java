package tech.thedumbdev.pojo;

import tech.thedumbdev.enums.Severity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
    private String data;
    private Timestamp timestamp;
    private String threadId;
    private String threadName;
    private Severity severity;
    private String stackTrace;

    public Log(String data) {
        this.data = data;
        // TODO: Figure out how to get thread information while,
        //  the user is adding or appending any Log and add stack trace
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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
