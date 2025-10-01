package tech.thedumbdev.data;

import tech.thedumbdev.data.exceptions.DirectoryCreationException;
import tech.thedumbdev.data.exceptions.FileStoreException;
import tech.thedumbdev.data.exceptions.LogFileException;
import tech.thedumbdev.pojo.Log;

import java.io.*;
import java.util.Set;

public class FileStore implements DataStore {
    private long timestamp;
    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    public FileStore() throws FileStoreException {
        this.timestamp = java.time.Instant.now().getEpochSecond();

        String home = System.getProperty("user.home");
        if(home == null || home.isEmpty()) {
            throw new FileStoreException("Unable to determine home directory");
        }

        String directoryPath = home + File.separator + "logs";
        File directory = new File(directoryPath);

        if(!directory.exists() || !directory.isDirectory()) {
            boolean result = directory.mkdir();
            if(!result) {
                throw new DirectoryCreationException("Unable to create directory");
            }
        }

        this.file = new File(directoryPath + File.separator + this.timestamp + ".log");
        try {
            this.fos = new FileOutputStream(file, true);
            this.oos = new ObjectOutputStream(this.fos);
        } catch (IOException | SecurityException e) {
            throw new LogFileException("Failed to initialize file store", e);
        }
    }

    @Override
    public synchronized void appendLog(Set<Log> logs) {
        try {
            for(Log log: logs) {
                System.out.println(log.toString());
                this.oos.writeObject(log);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to write log", e);
        }
    }

    @Override
    public void close() {
        try {
            this.oos.flush();
            this.oos.close();
            this.fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close file store", e);
        }
    }
}