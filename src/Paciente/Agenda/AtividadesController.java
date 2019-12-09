/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Agenda;

import Banco.MetodosPac;
import Banco.Sessao;
import ScreenFramework.ScreenController;
import Objects.Atividade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Fernando
 */

// substituir mecanismo de botao procura por procura automatica
// porque: pq ai nao vai precisar de variaveis auxiliares como o buscado e formado para auxiliar nos resets e filtros. Fazer rapido!

public class AtividadesController implements Initializable {
    
    @FXML   
    private Button cadastrar;

    @FXML
    private ComboBox<String> comboBus;

    @FXML
    private TextField tbus;
    
    @FXML
    private Button busca;

    @FXML
    private Button exclui;

    @FXML
    private Button visualiza;

    @FXML
    private ComboBox<String> comboFilt;

    @FXML
    private TableView<Atividade> table;
    
    MetodosPac metodos;
    
    String estados[]={"", "Completa", "Pendente"}, formas[]={"titulo", "frequencia"};

    @FXML
    void buscar(KeyEvent event) {
        metodos.start();
        
        table.setItems(metodos.getAtividades(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }
    
    @FXML
    void cadastrarAtv(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderDireitaBaixo("/Cadastros/CadAtividade.fxml");
    }

    @FXML
    void excluir(ActionEvent event) {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione alguma atividade para excluir");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
            alert.setTitle("Aviso");
            alert.setHeaderText("Deseja deletar a(s) atividade(s) selecionada(s)?");
            alert.getButtonTypes().setAll(sim, nao);
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait().ifPresent(e->{
                if(e==sim){
                    ObservableList<Atividade> lista=table.getSelectionModel().getSelectedItems();
                    metodos.start();
                    metodos.deletarAtividade(lista);
                    metodos.close();
                    table.getItems().removeAll(lista);
                }
            });
        }
    }
    
    @FXML
    void filtrar(ActionEvent event) {
        metodos.start();
        
        String formado="''";
        
        if(tbus.isEditable()) formado=formas[comboBus.getSelectionModel().getSelectedIndex()];
        
        table.setItems(metodos.getAtividades(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formado, tbus.getText()));
        
        metodos.close();
    }
    
    @FXML
    void resetar(ActionEvent event) {     
        comboFilt.getSelectionModel().select(0);
        comboBus.getSelectionModel().clearSelection();
        
        tbus.setEditable(false);
        tbus.setText("");
        
        metodos.start();
        
        table.setItems(metodos.getAtividades("", "''", ""));
        
        metodos.close();
    }
    
    @FXML
    void visivel(ActionEvent event) {
        tbus.setEditable(true);
        metodos.start();
        
        table.setItems(metodos.getAtividades(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void visualizar(ActionEvent event) throws IOException {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione alguma atividade para visualizar");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Sessao.setId(table.getSelectionModel().getSelectedItem().getId());
            new ScreenController().mudaBorderDireitaBaixo("/Visualizadores/VisAtividade.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn colunas[]={new TableColumn("Título"), new TableColumn("Frequência"), new TableColumn("Estado")};
        table.getColumns().clear();
        for(int i=0;i<colunas.length;i++) table.getColumns().add(colunas[i]);
        colunas[0].setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunas[1].setCellValueFactory(new PropertyValueFactory<>("frequencia"));
        colunas[2].setCellValueFactory(new PropertyValueFactory<>("estado"));

        metodos=new MetodosPac();
        table.setItems(metodos.getAtividades("", "''", ""));
        metodos.close();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        ObservableList<String> lista=FXCollections.observableArrayList("Título", "Frequência");
        comboBus.getItems().addAll(lista);
        lista=FXCollections.observableArrayList("Todas", "Completa", "Pendente");
        comboFilt.setItems(lista);
        comboFilt.getSelectionModel().selectFirst();
        
        
    }
}
