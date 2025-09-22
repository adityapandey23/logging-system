package tech.thedumbdev.data;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import tech.thedumbdev.pojo.Log;

import java.util.Set;
import java.util.concurrent.TimeoutException;

public class ElasticStore implements DataStore {

    ElasticsearchClient client;

    ElasticStore(String hostUrl, String apiKey) {
        this.client = ElasticsearchClient.of(b -> b.host(hostUrl).apiKey(apiKey));
    }

    @Override
    public void appendLog(Set<Log> logs) throws TimeoutException {
        System.out.println("It worked"); // TODO: Implement
    }
}
