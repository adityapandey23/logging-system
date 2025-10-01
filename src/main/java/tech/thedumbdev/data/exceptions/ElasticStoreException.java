package tech.thedumbdev.data.exceptions;

public class ElasticStoreException extends DataStoreException {
    public ElasticStoreException(String message) {
        super(message);
    }
    public ElasticStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
