/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Fernando
 */
public class ConsultaAux {
    
    String nomeMed, nomePac, loginPac, especialidade;

    public ConsultaAux(String nomeMed, String nomePac, String loginPac, String especialidade) {
        this.nomeMed = nomeMed;
        this.nomePac = nomePac;
        this.loginPac = loginPac;
        this.especialidade = especialidade;
    }

    public String getNomeMed() {
        return nomeMed;
    }

    public String getNomePac() {
        return nomePac;
    }

    public String getLoginPac() {
        return loginPac;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    
    
    
    
}
