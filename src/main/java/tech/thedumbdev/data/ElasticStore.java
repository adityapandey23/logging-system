package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

// TODO: Implement Elasticsearch as a datastore (Maybe we have to do something on the query processor side as well)
public class ElasticStore implements DataStore {
    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {}
}
