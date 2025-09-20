package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore {
    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {
        try {
            File file = new File("./logs/test.log"); // This is not autocloseable, as it doesn't need to be closed lol
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
