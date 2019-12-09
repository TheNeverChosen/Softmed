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
public class Medico {
    
    private String login, nome, crm;

    public Medico(String login, String completo, String crm) {
        this.login = login;
        this.nome = completo;
        this.crm=crm;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }
    
}
