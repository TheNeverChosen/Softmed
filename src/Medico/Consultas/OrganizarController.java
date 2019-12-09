/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Medico.Consultas;

import Banco.MetodosMed;
import Objects.Horario;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class OrganizarController implements Initializable {

    @FXML
    private Button volta;

    @FXML
    private DatePicker calendario;
    
    @FXML
    private HBox hbox1;

    @FXML
    private Spinner<Integer> horas;

    @FXML
    private Spinner<Integer> minutos;

    @FXML
    private Button adiciona;
    
    @FXML
    private HBox hbox2;

    @FXML
    private ListView<Horario> lista;

    @FXML
    private Button remove;
    
    @FXML
    private Button limpa;
    
    ArrayList<LocalDate> datas;
    
    MetodosMed metodos;

    @FXML
    void adicionar(ActionEvent event) {
        
        if(calendario.getValue().isBefore(LocalDate.now())){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dia indisponível para novos horários");
            alert.setContentText("Não é possível adicionar novos horários em dias anteriores ao de hoje");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            String horinha=String.format("%02d", horas.getValue())+" : "+String.format("%02d", minutos.getValue());
            boolean achou=false;
            for(Horario x:lista.getItems()){
                if(x.getTime().equalsIgnoreCase(horinha)){
                    achou=true;
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta");
                    alert.setHeaderText("Horário já adicionado.");
                    alert.initOwner(ScreenController.getStage());
                    alert.showAndWait();
                    break;
                }
            }
            if(!achou){
                metodos.start();
                metodos.cadConsulta(calendario.getValue(), horas.getValue().toString()+":"+minutos.getValue().toString());
                metodos.close();
                if(!datas.contains(calendario.getValue())){
                    datas.add(calendario.getValue());
                }
                atualizaCampos();
            }
        }
    }
    
    @FXML
    void limpar(ActionEvent event) {
        metodos.start();
        metodos.deletarHorarios(calendario.getValue());
        metodos.close();
        datas.remove(calendario.getValue());
        atualizaCampos();
    }

    @FXML
    void remover(ActionEvent event) {
        if(lista.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione algum horário para excluir");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            metodos.start();
            metodos.deletarHorario(lista.getSelectionModel().getSelectedItem().getId());
            metodos.close();
            lista.getItems().remove(lista.getSelectionModel().getSelectedItem());
            if(lista.getItems().isEmpty()){
                datas.remove(calendario.getValue());
            }
        }
    }
    
    @FXML
    void selecionarData(ActionEvent event) {
        atualizaCampos();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerda("/Medico/Consultas/Historico.fxml");
    }
    
    void atualizaCampos(){
        horas.getValueFactory().setValue(0);
        minutos.getValueFactory().setValue(0);
        metodos.start();
        lista.setItems(metodos.getHorarios(calendario.getValue()));
        metodos.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        horas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minutos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        
        calendario.setValue(LocalDate.now());
        metodos=new MetodosMed();
        datas=metodos.getDatas();
        metodos.close();
        final Callback<DatePicker, DateCell> dayCellFactory = 
            (final DatePicker datePicker) -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (datas.contains(item)){
                        setStyle("-fx-background-color: #66dd88;");
                    }
                    
                }
            };
        calendario.setDayCellFactory(dayCellFactory);
        atualizaCampos();
        
    }    
    
}
