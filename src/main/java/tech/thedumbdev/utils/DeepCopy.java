package tech.thedumbdev.utils;

import java.io.*;

public class DeepCopy {
    @SuppressWarnings("unchecked") // This is for line 21
    public static <T> T deepCopy(T original) // IDK whether I should put Serializable here or not
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
