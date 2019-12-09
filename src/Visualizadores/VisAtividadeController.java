/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizadores;

import Banco.MetodosPac;
import Banco.Sessao;
import ScreenFramework.ScreenController;
import Objects.Atividade;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class VisAtividadeController implements Initializable {

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
    private Button back;

    @FXML
    private Button exclui;

    @FXML
    private Button save;
    
    MetodosPac metodos;
    
    @FXML
    void excluir(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
        alert.setTitle("Aviso");
        alert.setHeaderText("Deseja excluir esta atividade?");
        alert.getButtonTypes().setAll(sim, nao);
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait().ifPresent(e->{
            if(e==sim){
                try {
                    metodos.start();
                    ObservableList<Atividade> lista=FXCollections.observableArrayList(metodos.getAtividade(Sessao.getId()));
                    metodos.deletarAtividade(lista);
                    metodos.close();
                    new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Atividades.fxml");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(0);
                }
            }
        });
        
    }
    
    @FXML
    void salvar(ActionEvent event) throws IOException {
        if(t1.getText().equals("") || comboPer.getSelectionModel().isEmpty() || comboEst.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            metodos.start();
            String txt[]={t1.getText(), t2.getText(), spinner.getEditor().getText()+" Vez(es) por "+comboPer.getSelectionModel().getSelectedItem(),
                    comboEst.getSelectionModel().getSelectedItem()};
            metodos.updateAtividade(txt, Sessao.getId());
            metodos.close();
        }
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Atividades.fxml");
    }
    
    void setaCampos(int id){
        metodos=new MetodosPac();
        Atividade atividade=metodos.getAtividade(id);
        t1.setText(atividade.getTitulo());
        t2.setText(atividade.getDescricao());
        String partes[]=atividade.getFrequencia().split(" ");
        spinner.getValueFactory().setValue(Integer.valueOf(partes[0]));
        comboPer.getSelectionModel().select(partes[3]);
        comboEst.getSelectionModel().select(atividade.getEstado());
        metodos.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 365, 0);
        spinner.setValueFactory(valueFactory);
        
        ObservableList<String>lista=FXCollections.observableArrayList("Dia", "Semana", "Mês", "Ano");
        comboPer.getItems().addAll(lista);
        lista=FXCollections.observableArrayList("Completa", "Pendente");
        comboEst.getItems().addAll(lista);
        setaCampos(Sessao.getId());
    }      
    
}
