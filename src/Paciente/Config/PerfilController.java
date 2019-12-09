/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Config;

import Banco.MetodosPac;
import Banco.Sessao;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

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
    private ComboBox<String> comboEst;

    @FXML
    private Button delete;

    @FXML
    private Button save;

    MetodosPac metodos;

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
                    metodos.deletarPaciente();
                    metodos.close();
                    new ScreenController().modoJanela();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @FXML
    void salvar(ActionEvent event) {
        if(t1.getText().equals("") || t2.getText().equals("") || t5.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Preencha todos os campos");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            String txt[]={t1.getText(), t2.getText(), t5.getText(), comboEst.getSelectionModel().getSelectedItem()};
            metodos.start();
            metodos.updatePaciente(txt);
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
        
        metodos=new MetodosPac();
        ResultSet resultado=metodos.getPaciente();
        
        try {
            resultado.next();
            t1.setText(resultado.getString("nome"));
            t2.setText(resultado.getString("sobrenome"));
            t3.setText(resultado.getString("rg"));
            t4.setText(resultado.getString("cpf"));
            t5.setText(resultado.getString("email"));
            comboEst.getSelectionModel().select(resultado.getString("estado"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        metodos.close();
    }    
    
}
