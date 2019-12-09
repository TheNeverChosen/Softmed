/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cadastros;

import Banco.MetodosGerais;
import Banco.MetodosPac;
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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class CadMedController implements Initializable {

    @FXML
    private TextField t1;

    @FXML
    private TextField t2;

    @FXML
    private TextField t3;

    @FXML
    private TextField t4;

    @FXML
    private TextField t5;

    @FXML
    private TextField t6;

    @FXML
    private TextField t7;

    @FXML
    private TextField t8;

    @FXML
    private ComboBox<String> comboEst;

    @FXML
    private ComboBox<String> comboEsp;

    @FXML
    private Button cancelar;

    @FXML
    private Button cadastrar;

    @FXML
    void acaoCad(ActionEvent event) throws IOException {
        if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") ||t7.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else if(comboEst.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione um Estado");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else if(comboEsp.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione uma Especialidade");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            String txt[]={t1.getText(), t2.getText(), t3.getText(), t4.getText(), t5.getText(), t6.getText(), t7.getText(), t8.getText(), comboEst.getSelectionModel().getSelectedItem(), 
                    comboEsp.getSelectionModel().getSelectedItem()};
            MetodosGerais metodos=new MetodosGerais();
            if(metodos.cadMedico(txt)){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText("Cadastrado com sucesso!");
                alert.initOwner(ScreenController.getStage());
                alert.showAndWait();
                new ScreenController().trocaJanela("/Diversos/Login.fxml");
            }
            else{
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta");
                alert.setHeaderText("Login já utilizado");
                alert.setContentText("Outro perfil já usa este Login. Escolha outro para efetuar o cadastro.");
                alert.initOwner(ScreenController.getStage());
                alert.showAndWait();
            }
            metodos.close();
        }
    }

    @FXML
    void acaoCancelar(ActionEvent event) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
        alert.setTitle("Aviso");
        alert.setHeaderText("Deseja voltar ao menu e descartar os dados não salvos?");
        alert.getButtonTypes().setAll(sim, nao);
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait().ifPresent(e->{
            if(e==sim){
                try {
                    new ScreenController().trocaJanela("/Diversos/Login.fxml");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> lista=FXCollections.observableArrayList("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
                                    "Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará",
                                    "Paraíba","Paraná","Pernambuco","Piauí", "Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul",
                                    "Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");
        comboEst.getItems().addAll(lista);
        
        lista=FXCollections.observableArrayList("Acupuntura", "Alergia e imunologia", "Anestesiologia", "Angiologia",
                "Cancerologia", "Cardiologia", "Cirurgia cardiovascular","Cirurgia da mão", "Cirurgia de cabeça e pescoço", "Cirurgia do aparelho digestivo",
                "Cirurgia geral", "Cirurgia pediátrica", "Cirurgia plástica", "Cirurgia torácica", "Cirurgia vascular",
                "Clínica médica", "Coloproctologia", "Dermatologia", "Endocrinologia e metabologia", "Endoscopia", "Gastroenterologia",
                "Genética médica", "Geriatria", "Ginecologia e obstetrícia", "Hematologia e hemoterapia", "Homeopatia", "Infectologia",
                "Mastologia", "Medicina de emergência", "Medicina de família e comunidade", "Medicina do trabalho", "Medicina de tráfego",
                "Medicina esportiva", "Medicina física e reabilitação","Medicina intensiva", "Medicina laboratorial","Medicina legal e perícia médica", "Medicina nuclear",
                "Medicina preventiva e social", "Nefrologia", "Neurocirurgia", "Neurologia", "Nutrologia", "Oftalmologia", "Ortopedia e traumatologia",
                "Otorrinolaringologia", "Patologia", "Patologia clínica", "Pediatria", "Pneumologia", "Psiquiatria",
                "Radiologia e diagnóstico por imagem", "Radioterapia", "Reumatologia", "Urologia");
        comboEsp.getItems().addAll(lista);
    }    
    
}
