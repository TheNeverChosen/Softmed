/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Consultas;

import ScreenFramework.ScreenController;
import Objects.Consulta;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class ConsultasController implements Initializable {
    
    @FXML
    private BorderPane border;

    @FXML
    private Button back;
    
    @FXML
    private StackPane stack;
    
    @FXML
    private VBox vBox;

    @FXML
    void voltar(ActionEvent event) {
        new ScreenController().trocaTela("/Paciente/Menu.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
    
        try {
            ScreenController.setTam((int) screenSize.getHeight()-50, (int) screenSize.getWidth());
            ScreenController.setInicial(stack, "/Paciente/Consultas/Historico.fxml", vBox);
            Pane pane=FXMLLoader.load(getClass().getResource("/Paciente/Consultas/Historico.fxml"));
            stack.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
}
