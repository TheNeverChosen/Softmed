/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenFramework;

import Banco.Sessao;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class ScreenController{
    
    private static Stage stage;
    private static BorderPane border;
    private static StackPane stack;
    private static int altura, largura;
    private static String telaAtual;
    private static VBox vBox;
    
    public void start(Stage stage) throws IOException{
        
        Sessao.setLoginMed(null);
        Sessao.setLoginPac(null);
        
        ScreenController.stage=stage;
        
        configStage();
        stage.setResizable(false);
        
        Parent root = FXMLLoader.load(getClass().getResource("/Diversos/Login.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public static void setTam(int altura, int largura) {
        ScreenController.altura = altura;
        ScreenController.largura= largura;
    }

    public static void setInicial(StackPane stack, String tela, VBox vBox) {
        ScreenController.stack = stack;
        ScreenController.telaAtual=tela;
        ScreenController.vBox=vBox;
    }

    public static String getTelaAtual() {
        return telaAtual;
    }
    
    public static void setBorder(BorderPane border) {
        ScreenController.border = border;
    }

    public static BorderPane getBorder() {
        return border;
    }

    public static Stage getStage() {
        return stage;
    }
    
    //--------------------------------------------------------------------------//
    
    //----------------------------TRANSIÇÕES------------------------------------//
    
    public void trocaJanela(String tela) throws IOException{
        
        Timeline timeline1 = new Timeline(), timeline2=new Timeline();
        timeline1.getKeyFrames().setAll(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 0)));
        
        timeline1.setOnFinished((ActionEvent event) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(tela));
                stage.setScene(new Scene(root));
                stage.sizeToScene();
                stage.centerOnScreen();
                timeline1.getKeyFrames().setAll(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 1)));
                timeline1.setOnFinished(null);
                timeline1.playFromStart();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        timeline1.play();
    }
    
    public void trocaTela(String tela){
        
        FadeTransition ft = new FadeTransition(Duration.millis(300), stage.getScene().getRoot());
        ft.setFromValue(1);
        ft.setToValue(0);
        
        ft.setOnFinished((ActionEvent event) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(tela));
                root.setOpacity(0);
                stage.getScene().setRoot(root);
                ft.setNode(stage.getScene().getRoot());   
                
                ft.setOnFinished(null);
                ft.setRate(-1);
                ft.play();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        ft.play();
    }
    
    public void modoCheia(String tipo) throws IOException{
        
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 0)));
        
        timeline1.setOnFinished((ActionEvent event) -> {
            try {
                stage.close();
                stage=new Stage(StageStyle.UNDECORATED);
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                configStage();
                
                Parent root;
                if(tipo.equals("pacientes"))root = FXMLLoader.load(getClass().getResource("/Paciente/Menu.fxml"));
                else root = FXMLLoader.load(getClass().getResource("/Medico/Menu.fxml"));
                
                stage.setScene(new Scene(root));
                stage.opacityProperty().set(0);
                timeline1.getKeyFrames().setAll(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 1)));
                timeline1.setOnFinished(null);
                stage.show();
                timeline1.playFromStart();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        timeline1.play();

    }
    
    public void modoJanela() throws IOException{
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 0)));
        
        timeline1.setOnFinished((ActionEvent event) -> {
            try {
                stage.close();
                stage=new Stage(StageStyle.DECORATED);
                configStage();
                stage.resizableProperty().set(false);
                Parent root = FXMLLoader.load(getClass().getResource("/Diversos/Login.fxml"));
                stage.setScene(new Scene(root));
                stage.opacityProperty().set(0);
                stage.show();
                timeline1.getKeyFrames().setAll(new KeyFrame(Duration.millis(250), new KeyValue (stage.opacityProperty(), 1)));
                timeline1.setOnFinished(null);
                timeline1.playFromStart();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        timeline1.play();
        
    }
    
    public void mudaBorderBaixo(String tela) throws IOException{
        telaAtual=tela;

        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateYProperty().set(altura);
        stack.getChildren().get(0).translateYProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateYProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateYProperty(), -altura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    public void mudaBorderCima(String tela) throws IOException{
        telaAtual=tela;

        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateYProperty().set(-altura);
        stack.getChildren().get(0).translateYProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateYProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateYProperty(), altura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    public void mudaBorderDireitaBaixo(String tela) throws IOException{
        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateXProperty().set(largura);
        stack.getChildren().get(0).translateYProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateYProperty(), -largura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    public void mudaBorderEsquerdaCima(String tela) throws IOException{
        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateYProperty().set(-altura);
        stack.getChildren().get(0).translateXProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateYProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateXProperty(), largura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    public void mudaBorderDireita(String tela) throws IOException{
        telaAtual=tela;

        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateXProperty().set(largura);
        stack.getChildren().get(0).translateXProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateXProperty(), -largura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    public void mudaBorderEsquerda(String tela) throws IOException{
        telaAtual=tela;

        Pane pane=FXMLLoader.load(getClass().getResource(tela));
        pane.translateXProperty().set(-largura);
        stack.getChildren().get(0).translateXProperty().set(0);

        KeyValue    kv1=new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_IN),
                    kv2=new KeyValue(stack.getChildren().get(0).translateXProperty(), largura, Interpolator.EASE_IN);

        KeyFrame kf1=new KeyFrame(Duration.seconds(1), kv1), kf2=new KeyFrame(Duration.seconds(1), kv2);

        Timeline timeline=new Timeline();
        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished((event) -> {
            vBox.setDisable(false);
            stack.getChildren().remove(0);
        });

        stack.getChildren().add(pane);
        vBox.setDisable(true);
        timeline.play();
    }
    
    //--------------------------------------------------------------------------//
    
    //----------------------------Configurações---------------------------------//
    
    public void configStage(){
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Img/Icone.png")));
        stage.setTitle("SoftMed");
    }
    
}
