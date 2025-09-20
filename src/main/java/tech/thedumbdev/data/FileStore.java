package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore {
    @Override
    public void addLog(Log log) {}

    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {

    }
}
