/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import Objects.Atividade;
import Objects.Consulta;
import Objects.Horario;
import Objects.Medico;
import Objects.Problema;
import Objects.RecObs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Fernando
 */
public class MetodosPac {

    private Connection coneccao;

    public MetodosPac(){
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
    
    public void cadAtividade(String txt[]){
        String sql="insert into atividades values (default, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            for(int i=0;i<txt.length;i++) statement.setString(i+2, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void cadRecObs(String txt[]){
        String sql="insert into reco_obs values (default, ?, ?, ?, ?);";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            for(int i=0;i<txt.length;i++) statement.setString(i+2, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void cadProblema(String txt[]){
        String sql="insert into problemas values (default, ?, ?, ?, ?);";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            for(int i=0;i<txt.length;i++) statement.setString(i+2, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void cadCons(int id){
        String sql="update datasMed set usado=1 where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.execute();
            
            sql="select medicos_login, dia, horario from datasMed where id="+id;
            statement=coneccao.prepareStatement(sql);
            ResultSet dataMed=statement.executeQuery();
            dataMed.next();
             
            sql="select especialidade, estado from medicos where login like ?";
            statement=coneccao.prepareStatement(sql);
            statement.setString(1, dataMed.getString("medicos_login"));
            ResultSet medico=statement.executeQuery();
            medico.next();
            
            sql="insert into consultas values (default,"+id+", ?, ?, ?, ?, ?, ?, 'Pendente')";
            statement=coneccao.prepareStatement(sql);
            statement.setString(1, dataMed.getString("medicos_login"));
            statement.setString(2, Sessao.getLoginPac());
            statement.setString(3, medico.getString("especialidade"));
            statement.setString(4, dataMed.getString("dia"));
            statement.setString(5, medico.getString("estado"));
            statement.setString(6, dataMed.getString("horario"));
            statement.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Updates------------------------------------------ //
    
    public void updatePaciente(String txt[]){
        String sql="update pacientes set nome=?, sobrenome=?, email=?, estado=? where login like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.setString(txt.length+1, Sessao.getLoginPac());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void updateAtividade(String txt[], int id){
        String sql="update atividades set titulo=?, descricao=?, frequencia=?, estado=? where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void updateRecObs(String txt[], int id){
        String sql="update reco_obs set titulo=?, descricao=?, categoria=? where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void updateProblema(String txt[], int id){
        String sql="update problemas set titulo=?, descricao=?, categoria=? where id="+id;
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            for(int i=0;i<txt.length;i++) statement.setString(i+1, txt[i]);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    // --------------------------------------------------------------------------------------- //

    // --------------------------------------Getters------------------------------------------ //
    
    public ObservableList<Atividade> getAtividades(String s1, String s2, String s3){
        String sql="select * from atividades where pacientes_login like ? and estado like ? and "+s2+" like ?";
        try {
            PreparedStatement statement = coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            statement.setString(2, "%"+s1+"%");
            statement.setString(3, "%"+s3+"%");
            ResultSet resultado=statement.executeQuery();
            ArrayList<Atividade> data=new ArrayList<>();
            
            while(resultado.next()){
                data.add(new Atividade(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"), resultado.getString("frequencia"),
                        resultado.getString("estado")));
            }
            
            return FXCollections.observableArrayList(data);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public Atividade getAtividade(int id){
        String sql="select * from atividades where id="+id;
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            ResultSet resultado=statement.executeQuery();
            resultado.next();
            return new Atividade(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"), resultado.getString("frequencia"),
                        resultado.getString("estado"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ObservableList<RecObs> getRecObs(String s1, String s2){
        String sql="select * from reco_obs where pacientes_login like ? and "+s1+" like ?";
        try {
            PreparedStatement statement = coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            statement.setString(2, "%"+s2+"%");
            ResultSet resultado=statement.executeQuery();
            ArrayList<RecObs> data=new ArrayList<>();
            
            while(resultado.next()){
                data.add(new RecObs(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"),
                        resultado.getString("categoria")));
            }
            
            return FXCollections.observableArrayList(data);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public RecObs getRecObs(int id){
        String sql="select * from reco_obs where id="+id;
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            ResultSet resultado=statement.executeQuery();
            resultado.next();
            return new RecObs(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"),
                        resultado.getString("categoria"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ObservableList<Problema> getProblemas(String s1, String s2){
        String sql="select * from problemas where pacientes_login like ? and "+s1+" like ?";
        try {
            PreparedStatement statement = coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            statement.setString(2, "%"+s2+"%");
            ResultSet resultado=statement.executeQuery();
            ArrayList<Problema> data=new ArrayList<>();
            
            while(resultado.next()){
                data.add(new Problema(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"),
                        resultado.getString("categoria")));
            }
            
            return FXCollections.observableArrayList(data);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public Problema getProblema(int id){
        String sql="select * from problemas where id="+id;
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            ResultSet resultado=statement.executeQuery();
            resultado.next();
            return new Problema(resultado.getInt("id"), resultado.getString("titulo"), resultado.getString("descricao"),
                        resultado.getString("categoria"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ResultSet getPaciente(){
        String sql="select * from pacientes where login like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return null;
    }
    
    public ObservableList<Medico> getMedicos(String estado, String especialidade){
        String sql="select login, concat(nome,' ',sobrenome) as completo, crm from medicos where estado like ? and especialidade like ? order by completo";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, estado);
            statement.setString(2, especialidade);
            ResultSet resultado=statement.executeQuery();
            ArrayList<Medico> lista=new ArrayList<>();
            
            while(resultado.next()){
                lista.add(new Medico(resultado.getString("login"), resultado.getString("completo"),resultado.getString("crm")));
            }
            
            return FXCollections.observableArrayList(lista);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    public ArrayList<LocalDate> getDatas(String login){
        String sql="select distinct dia from datasmed where medicos_login like ? and usado=0";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, login);
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
    
    public ObservableList<Horario> getHorarios(LocalDate dia, String login){
        String sql="select id, time_format(horario, '%H : %i') as horario from datasMed where medicos_login like ? and dia like ? and usado=0 order by horario";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, login);
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
        String sql="select c.id, c.datasmed_id as dataid, concat(m.nome,' ',m.sobrenome) as completo, c.especialidade, c.localizacao, c.estado from consultas c "
                + "join medicos m on c.medicos_login=m.login where c.pacientes_login like ? and c.estado like ? and "
                + s2 +" like ?";
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            statement.setString(1, Sessao.getLoginPac());
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
    
    public ResultSet getConsulta(){
        String sql="select concat(m.nome,' ', m.sobrenome) as nomeMed, m.crm, concat(p.nome,' ',p.sobrenome) as nomePac, c.datasmed_id as dataid,"
                + "c.especialidade, c.dataCons, c.localizacao, time_format(c.horario, '%H : %i') as horario, c.estado from consultas c "
                + "join pacientes p on p.login=c.pacientes_login join medicos m on m.login=c.medicos_login where c.id="+Sessao.getId();
        
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            ResultSet resultado=statement.executeQuery();
            resultado.next();
            return resultado;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        return null;
    }
    
    // --------------------------------------------------------------------------------------- //
    
    // --------------------------------------Deletes------------------------------------------ //
    
    public void deletarPaciente(){
        String sql[]={"delete from atividades where pacientes_login like ?", "delete from reco_obs where pacientes_login like ?",
                    "delete from problemas where pacientes_login like ?", "delete from consultas where pacientes_login like ?",
                    "delete from pacientes where login like ?"};
        try {
            for(int i=0;i<sql.length;i++){
                PreparedStatement statement=coneccao.prepareStatement(sql[i]);
                statement.setString(1, Sessao.getLoginPac());
                statement.execute();
            }
            Sessao.setLoginPac(null);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public void deletarAtividade(ObservableList<Atividade> lista){
        String sql="delete from atividades where id like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            
            for(int i=0;i<lista.size();i++){
                statement.setString(1, String.valueOf(lista.get(i).getId()));
                statement.execute();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        } 
    }
    
    public void deletarRecObs(ObservableList<RecObs> lista){
        String sql="delete from reco_obs where id like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            
            for(int i=0;i<lista.size();i++){
                statement.setString(1, String.valueOf(lista.get(i).getId()));
                statement.execute();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        } 
    }
    
    public void deletarProblema(ObservableList<Problema> lista){
        String sql="delete from problemas where id like ?";
        try {
            PreparedStatement statement=coneccao.prepareStatement(sql);
            
            for(int i=0;i<lista.size();i++){
                statement.setString(1, String.valueOf(lista.get(i).getId()));
                statement.execute();
            }
            
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
