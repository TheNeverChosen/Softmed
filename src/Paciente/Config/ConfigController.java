/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Config;

import ScreenFramework.ScreenController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author 2aimaq11
 */
public class ConfigController implements Initializable {

    @FXML
    private BorderPane border;

    @FXML
    private VBox vBox;
    
    @FXML
    private VBox vBoxG;

    @FXML
    private Button back;
    
    @FXML
    private StackPane stack;

    @FXML
    void voltar(ActionEvent event) {
        new ScreenController().trocaTela("/Paciente/Menu.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
        vBoxG.setPrefSize(220, screenSize.getHeight()-50);
        
        ScreenController.setBorder(border);
        
        try {
            ScreenController.setTam((int) screenSize.getHeight(), (int) screenSize.getWidth()-224);
            ScreenController.setInicial(stack, "/Paciente/Config/Perfil.fxml", vBox);
            Pane pane=FXMLLoader.load(getClass().getResource("/Paciente/Config/Perfil.fxml"));
            stack.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
