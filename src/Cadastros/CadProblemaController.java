/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cadastros;

import Banco.MetodosPac;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class CadProblemaController implements Initializable {

    @FXML
    private TextField t1;

    @FXML
    private TextArea t2;

    @FXML
    private TextField t3;

    @FXML
    private Button canc;

    @FXML
    private Button cad;
    
    MetodosPac metodos;

    @FXML
    void cadastrar(ActionEvent event) throws IOException {
        if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            metodos=new MetodosPac();
            String txt[]={t1.getText(), t2.getText(), t3.getText()};
            metodos.cadProblema(txt);
            metodos.close();
            new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Problemas.fxml");
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Problemas.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
