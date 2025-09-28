package tech.thedumbdev.data;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import tech.thedumbdev.pojo.Log;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class ElasticStore implements DataStore {
    private ElasticsearchClient client;
    private long timestamp;

    public ElasticStore(String hostUrl, String apiKey) {
        this.client = ElasticsearchClient.of(b -> b.host(hostUrl).apiKey(apiKey));
        this.timestamp = java.time.Instant.now().getEpochSecond();
        try {
            this.client.indices().create(c -> c.index(String.valueOf(this.timestamp)));
            // curl -sS 'http://localhost:9200/_cat/indices?v'
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void appendLog(Set<Log> logs) throws Exception {
        for(Log log : logs) {
            try {
                IndexResponse response = this.client.index(i -> i
                        .index(String.valueOf(this.timestamp))
                        .document(log)
                );
                System.out.println("Response: " + response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void connectionClose() throws IOException {
        this.client.close();
    }
}
