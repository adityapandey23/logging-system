package tech.thedumbdev.data.exceptions;

public class DirectoryCreationException extends FileStoreException {
    public DirectoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryCreationException(String message) {
        super(message);
    }
}