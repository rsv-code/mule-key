package com.lehman.muleKey;

import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Mule-Key - Written by Austin Lehman");
        primaryStage.setScene(new Scene(root, 850, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
