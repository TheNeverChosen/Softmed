/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diversos;

import Banco.MetodosGerais;
import Banco.MetodosPac;
import Banco.Sessao;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button cad, log, pac;
    
    @FXML
    private TextField t1;

    @FXML
    private PasswordField t2;
    
    @FXML
    private ComboBox<String> comboTip;
    
    private String tipos[]={"pacientes", "medicos"};
    
    @FXML
    void Logar(ActionEvent event) throws IOException {
        new MetodosGerais().Logar(tipos[comboTip.getSelectionModel().getSelectedIndex()],t1.getText(), t2.getText());
    }
    
    @FXML
    void logarAtalho(KeyEvent event) throws IOException {
        if(event.getCode()==KeyCode.ENTER) new MetodosGerais().Logar(tipos[comboTip.getSelectionModel().getSelectedIndex()],t1.getText(), t2.getText());
    }

    @FXML
    void cadMed(ActionEvent event) throws IOException {
        new ScreenController().trocaJanela("/Cadastros/CadMed.fxml");
    }

    @FXML
    void cadPac(ActionEvent event) throws IOException {
        new ScreenController().trocaJanela("/Cadastros/CadPac.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboTip.getItems().setAll(FXCollections.observableArrayList("Paciente", "Médico"));
        comboTip.getSelectionModel().select(0);
    }    
    
}
