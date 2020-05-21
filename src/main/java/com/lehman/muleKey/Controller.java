package com.lehman.muleKey;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAlgorithm;

    @FXML
    private ChoiceBox choiceBoxMode;

    @FXML
    private TextField textFieldKey;

    @FXML
    private Button buttonGenerateKey;

    @FXML
    private TextField textFieldInput;

    @FXML
    private TextField textFieldOutput;

    @FXML
    private Button buttonEncrypt;

    @FXML
    private ImageView imageViewLogo;

    @FXML
    private Hyperlink hyperlinkLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set algorithms
        this.choiceBoxAlgorithm.getItems().addAll(Crypto.getAlgorithms());
        this.choiceBoxAlgorithm.getSelectionModel().select(0);

        // Set modes
        this.choiceBoxMode.getItems().addAll(Crypto.getModes());
        this.choiceBoxMode.getSelectionModel().select(0);

        // Set generate button event handler
        this.buttonGenerateKey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateKey();
            }
        });

        // Set encrypt button event handler
        this.buttonEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runEncrypt();
            }
        });

        this.hyperlinkLogo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openUrl("http://rosevillecode.com");
            }
        });
    }

    public void generateKey() {
        String algorithm = (String)this.choiceBoxAlgorithm.getSelectionModel().getSelectedItem();
        this.textFieldKey.setText(Crypto.generateKey(algorithm));
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

    public void openUrl(String Url) {
        HostServices services = Main.getServices();
        services.showDocument(Url);
    }
}
