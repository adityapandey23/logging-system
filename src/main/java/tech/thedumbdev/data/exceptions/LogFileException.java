package tech.thedumbdev.data.exceptions;

public class LogFileException extends FileStoreException {
    public LogFileException(String message, Throwable cause) {
        super(message, cause);
    }
}