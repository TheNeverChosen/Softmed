/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import Objects.Consulta;
import Objects.ConsultaAux;
import Objects.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Fernando
 */
public class MetodosMed {
    
    private Connection coneccao;

    public MetodosMed() {
        start();
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Diversos----------------------------------------- //
    
    public void start(){
        this.coneccao = ConnectionFactory.getConnection();
    }
    
    public void close(){
        ConnectionFactory.closeConnection(coneccao);
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // -------------------------------Cadastros----------------------------------------------- //
    
    public void cadConsulta(LocalDate data, String horario){
        String sql="insert into datasMed values (default, ?, ?, ?, 0)";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            statement.setString(2, data.toString());
            statement.setString(3, horario);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Updates------------------------------------------ //
    
    public void updateMedico(String txt[]){
        String sql="update medicos set nome=?, sobrenome=?, email=?, estado=?, especialidade=? where login like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.setString(txt.length+1, Sessao.getLoginMed());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void updateEstadoCons(int id){
        String sql="update consultas set estado='Completa' where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    // --------------------------------------------------------------------------------------- //

    // --------------------------------------Getters------------------------------------------ //
    
    public ResultSet getMedico(){
        String sql="select * from medicos where login like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public ArrayList<LocalDate> getDatas(){
        String sql="select distinct dia from datasmed where medicos_login like ?";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            ResultSet resultado=statement.executeQuery();
            ArrayList<LocalDate> lista=new ArrayList<>();
            while(resultado.next()){
                lista.add(LocalDate.parse(resultado.getString("dia")));
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ObservableList<Horario> getHorarios(LocalDate dia){
        String sql="select id, time_format(horario, '%H : %i') as horario from datasMed where medicos_login like ? and dia like ? order by horario";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            statement.setString(2, dia.toString());
            ResultSet resultado=statement.executeQuery();
            ArrayList<Horario> lista=new ArrayList<>();
            
            while(resultado.next()){
                lista.add(new Horario(resultado.getInt("id"), resultado.getString("horario")));
            }
            
            return FXCollections.observableArrayList(lista);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ObservableList<Consulta> getConsultas(String s1, String s2, String s3){
        String sql="select c.id, c.datasmed_id as dataid, concat(p.nome,' ',p.sobrenome) as completo, c.especialidade, c.localizacao, c.estado from consultas c "
                + "join pacientes p on c.pacientes_login=p.login where c.medicos_login like ? and c.estado like ? and "
                + s2 +" like ?";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            statement.setString(2, "%"+s1+"%");
            statement.setString(3, "%"+s3+"%");
            ResultSet resultado=statement.executeQuery();
            ArrayList<Consulta> lista=new ArrayList<>();
            
            while(resultado.next()){
                lista.add(new Consulta(resultado.getInt("id"), resultado.getInt("dataid"), resultado.getString("completo"), resultado.getString("especialidade"),
                        resultado.getString("localizacao"), resultado.getString("estado")));
            }
            
            return FXCollections.observableArrayList(lista);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public ConsultaAux getConsultaFull(int id){
        String sql= "select c.especialidade, c.pacientes_login, m.nome as nomeMed, p.nome as nomePac from consultas c " +
                    "join pacientes p on c.pacientes_login=p.login " +
                    "join medicos m on c.medicos_login=m.login where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            ResultSet resultado=statement.executeQuery();
            resultado.next();
            return new ConsultaAux(resultado.getString("nomeMed"), resultado.getString("nomePac"),
                    resultado.getString("pacientes_login"), resultado.getString("especialidade"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
     // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Deletes------------------------------------------ //
    
    public void deletarMedico(){
        String sql[]={"delete from consultas where medicos_login like ?", "delete from datasMed where medicos_login like ?",
                    "delete from medicos where login like ?"};
        try {
            for(int i=0;i<sql.length;i++){
                PreparedStatement statement=coneccao.prepareStatement(sql[i]);
                statement.setString(1, Sessao.getLoginMed());
                statement.execute();
            }
            Sessao.setLoginMed(null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void deletarHorario(int id){
        String sql="delete from datasMed where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void deletarHorarios(LocalDate dia){
        String sql="delete from datasMed where medicos_login like ? and dia like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginMed());
            statement.setString(2, dia.toString());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void deletarConsulta(ObservableList<Consulta> lista){
        String sql[]={"delete from consultas where id=?", "update datasMed set usado=0 where id=?"};
        try {
            PreparedStatement statement[]={coneccao.prepareStatement(sql[0]), coneccao.prepareStatement(sql[1])};
            for(Consulta aux:lista){
                statement[0].setInt(1, aux.getId());
                statement[1].setInt(1, aux.getDataId());
                for(PreparedStatement xd:statement){
                    xd.execute();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    // --------------------------------------------------------------------------------------- //
    
}
