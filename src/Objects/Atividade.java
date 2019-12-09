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
public class Atividade {
    
    private int id;
    private String titulo, descricao, frequencia, estado;

    public Atividade(int id, String titulo, String descricao, String frequencia, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.frequencia = frequencia;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Atividade{" + "id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", frequencia=" + frequencia + ", estado=" + estado + '}';
    }
    
    

}
