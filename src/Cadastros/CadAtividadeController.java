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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class CadAtividadeController implements Initializable {

    @FXML
    private TextField t1;
    
    @FXML
    private TextArea t2;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private ComboBox<String> comboPer;

    @FXML
    private ComboBox<String> comboEst;

    @FXML
    private Button canc;

    @FXML
    private Button cad;

    @FXML
    void cadastrar(ActionEvent event) throws IOException {
        if(t1.getText().equals("") || comboPer.getSelectionModel().isEmpty() || comboEst.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            MetodosPac metodos=new MetodosPac();
            String txt[]={t1.getText(), t2.getText(), spinner.getEditor().getText()+" Vez(es) por "+comboPer.getSelectionModel().getSelectedItem(),
                    comboEst.getSelectionModel().getSelectedItem()};
            metodos.cadAtividade(txt);
            metodos.close();
            new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Atividades.fxml");
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Atividades.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ScreenController.getBorder().setStyle("-fx-background-color: #7ce1ec;");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 365, 0);
        spinner.setValueFactory(valueFactory);
        
        ObservableList<String>lista=FXCollections.observableArrayList("Dia", "Semana", "Mês", "Ano");
        comboPer.getItems().addAll(lista);
        lista=FXCollections.observableArrayList("Completa", "Pendente");
        comboEst.getItems().addAll(lista);
        
    }    
    
}
