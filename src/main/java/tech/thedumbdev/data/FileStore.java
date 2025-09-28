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
        String home = System.getProperty("user.home");
        String directoryPath = home + File.separator + "logs";
        File directory = new File(directoryPath);

        if(!directory.exists() || !directory.isDirectory()) {
            boolean result = directory.mkdir();
            if(!result) {
                throw new RuntimeException("Unable to create directory");
            }
        }

        // this is the actual file
        this.file = new File(directoryPath + File.separator + this.timestamp + ".log");
        try {
            this.fos = new FileOutputStream(file, true);
            this.oos = new ObjectOutputStream(this.fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void appendLog(Set<Log> logs) throws Exception { // Will be an io exception
        try {
            for(Log log: logs) {
                System.out.println(log.toString());
                this.oos.writeObject(log);
            }
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
