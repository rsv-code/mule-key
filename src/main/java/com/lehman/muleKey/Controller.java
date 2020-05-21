/*
 * This file is part of the Mule-Key (https://github.com/rsv-code/mule-key).
 * Copyright (c) 2020 Roseville Code Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.lehman.muleKey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * The main view controller. This app is simple so there's
 * just this single one for the whole application.
 */
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

    /**
     * Implementation of the initialize function to
     * setup the UI.
     * @param url
     * @param resourceBundle
     */
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
                Main.openUrl("http://rosevillecode.com");
            }
        });
    }

    /**
     * Generates a key and sets it in the key text field.
     */
    public void generateKey() {
        String algorithm = (String)this.choiceBoxAlgorithm.getSelectionModel().getSelectedItem();
        this.textFieldKey.setText(Crypto.generateKey(algorithm));
    }

    /**
     * Runs the encrypt function. It gets the algorithm, mode, key, and input text
     * fields and then sets the result in the output text field.
     */
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
