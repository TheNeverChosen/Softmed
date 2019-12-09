/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paciente.Consultas;

import Banco.MetodosMed;
import Banco.MetodosPac;
import Objects.Consulta;
import Objects.Horario;
import Objects.Medico;
import ScreenFramework.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javax.swing.DefaultListModel;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class MarcarConsController implements Initializable {

    @FXML
    private VBox vboxL;
    
    @FXML
    private Button agenda;
    
    @FXML
    private ComboBox<String> comboEst;

    @FXML
    private ComboBox<String> comboEsp;

    @FXML
    private TableView<Medico> table;
    
    @FXML
    private VBox vboxR;

    @FXML
    private DatePicker calendario;

    @FXML
    private ComboBox<Horario> comboHora;

    @FXML
    private Button marca;
    
    MetodosPac metodos;
    
    ArrayList<LocalDate> datas;

    @FXML
    void carregarMedicos(ActionEvent event) {
        if(!comboEst.getSelectionModel().isEmpty() && !comboEsp.getSelectionModel().isEmpty()){
            metodos.start();
            table.setItems(metodos.getMedicos(comboEst.getSelectionModel().getSelectedItem(), comboEsp.getSelectionModel().getSelectedItem()));
            metodos.close();
        }
        vboxR.setVisible(false);
        table.getSelectionModel().clearSelection();
    }
    
    @FXML
    void carregarDataHora(MouseEvent event) {
        if(!table.getSelectionModel().isEmpty()){
            vboxR.setVisible(true);

            metodos.start();
            datas=metodos.getDatas(table.getSelectionModel().getSelectedItem().getLogin());
            metodos.close();
            final Callback<DatePicker, DateCell> dayCellFactory = 
                (final DatePicker datePicker) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if(item.isBefore(LocalDate.now()) || !datas.contains(item)){
                            setDisable(true);
                        }
                        else{
                            setStyle("-fx-background-color: #66dd88;");
                        }
                        
                    }
                };
            calendario.setDayCellFactory(dayCellFactory);
            atualizaHorarios();
        }
        else vboxR.setVisible(false);
    }
    
    @FXML
    void clicaCalendario(ActionEvent event) {
        atualizaHorarios();
    }
    
    @FXML
    void marcarCons(ActionEvent event) {
        metodos.start();
        metodos.cadCons(comboHora.getSelectionModel().getSelectedItem().getId());
        atualizaHorarios();
        metodos.start();
        datas=metodos.getDatas(table.getSelectionModel().getSelectedItem().getLogin());
        metodos.close();
        
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação sobre consulta");
        alert.setHeaderText("Consulta marcada com sucesso!");
        alert.setContentText("Confira a Agenda de Consultas para mais Detalhes");
        alert.initOwner(ScreenController.getStage());
        alert.showAndWait();
    }
    
    @FXML
    void mudaAgenda(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderEsquerda("/Paciente/Consultas/Historico.fxml");
    }
    
    void atualizaHorarios(){
        metodos.start();
        comboHora.setItems(metodos.getHorarios(calendario.getValue(), table.getSelectionModel().getSelectedItem().getLogin()));
        metodos.close();
        if(comboHora.getItems().isEmpty()){
            comboHora.setVisible(false);
            marca.setVisible(false);
            calendario.setValue(null);
        }
        else{
            comboHora.getSelectionModel().select(0);
            comboHora.setVisible(true);
            marca.setVisible(true);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> lista=FXCollections.observableArrayList("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
                                    "Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará",
                                    "Paraíba","Paraná","Pernambuco","Piauí", "Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul",
                                    "Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");
        comboEst.setItems(lista);
        
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
        comboEsp.setItems(lista);
    
        TableColumn colunas[]={new TableColumn("Nome"), new TableColumn("CRM")};
        colunas[0].setPrefWidth(225); colunas[1].setPrefWidth(225);
        table.getColumns().setAll(colunas[0], colunas[1]);
        colunas[0].setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunas[1].setCellValueFactory(new PropertyValueFactory<>("crm"));
        
        
        metodos=new MetodosPac();
        metodos.close();
    }    
    
}
