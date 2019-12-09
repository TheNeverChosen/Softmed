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
public class Horario {
    
    private int id;
    private String horario;

    public Horario(int id, String time) {
        this.id = id;
        this.horario = time;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return horario;
    }    

    @Override
    public String toString() {
        return horario;
    }
    
}
