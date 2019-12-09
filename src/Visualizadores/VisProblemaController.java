/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizadores;

import Banco.MetodosPac;
import Banco.Sessao;
import Objects.Problema;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class VisProblemaController implements Initializable {

    @FXML
    private TextField t1;

    @FXML
    private TextArea t2;

    @FXML
    private TextField t3;

    @FXML
    private Button back;

    @FXML
    private Button exclui;

    @FXML
    private Button save;
    
    MetodosPac metodos;

    @FXML
    void excluir(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
        alert.setTitle("Aviso");
        alert.setHeaderText("Deseja excluir este registro?");
        alert.getButtonTypes().setAll(sim, nao);
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait().ifPresent(e->{
            if(e==sim){
                try {
                    metodos.start();
                    ObservableList<Problema> lista=FXCollections.observableArrayList(metodos.getProblema(Sessao.getId()));
                    metodos.deletarProblema(lista);
                    metodos.close();
                    new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Problemas.fxml");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(0);
                }
            }
        });
    }

    @FXML
    void salvar(ActionEvent event) {
        if(t1.getText().equals("") || t3.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            metodos.start();
            String txt[]={t1.getText(), t2.getText(), t3.getText()};
            metodos.updateProblema(txt, Sessao.getId());
            metodos.close();
        }
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerdaCima("/Paciente/Agenda/Problemas.fxml");
    }    
    
    void setaCampos(int id){
        metodos=new MetodosPac();
        Problema problema=metodos.getProblema(id);
        t1.setText(problema.getTitulo());
        t2.setText(problema.getDescricao());
        t3.setText(problema.getCategoria());
        metodos.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setaCampos(Sessao.getId());
    }    
    
}
