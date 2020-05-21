package com.lehman.muleKey;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main entry point.
 * VM args:
 *      --module-path /opt/javafx/javafx-sdk-14.0.1/lib
 *      --add-modules javafx.controls,javafx.fxml
 *      --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
 */
public class Main extends Application {
    private static HostServices services;

    public Main() {
        services = this.getHostServices();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Mule-Key - Copyright 2020 Roseville Code Inc");
        Scene scene = new Scene(root, 520, 200);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static HostServices getServices() {
        return services;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
