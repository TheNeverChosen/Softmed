/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Medico;

import Banco.Sessao;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class MenuController implements Initializable {
    
    @FXML
    private VBox fundo;
    
    @FXML
    private Button consultas;

    @FXML
    private Button config;

    @FXML
    private Button deslog;

    @FXML
    void configurar(ActionEvent event) {
        new ScreenController().trocaTela("/Medico/Config/Config.fxml");
    }

    @FXML
    void consultar(ActionEvent event) {
        new ScreenController().trocaTela("/Medico/Consultas/Consultas.fxml");
    }

    @FXML
    void deslogar(ActionEvent event) throws IOException {
        Sessao.setLoginMed(null);
        new ScreenController().modoJanela();
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
