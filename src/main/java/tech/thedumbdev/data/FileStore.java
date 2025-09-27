package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.io.*;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore {
    private long timestamp;
    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    public FileStore() {
        this.timestamp = java.time.Instant.now().getEpochSecond();
        this.file = new File("./logs/" + this.timestamp + ".log");
        try {
            this.fos = new FileOutputStream(file, true);
            this.oos = new ObjectOutputStream(this.fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void appendLog(Set<Log> logs) throws TimeoutException {
        try {
            for(Log log: logs) {
                System.out.println(log.toString());
                this.oos.writeObject(log);
            }
            // Remove element
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fileClose() throws IOException { // I KNOW THIS IS NOT CLOSED
        this.oos.flush();
        this.oos.close();
        this.fos.close();
    }
}
