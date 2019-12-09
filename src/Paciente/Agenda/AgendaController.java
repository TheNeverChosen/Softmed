/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Agenda;

import Banco.Sessao;
import Objects.Consulta;
import ScreenFramework.ScreenController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class AgendaController implements Initializable {
    
    @FXML
    private BorderPane border;
    
    @FXML
    private Button atv;
     
    @FXML
    private Button recobs;
    
    @FXML
    private Button problema;

    @FXML
    private VBox vBox;
    
    @FXML
    private VBox vBoxG;

    @FXML
    private Button back;
    
    @FXML
    private StackPane stack;

    @FXML
    void trocaAtividade(ActionEvent event) throws IOException {
        if(!ScreenController.getTelaAtual().equals("/Paciente/Agenda/Atividades.fxml")){
            new ScreenController().mudaBorderCima("/Paciente/Agenda/Atividades.fxml");
        }
    }
    
    @FXML
    void trocaProblema(ActionEvent event) throws IOException {
        if(!ScreenController.getTelaAtual().equals("/Paciente/Agenda/Problemas.fxml"))
            new ScreenController().mudaBorderBaixo("/Paciente/Agenda/Problemas.fxml");
    }
    
    @FXML
    void trocaRecomenda(ActionEvent event) throws IOException, InterruptedException {
        String atual=ScreenController.getTelaAtual();
        if(!atual.equals("/Paciente/Agenda/RecObs.fxml")){
            if(atual.equals("/Paciente/Agenda/Problemas.fxml")) new ScreenController().mudaBorderCima("/Paciente/Agenda/RecObs.fxml");
            else new ScreenController().mudaBorderBaixo("/Paciente/Agenda/RecObs.fxml");
        }
            
    }
    
    @FXML
    void voltar(ActionEvent event){
        if(Sessao.getLoginMed()!=null){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
            alert.setTitle("Aviso");
            alert.setHeaderText("Deseja finalizar a consulta?");
            alert.setContentText("Nenhuma interação com esta consulta será possível novamente.");
            alert.getButtonTypes().setAll(sim, nao);
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait().ifPresent(e->{
                if(e==sim){
                    Sessao.setLoginPac(null);
                    new ScreenController().trocaTela("/Medico/Consultas/Consultas.fxml");
                }
            });
        }
        else{
            new ScreenController().trocaTela("/Paciente/Menu.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
        int medCabecalho=48;
        
        if(Sessao.getLoginMed()!=null){
            back.setText("Finalizar Consulta");
            ScreenController.setTam((int) (screenSize.getHeight()-medCabecalho), (int) screenSize.getWidth()-224);
            vBoxG.setPrefSize(220, screenSize.getHeight()-50*3-medCabecalho);
            try {
                border.topProperty().set(FXMLLoader.load(getClass().getResource("/Medico/Consultas/Consultar.fxml")));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }
        else{
            ScreenController.setTam((int) screenSize.getHeight(), (int) screenSize.getWidth()-224);
            vBoxG.setPrefSize(220, screenSize.getHeight()-50*3);
        }
        
        try{
            ScreenController.setInicial(stack, "/Paciente/Agenda/Atividades.fxml", vBox);
            Pane pane=FXMLLoader.load(getClass().getResource("/Paciente/Agenda/Atividades.fxml"));
            stack.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}