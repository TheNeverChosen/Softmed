/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizadores;

import Banco.MetodosMed;
import Banco.MetodosPac;
import Banco.Sessao;
import Objects.Consulta;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class VisConsController implements Initializable {
    
    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private Label l4;

    @FXML
    private Label l5;

    @FXML
    private Label l6;

    @FXML
    private Label l7;

    @FXML
    private Label l8;

    @FXML
    private HBox hBox;
    
    @FXML
    private Button exclui;

    @FXML
    private Button inicia;
    
    MetodosPac metodos;

    @FXML
    void excluir(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
        alert.setTitle("Aviso");
        alert.setHeaderText("Deseja "+exclui.getText()+" esta consulta?");
        alert.setContentText("Isso retirará ela do seu histórico permanentemente");
        alert.getButtonTypes().setAll(sim, nao);
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait().ifPresent(e->{
            if(e==sim){
                metodos.start();
                ResultSet resultado=metodos.getConsulta();
                try {
                    metodos.deletarConsulta(FXCollections.observableArrayList(new Consulta(Sessao.getId(), resultado.getInt("dataid"),
                            null, null, null, null)));
                    
                    if(Sessao.getLoginMed()==null) new ScreenController().mudaBorderEsquerda("/Paciente/Consultas/Historico.fxml");         
                    else new ScreenController().mudaBorderEsquerda("/Medico/Consultas/Historico.fxml");
                    
                } catch (SQLException|IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(0);
                }
                metodos.close();
            }
        });
    }
    
    @FXML
    void iniciar(ActionEvent event) throws IOException {
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
            alert.setTitle("Aviso");
            alert.setHeaderText("Deseja iniciar a consulta atual?");
            alert.setContentText("Depois do início só será possível sair ao finalizar a consulta.\nUma vez iniciada, não será possível realizar a consulta novamente, pois ela será marcada como completa.");
            alert.getButtonTypes().setAll(sim, nao);
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait().ifPresent(e->{
                if(e==sim){
                    MetodosMed metodosMed=new MetodosMed();
                    metodosMed.updateEstadoCons(Sessao.getId());
                    metodosMed.close();
                    new ScreenController().trocaTela("/Paciente/Agenda/Agenda.fxml");
                }
            });
        
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        if(Sessao.getLoginMed()==null) new ScreenController().mudaBorderEsquerda("/Paciente/Consultas/Historico.fxml");
        else new ScreenController().mudaBorderEsquerda("/Medico/Consultas/Historico.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        metodos=new MetodosPac();
        ResultSet resultado=metodos.getConsulta();
        try {
            l1.setText(resultado.getString("nomeMed"));
            l2.setText(resultado.getString("crm"));
            l3.setText(resultado.getString("nomePac"));
            l4.setText(resultado.getString("especialidade"));
            String dia[]=resultado.getString("dataCons").split("-");
            l5.setText(dia[2]+"/"+dia[1]+"/"+dia[0]);
            l6.setText(resultado.getString("localizacao"));
            l7.setText(resultado.getString("horario"));
            l8.setText(resultado.getString("estado"));
            if(l8.getText().equals("Completa")){
                exclui.setText("Excluir");
                hBox.getChildren().remove(inicia);
            }
            if(Sessao.getLoginMed()==null) hBox.getChildren().remove(inicia);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        metodos.close();
        
    }    
    
}
