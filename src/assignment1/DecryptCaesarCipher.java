package assignment1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecryptCaesarCipher {
    public static void main(String[] args) throws IOException {
        File file = new File("F:\\Downloads\\Mathias_Feuchtenhofer.txt");
        File out = new File("F:\\Downloads\\Mathias_Feuchtenhofer_decrypted.txt");
        FileReader reader = new FileReader(file);
        FileWriter writer = new FileWriter(out);
        List<Character> norm = new ArrayList<>();
        List<Character> trans = new ArrayList<>(Arrays.asList('x', 'y', 'z'));
        for (int i = 97; i < 97 + 26; i++){
            norm.add((char) i);
        }
        for (int i = 97; i < (97 + 23); i++){
            trans.add((char) i);
        }
        int character;
        while ((character = reader.read()) != -1){
            if (Character.isLetter(character) && Character.isLowerCase(character)){
                writer.write(norm.get(trans.indexOf((char) character)));
            } else {
                writer.write((char) character);
            }
        }
        writer.flush();
    }
}
