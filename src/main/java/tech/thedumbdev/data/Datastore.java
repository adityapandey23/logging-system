package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.concurrent.TimeoutException;

public interface Datastore {
    void addLog(Log log);
    void appendLog() throws TimeoutException; // Might change later
}
