package tech.thedumbdev.data;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import tech.thedumbdev.data.exceptions.ElasticStoreException;
import tech.thedumbdev.pojo.Log;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ElasticStore implements DataStore {
    private ElasticsearchClient client;
    private long timestamp;

    public ElasticStore(String hostUrl, String apiKey) throws ElasticStoreException {
        this.client = ElasticsearchClient.of(b -> b.host(hostUrl).apiKey(apiKey));
        this.timestamp = java.time.Instant.now().getEpochSecond();

        try {
            this.client.indices().create(c -> c.index(String.valueOf(this.timestamp)));
        } catch (IOException | ElasticsearchException e) {
            throw new ElasticStoreException("Unable to create index", e);
        }
    }

    @Override
    public void appendLog(Set<Log> logs) {
        try {
            for(Log log : logs) {
                IndexResponse response = this.client.index(i -> i
                        .index(String.valueOf(this.timestamp))
                        .document(log)
                );
            }
        } catch(ElasticsearchException | IOException e) {
            throw new RuntimeException("Failed to write logs", e);
        }
    }

    @Override
    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }
}
