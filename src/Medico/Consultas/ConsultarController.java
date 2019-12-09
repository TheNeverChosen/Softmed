/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Medico.Consultas;

import Banco.MetodosMed;
import Banco.Sessao;
import Objects.ConsultaAux;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class ConsultarController implements Initializable {

    @FXML
    private VBox vBox;
    
    @FXML
    private Label nomeMed;

    @FXML
    private Label nomePac;

    @FXML
    private Label especialidade;
    
    MetodosMed metodos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        metodos=new MetodosMed();
        ConsultaAux aux=metodos.getConsultaFull(Sessao.getId());
        Sessao.setLoginPac(aux.getLoginPac());
        metodos.close();
        
        nomeMed.setText("Nome do médico: "+aux.getNomeMed());
        nomePac.setText("Nome do paciente: "+aux.getNomePac());
        especialidade.setText("Especialidade da Consulta: "+aux.getEspecialidade());
    }
    
}
