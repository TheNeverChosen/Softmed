/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente;

import Banco.Sessao;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class MenuController implements Initializable {

    @FXML
    private Button agenda;

    @FXML
    private Button config;

    @FXML
    private Button deslog;
    
    @FXML
    private Button consultas;   

    @FXML
    void abrirAg(ActionEvent event) {
        new ScreenController().trocaTela("/Paciente/Agenda/Agenda.fxml");
    }

    @FXML
    void configurar(ActionEvent event) {
        new ScreenController().trocaTela("/Paciente/Config/Config.fxml");
    }
    
    @FXML
    void consultar(ActionEvent event) {
        new ScreenController().trocaTela("/Paciente/Consultas/Consultas.fxml");
    }

    @FXML
    void deslogar(ActionEvent event) throws IOException {
        Sessao.setLoginPac(null);
        new ScreenController().modoJanela();
    }
    
    @FXML
    void gifCad(MouseEvent event){
        agenda.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/gifs/caderno.gif").toString()+"\")");
    }

    @FXML
    void gifConfig(MouseEvent event) {
        config.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/gifs/config.gif").toString()+"\")");
    }

    @FXML
    void gifCons(MouseEvent event) {
        consultas.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/gifs/consultas.gif").toString()+"\")");
    }
    
    @FXML
    void gifDeslog(MouseEvent event) {
        deslog.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/gifs/deslogar.gif").toString()+"\")");
    }

    @FXML
    void imgCad(MouseEvent event) {
        agenda.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/imagens/caderno.png").toString()+"\")");
    }

    @FXML
    void imgConfig(MouseEvent event) {
        config.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/imagens/config.png").toString()+"\")");
    }

    @FXML
    void imgCons(MouseEvent event) {
        consultas.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/imagens/consultas.png").toString()+"\")");
    }
    
    @FXML
    void imgDeslog(MouseEvent event) {
        deslog.setStyle("-fx-background-image: url(\""+getClass().getResource("/Css/imagens/deslogar.png").toString()+"\")");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
