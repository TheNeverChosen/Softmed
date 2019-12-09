
package Paciente.Consultas;

import Banco.MetodosPac;
import Banco.Sessao;
import Objects.Atividade;
import Objects.Consulta;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Fernando
 */
public class HistoricoController implements Initializable {

    @FXML
    private Button marcar;

    @FXML
    private ComboBox<String> comboBus;

    @FXML
    private TextField tbus;

    @FXML
    private Button desmExc;

    @FXML
    private Button visualiza;

    @FXML
    private ComboBox<String> comboFilt;

    @FXML
    private Button reset;

    @FXML
    private TableView<Consulta> table;
    
    String estados[]={"", "Completa", "Pendente"}, formas[]={"c.especialidade", "localizacao", "concat(m.nome,' ',m.sobrenome)"};
    
    MetodosPac metodos;

    @FXML
    void buscar(KeyEvent event) {
        metodos.start();
        
        table.setItems(metodos.getConsultas(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void filtrar(ActionEvent event) {
        String formado="''";
 
        metodos.start();      
        
        if(tbus.isEditable()) formado=formas[comboBus.getSelectionModel().getSelectedIndex()];
        
        table.setItems(metodos.getConsultas(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formado, tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void marcarCons(ActionEvent event) throws IOException {
        new ScreenController().mudaBorderDireita("/Paciente/Consultas/MarcarCons.fxml");
    }

    @FXML
    void resetar(ActionEvent event) {
        comboFilt.getSelectionModel().select(0);
        comboBus.getSelectionModel().clearSelection();
        
        tbus.setEditable(false);
        tbus.setText("");
        
        metodos.start();
        
        table.setItems(metodos.getConsultas("","''", ""));
        
        metodos.close();
    }
    
    @FXML
    void retirar(ActionEvent event) {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione alguma consulta para realizar a ação");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType sim=new ButtonType("Sim"), nao=new ButtonType("Não");
            alert.setTitle("Aviso");
            alert.setHeaderText("Deseja excluir a(s) consulta(s) selecionada(s)?");
            alert.setContentText("A ação retirará ela(s) do seu histórico permanentemente.\nConsultas pendentes serão desmarcadas e retiradas.");
            alert.getButtonTypes().setAll(sim, nao);
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait().ifPresent(e->{
                if(e==sim){
                    ObservableList<Consulta> lista=table.getSelectionModel().getSelectedItems();
                    metodos.start();
                    metodos.deletarConsulta(lista);
                    metodos.close();
                    table.getItems().removeAll(lista);
                }
            });
        }
    }

    @FXML
    void visivel(ActionEvent event) {
        tbus.setEditable(true);
        metodos.start();
        
        table.setItems(metodos.getConsultas(estados[comboFilt.getSelectionModel().getSelectedIndex()],
                    formas[comboBus.getSelectionModel().getSelectedIndex()], tbus.getText()));
        
        metodos.close();
    }

    @FXML
    void visualizar(ActionEvent event) throws IOException {
        if(table.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Selecione alguma consulta para visualizar");
            alert.initOwner(ScreenController.getStage());
            alert.showAndWait();
        }
        else{
            Sessao.setId(table.getSelectionModel().getSelectedItem().getId());
            new ScreenController().mudaBorderDireita("/Visualizadores/VisCons.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TableColumn colunas[]={new TableColumn("Médico"), new TableColumn("Especialidade"), new TableColumn("Local"),
                                new TableColumn("Estado")};
        
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().clear();
        for(int i=0;i<colunas.length;i++) table.getColumns().add(colunas[i]);
        colunas[0].setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunas[1].setCellValueFactory(new PropertyValueFactory<>("especialidade"));
        colunas[2].setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colunas[3].setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        metodos=new MetodosPac();
        table.setItems(metodos.getConsultas("", "''", ""));
        metodos.close();
        
        ObservableList<String> lista=FXCollections.observableArrayList("Especialidade", "Local", "Médico");
        comboBus.getItems().addAll(lista);
        lista=FXCollections.observableArrayList("Todas", "Completa", "Pendente");
        comboFilt.setItems(lista);
        comboFilt.getSelectionModel().selectFirst();
        
    }    
    
}
