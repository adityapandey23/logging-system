package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore {
    private long timestamp;

    public FileStore() {
        this.timestamp = java.time.Instant.now().getEpochSecond();
    }

    @Override
    public void appendLog(Set<Log> logs) throws TimeoutException {
        try {
            File file = new File("./logs/" + this.timestamp + ".log");
            try (
                    FileOutputStream outputBuffer = new FileOutputStream(file, false);
                    ObjectOutputStream objectOut = new ObjectOutputStream(outputBuffer);
                    ) {
                System.out.println("Yes I reached here");
                for(Log log : logs) {
                    objectOut.writeObject(log);
                }
                objectOut.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
