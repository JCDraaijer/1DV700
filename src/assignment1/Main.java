package assignment1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static String generateKey(EncryptionType type) {
        if (type == EncryptionType.SUBSTITUTION) {
            byte b = (byte) new Random().nextInt(256);
            return String.format("%02X ", b);
        } else {
            StringBuilder keyBuilder = new StringBuilder();
            Random rand = new Random();
            ArrayList<Integer> keyList = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
            while (keyList.size() != 0) {
                int random = rand.nextInt(keyList.size());
                keyBuilder.append(keyList.get(random));
                keyList.remove(random);
            }
            if (keyBuilder.toString().equalsIgnoreCase("1234")) {
                return generateKey(type);
            } else {
                return keyBuilder.toString();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vBoxTotal = new VBox();
        vBoxTotal.setPadding(new Insets(10));


        Text theTitle = new Text("Encrypt/Decrypt");
        vBoxTotal.getChildren().add(theTitle);

        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        vBoxTotal.getChildren().add(pane);

        pane.setPadding(new Insets(5));
        ComboBox<String> selectEncryptionType = new ComboBox<>(FXCollections.observableArrayList("substitution", "transposition"));

        final Text keyText = new Text("Key:");
        final Text inputText = new Text("Input file: ");
        final Text encryptionTypeText = new Text("Encryption type:");

        final TextField keyField = new TextField();
        keyField.setPromptText("This field requires input");
        final TextField inputFileField = new TextField();
        inputFileField.setPromptText("This field requires input");

        final Button encryptButton = new Button("Encrypt");
        final Button decryptButton = new Button("Decrypt");
        final Button generateKeyButton = new Button("Generate key");
        generateKeyButton.setDisable(true);
        encryptButton.setDisable(true);
        decryptButton.setDisable(true);

        final Text errorField = new Text();
        errorField.setFill(Color.RED);
        errorField.setFont(new Font(17));

        pane.add(encryptionTypeText, 0, 0, 1, 1);
        pane.add(selectEncryptionType, 1, 0, 2, 1);
        pane.add(keyText, 0, 1, 1, 1);
        pane.add(inputText, 0, 2, 1, 1);
        pane.add(keyField, 1, 1, 2, 1);
        pane.add(inputFileField, 1, 2, 2, 1);
        pane.add(encryptButton, 0, 3);
        pane.add(decryptButton, 1, 3);
        pane.add(generateKeyButton, 2, 3);


        pane.add(errorField, 0, 4, 3, 1);

        selectEncryptionType.setOnAction(e -> {
            encryptButton.setDisable(false);
            decryptButton.setDisable(false);
            generateKeyButton.setDisable(false);
        });

        generateKeyButton.setOnAction(e -> keyField.setText(generateKey(EncryptionType.valueOf(selectEncryptionType.getValue().toUpperCase()))));

        encryptButton.setOnAction(e -> {
            if (!keyField.getText().isEmpty() && !inputFileField.getText().isEmpty()) {
                String key = keyField.getText();
                File file = new File(inputFileField.getText());
                EncryptionType type = EncryptionType.valueOf(selectEncryptionType.getValue().toUpperCase());
                try {
                    if ((type == EncryptionType.SUBSTITUTION && key.length() == 2) || (type == EncryptionType.TRANSPOSITION && key.length() == 4 && key.matches("[0-9]*"))) {
                        errorField.setText("");
                        Encryption.encryptFile(type, file, key, "encrypted");
                    } else {
                        errorField.setText("Invalid key");
                    }
                } catch (Exception ex) {
                    errorField.setText("File not found.");
                }
            }
        });

        decryptButton.setOnAction(e -> {
            if (!keyField.getText().isEmpty() && !inputFileField.getText().isEmpty()) {
                String key = keyField.getText();
                File file = new File(inputFileField.getText());
                EncryptionType type = EncryptionType.valueOf(selectEncryptionType.getValue().toUpperCase());
                try {
                    if ((type == EncryptionType.SUBSTITUTION && key.length() == 2) || (type == EncryptionType.TRANSPOSITION && key.length() == 4 && key.matches("[0-9]*"))) {
                        errorField.setText("");
                        Encryption.decryptFile(type, file, key);
                    } else {
                        errorField.setText("Invalid key");
                    }
                } catch (Exception ex) {
                    errorField.setText("File not found.");
                }
            }
        });

        Scene scene = new Scene(vBoxTotal);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
