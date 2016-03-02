/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cotel.jseries.models;

import java.io.Serializable;

/**
 *
 * @author Cotel
 */
public class Capitulo implements Serializable {
    
    private String titulo;
    private String fecha;
    private Boolean visto;
    
    public Capitulo(String titulo, String fecha, boolean visto) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.visto = visto;
        
    }
    
    public void changeVisto() {
        if(this.visto == true) {
            this.visto = false;
        } else {
            this.visto = true;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

}
