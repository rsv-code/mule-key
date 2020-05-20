package com.lehman.muleKey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAlgorithm;

    @FXML
    private ChoiceBox choiceBoxMode;

    @FXML
    private TextField textFieldKey;

    @FXML
    private TextField textFieldInput;

    @FXML
    private TextField textFieldOutput;

    @FXML
    private Button buttonEncrypt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set algorithms
        this.choiceBoxAlgorithm.getItems().addAll(Crypto.getAlgorithms());
        this.choiceBoxAlgorithm.getSelectionModel().select(0);

        // Set modes
        this.choiceBoxMode.getItems().addAll(Crypto.getModes());
        this.choiceBoxMode.getSelectionModel().select(0);

        // Set button event handler
        this.buttonEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runEncrypt();
            }
        });
    }

    public void runEncrypt() {
        String algorithm = (String)this.choiceBoxAlgorithm.getSelectionModel().getSelectedItem();
        String mode = (String)this.choiceBoxMode.getSelectionModel().getSelectedItem();
        String key = (String)this.textFieldKey.getText();
        String inputText = this.textFieldInput.getText();
        try {
            this.textFieldOutput.setText(Crypto.encrypt(algorithm, mode, key, inputText));
        } catch (Exception e) {
            Alert alt = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alt.showAndWait();
        }
    }
}
