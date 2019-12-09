/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import ScreenFramework.ScreenController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Fernando
 */
public class MetodosGerais {
    
    private Connection coneccao;

    public MetodosGerais() {
        this.coneccao = ConnectionFactory.getConnection();
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Diversos----------------------------------------- //
    
    public void start(){
        this.coneccao = ConnectionFactory.getConnection();
    }
    
    public void close(){
        ConnectionFactory.closeConnection(coneccao);
    }
    
    public void Logar(String tipo, String login, String senha) throws IOException{
        String sql="Select nome from "+tipo+" where login like ? and senha like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, senha);
            ResultSet resultado=statement.executeQuery();
            if(resultado.next()){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bem-vindo");
                alert.setHeaderText("Login bem-sucedido");
                alert.setContentText("Bem-vindo "+resultado.getString("nome")+"!");
                alert.initOwner(ScreenController.getStage());
                alert.showAndWait();
                
                new ScreenController().modoCheia(tipo);
                
                if(tipo.equals("pacientes")) Sessao.setLoginPac(login);
                else Sessao.setLoginMed(login);
            }
            else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login mal-sucedido");
                alert.setHeaderText("Credenciais inválidas");
                alert.initOwner(ScreenController.getStage());
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        close();
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // ---------------------------------Cadastros--------------------------------------------- //
    
    public boolean cadPaciente(String txt[]){
        String sql="insert into pacientes (nome, sobrenome, rg, cpf, email, login, senha, estado) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean cadMedico(String txt[]){
        String sql="insert into medicos (nome, sobrenome, rg, cpf, crm, email, login, senha, estado, especialidade) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    
    // --------------------------------------------------------------------------------------- //
    
}
