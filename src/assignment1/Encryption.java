package assignment1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Encryption {

    private static void encryptOrDecryptFile(EncryptionType type, File file, String key, String outputName, boolean encrypt) throws IOException {
        File out = new File(outputName + ".txt");
        if (file.exists()) {
            FileOutputStream outputStream = new FileOutputStream(out);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            if (type == EncryptionType.SUBSTITUTION) {
                byte keyByte = (byte) ((Character.digit(key.charAt(0), 16) << 4) + Character.digit(key.charAt(1), 16));
                byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i] ^ keyByte);
                }
                outputStream.write(bytes);
                outputStream.flush();
            } else if (type == EncryptionType.TRANSPOSITION) {
                String[] cut = key.split("");
                int[] keyArray = new int[4];
                for (int i = 0; i < keyArray.length; i++) {
                    keyArray[i] = Integer.valueOf(cut[i]) - 1;
                }
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                char[] characters = new char[4];
                char[] finalCharacters = new char[4];
                int size = fileReader.read(characters);
                while (size != -1) {
                    for (int i = 0; i < keyArray.length; i++) {
                        if (encrypt) {
                            finalCharacters[i] = characters[keyArray[i]];
                        } else {
                            finalCharacters[keyArray[i]] = characters[i];
                        }
                    }
                    writer.write(finalCharacters);
                    characters = new char[4];
                    finalCharacters = new char[4];
                    size = fileReader.read(characters);
                }
                fileReader.close();
                writer.flush();
            }
            writer.close();
            outputStream.close();
        } else {
            throw new FileNotFoundException();
        }
    }

    public static void encryptFile(EncryptionType type, File file, String key, String outputName) throws IOException {
        encryptOrDecryptFile(type, file, key, outputName, true);
    }

    public static void decryptFile(EncryptionType type, File file, String key) throws IOException {
        encryptOrDecryptFile(type, file, key, "decrypted", false);
    }
}
