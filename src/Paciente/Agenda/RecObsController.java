/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Agenda;

import Banco.MetodosPac;
import Banco.Sessao;
import Objects.RecObs;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class RecObsController implements Initializable {

    @FXML
    private Button cadastrar;

    @FXML
    private ComboBox<String> comboBus;

    @FXML
    private TextField tbus;

    @FXML
    private Button exclui;

    @FXML
    private Button visualiza;

    @FXML
    private Button reset;

    @FXML
    private TableView<RecObs> table;

    MetodosPac metodos;
    
    String formas[]={"titulo", "categoria"};
    
    @FXML
    void buscar(KeyEvent event) {
        metodos.start();
        
        table.setItems(metodos.getRecObs(formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void cadastrarRecObs(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderDireitaBaixo("/Cadastros/CadRecObs.fxml");
    }

    @FXML
    void excluir(ActionEvent event) {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione algum registro para excluir");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
            alert.setTitle("Aviso");
            alert.setHeaderText("Deseja deletar o(s) Registros(s) selecionado(s)?");
            alert.getButtonTypes().setAll(sim, nao);
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait().ifPresent(e->{
                if(e==sim){
                    ObservableList<RecObs> lista=table.getSelectionModel().getSelectedItems();
                    metodos.start();
                    metodos.deletarRecObs(lista);
                    metodos.close();
                    table.getItems().removeAll(lista);
                }
            });
        }
    }

    @FXML
    void resetar(ActionEvent event) {
        metodos.start();
        
        table.setItems(metodos.getRecObs("''", ""));
        
        metodos.close();

        comboBus.getSelectionModel().clearSelection();
        
        tbus.setEditable(false);
        tbus.setText("");
    }

    @FXML
    void visivel(ActionEvent event) {
        tbus.setEditable(true);
        metodos.start();
        
        table.setItems(metodos.getRecObs(formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void visualizar(ActionEvent event) throws IOException {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione algum registro para visualizar");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Sessao.setId(table.getSelectionModel().getSelectedItem().getId());
            new ScreenController().mudaBorderDireitaBaixo("/Visualizadores/VisRecObs.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn colunas[]={new TableColumn("Título"), new TableColumn("Categoria")};
        table.getColumns().clear();
        for(int i=0;i<colunas.length;i++) table.getColumns().add(colunas[i]);
        colunas[0].setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunas[1].setCellValueFactory(new PropertyValueFactory<>("categoria"));

        metodos=new MetodosPac();
        table.setItems(metodos.getRecObs("''", ""));
        metodos.close();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        ObservableList<String> lista=FXCollections.observableArrayList("Título", "Categoria");
        comboBus.getItems().addAll(lista);
        
        
    }    
    
}
