/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Fernando
 */
public class Consulta {
    
    private int id, dataId;
    private String nome, especialidade, localizacao, estado;

    public Consulta(int id, int dataId, String nome, String especialidade, String localizacao, String estado) {
        this.id = id;
        this.dataId = dataId;
        this.nome = nome;
        this.especialidade = especialidade;
        this.localizacao = localizacao;
        this.estado = estado;
    }

    public int getDataId() {
        return dataId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getEstado() {
        return estado;
    }
   
}
