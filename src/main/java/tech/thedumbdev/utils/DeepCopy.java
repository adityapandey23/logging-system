package tech.thedumbdev.utils;

import java.io.*;

public class DeepCopy {
    @SuppressWarnings("unchecked") // This is for line 21
    public static <T /* extends Serializable // TODO: Make this serialable*/ > T deepCopy(T original)
            throws IOException, ClassNotFoundException {

        try (
                ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
                ObjectOutputStream objectOut = new ObjectOutputStream(outputBuffer)
        ) {
            objectOut.writeObject(original);
            objectOut.flush();

            try (
                    ByteArrayInputStream inputBuffer = new ByteArrayInputStream(outputBuffer.toByteArray());
                    ObjectInputStream objectIn = new ObjectInputStream(inputBuffer)
            ) {
                return (T) objectIn.readObject();
            }
        }
    }
}
