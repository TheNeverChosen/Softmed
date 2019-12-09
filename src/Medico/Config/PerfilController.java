/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Medico.Config;

import Banco.MetodosMed;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class PerfilController implements Initializable {

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
    private ComboBox<String> comboEst;

    @FXML
    private ComboBox<String> comboEsp;

    @FXML
    private Button delete;

    @FXML
    private Button save;
    
    MetodosMed metodos;

    @FXML
    void deletar(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
        alert.setTitle("Aviso");
        alert.setHeaderText("Deseja deletar sua conta e perder todos os seus dados? (Isto inclui o histórico de consultas e médico geral).\nEsta ação não pode ser desfeita.");
        alert.getButtonTypes().setAll(sim, nao);
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait().ifPresent(e->{
            if(e==sim){
                try {
                    metodos.start();
                    metodos.deletarMedico();
                    metodos.close();
                    new ScreenController().modoJanela();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(0);
                }
            }
        });
    }

    @FXML
    void salvar(ActionEvent event) {
        if(t1.getText().equals("") || t2.getText().equals("") || t6.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            String txt[]={t1.getText(), t2.getText(), t6.getText(), comboEst.getSelectionModel().getSelectedItem(),
                        comboEsp.getSelectionModel().getSelectedItem()};
            metodos.start();
            metodos.updateMedico(txt);
            metodos.close();
        }
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
        
        metodos=new MetodosMed();
        ResultSet resultado=metodos.getMedico();
        
        try {
            resultado.next();
            t1.setText(resultado.getString("nome"));
            t2.setText(resultado.getString("sobrenome"));
            t3.setText(resultado.getString("rg"));
            t4.setText(resultado.getString("cpf"));
            t5.setText(resultado.getString("crm"));
            t6.setText(resultado.getString("email"));
            comboEst.getSelectionModel().select(resultado.getString("estado"));
            comboEsp.getSelectionModel().select(resultado.getString("especialidade"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        metodos.close();
        
    }    
    
}
