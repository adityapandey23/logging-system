package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface DataStore {
    void appendLog(Collection<Log> logs) throws TimeoutException;
}
