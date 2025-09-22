package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Set;
import java.util.concurrent.TimeoutException;

public interface DataStore {
    void appendLog(Set<Log> logs) throws TimeoutException;
}
