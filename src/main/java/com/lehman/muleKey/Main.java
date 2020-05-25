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

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main entry point extends javafx Application.
 * VM args:
 *      --module-path /opt/javafx/javafx-sdk-14.0.1/lib
 *      --add-modules javafx.controls,javafx.fxml
 *      --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
 */
public class Main extends Application {
    /**
     * HostServices object needs to be stored in order
     * to use later for opening browser URLs.
     */
    private static HostServices services;

    /**
     * Constructor set the host services.
     */
    public Main() {
        services = this.getHostServices();
    }

    /**
     * Start function launches the FX application.
     * @param primaryStage is the Stage object.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Mule-Key - Copyright 2020 Roseville Code Inc");
        Scene scene = new Scene(root, 520, 240);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Gets the HostServices object.
     * @return A HostServices object.
     */
    public static HostServices getServices() {
        return services;
    }

    /**
     * The main entry point that calls FX launch.
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // First, try and load the config.
        EncryptionConfig.getInstance().loadConfig();

        // Launch the GUI
        launch(args);
    }

    /**
     * Static helper function that uses the host services
     * and opens they system browser with the provided URL.
     * @param Url is a String with the URL to open.
     */
    public static void openUrl(String Url) {
        HostServices services = getServices();
        services.showDocument(Url);
    }
}
