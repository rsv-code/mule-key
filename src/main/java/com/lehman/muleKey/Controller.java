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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;


import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.ResourceBundle;

/**
 * The main view controller. This app is simple so there's
 * just this single one for the whole application.
 */
public class Controller implements Initializable {
    @FXML
    private HBox hboxConfig;

    @FXML
    private ComboBox comboBoxConfig;

    @FXML
    private Button buttonSave;

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

    @FXML
    private Button buttonAddToKeyStore;

    /**
     * Implementation of the initialize function to
     * setup the UI.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBoxConfig = new ComboBox<EncryptionConfigRecord>();
        this.comboBoxConfig.setEditable(true);
        this.comboBoxConfig.setMaxWidth(Double.MAX_VALUE);
        this.comboBoxConfig.getItems().addAll(EncryptionConfig.getInstance().getConfigFile().getKeys());
        this.hboxConfig.getChildren().set(1, this.comboBoxConfig);
        this.hboxConfig.setHgrow(this.comboBoxConfig, Priority.ALWAYS);

        this.comboBoxConfig.setConverter(new StringConverter<EncryptionConfigRecord>() {
            @Override
            public String toString(EncryptionConfigRecord rec) {
                if (rec != null) {
                    return rec.getName();
                }
                return "";
            }

            @Override
            public EncryptionConfigRecord fromString(String string) {
                return (EncryptionConfigRecord) comboBoxConfig.getSelectionModel().getSelectedItem();
            }
        });

        this.comboBoxConfig.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadConfig();
            }
        });


        // Save config button
        this.buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveConfig();
            }
        });

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

        this.buttonAddToKeyStore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    saveInKeyStore();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Loads the config records from the encryption config and puts them in the
     * config combo box.
     */
    public void loadConfigRecords() {
        this.comboBoxConfig.getItems().clear();
        this.comboBoxConfig.getItems().addAll(EncryptionConfig.getInstance().getConfigFile().getKeys());
    }

    /**
     * Saves the current config.
     */
    public void saveConfig() {
        // Because the combobox is editable it has a value and editor
        // that has to be checked for value.
        Object val = this.comboBoxConfig.getValue();
        String txtval = this.comboBoxConfig.getEditor().getText();

        EncryptionConfigRecord newConfig;
        if (val instanceof EncryptionConfigRecord && txtval == null) {
            newConfig = (EncryptionConfigRecord)val;
        } else if (!txtval.trim().equals("")) {
            newConfig = new EncryptionConfigRecord();
            newConfig.setName(txtval);
        } else {
            // Save is clicked on a blank string ...
            Alert alt = new Alert(Alert.AlertType.ERROR, "You must specify a name in the config field to save the config as.");
            alt.showAndWait();
            return;
        }
        newConfig.setAlgorithm(Algorithm.get((String)this.choiceBoxAlgorithm.getValue()));
        newConfig.setMode(Mode.get((String)this.choiceBoxMode.getValue()));
        newConfig.setKey(this.textFieldKey.getText());

        // Look for existing key record in config file.
        EncryptionConfigRecord found = null;
        for (EncryptionConfigRecord rec : EncryptionConfig.getInstance().getConfigFile().getKeys()) {
            if (rec.getName().equals(newConfig.getName())) {
                found = rec;
                break;
            }
        }

        if (found != null) {
            // Update needed
            found.setAlgorithm(newConfig.getAlgorithm());
            found.setMode(newConfig.getMode());
            found.setKey(newConfig.getKey());
        } else {
            EncryptionConfig.getInstance().getConfigFile().getKeys().add(newConfig);
        }

        try {
            EncryptionConfig.getInstance().saveConfig();
            if (found == null) {
                this.comboBoxConfig.getItems().add(newConfig);
                this.comboBoxConfig.getSelectionModel().select(newConfig);
            }

            // Alert the user that the record has been saved.
            Alert alt = new Alert(Alert.AlertType.INFORMATION, "Record '" + newConfig.getName() + "' has been saved.");
            alt.showAndWait();
        } catch (IOException e) {
            Alert alt = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alt.showAndWait();
        }
    }

    /**
     * This is ran when the comboBoxConfig value is changed from
     * the selector. It then fires this function.
     */
    public void loadConfig() {
        EncryptionConfigRecord rec = (EncryptionConfigRecord) this.comboBoxConfig.getValue();
        if (rec != null && rec instanceof  EncryptionConfigRecord) {
            this.choiceBoxAlgorithm.setValue(rec.getAlgorithm().getValue());
            this.choiceBoxMode.setValue(rec.getMode().getValue());
            this.textFieldKey.setText(rec.getKey());
        }
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

    public void saveInKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        //Creating the KeyStore object
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //Loading the KeyStore object
        char[] password = "password".toCharArray();
        String path = "emptyStore.keystore";
        FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);

        EncryptedPrivateKeyInfo keyInfo = new EncryptedPrivateKeyInfo(this.choiceBoxAlgorithm.getValue().toString(), this.textFieldKey.getText().getBytes());
        keyStore.setKeyEntry("mule.vault.key", keyInfo.getEncoded(), null);

        FileOutputStream fos = null;
        fos = new FileOutputStream("emptyStore.keystore");
        keyStore.store(fos, password);
    }
}
