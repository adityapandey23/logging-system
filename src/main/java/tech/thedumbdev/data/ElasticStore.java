package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

// TODO: Implement Elasticsearch as a datastore
public class ElasticStore implements DataStore {
    @Override
    public void addLog(Log log) {}

    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {} // Make this more descriptive
}
