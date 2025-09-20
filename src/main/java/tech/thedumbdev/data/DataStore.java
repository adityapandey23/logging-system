package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

// This is to make sure that anyone can implement their own datastore
public interface DataStore {
    void addLog(Log log);
    void appendLog(Collection<Log> logs) throws TimeoutException; // Might change later
}
