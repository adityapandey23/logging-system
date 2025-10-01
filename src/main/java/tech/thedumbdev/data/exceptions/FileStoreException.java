package tech.thedumbdev.data.exceptions;

public class FileStoreException extends DataStoreException {
    public FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStoreException(String message) {
        super(message);
    }
}