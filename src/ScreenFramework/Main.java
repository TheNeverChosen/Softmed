/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenFramework;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Fernando
 */
public class Main extends Application{
    
    @Override
    public void start(Stage primaryStage) throws IOException{        
        new ScreenController().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
