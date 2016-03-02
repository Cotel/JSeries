/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cotel.jseries.models;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Modelo SERIE
 * 
 * @author Cotel
 */
public class Mes {
    
    private String nombreMes;
    private int numMes;
    private int numAno;
    private int diasMes;
    
    /**
     *  Constructor por defecto
     *  Crea un objeto Mes con el mes actual
     */
    public Mes() {
        Date fecha = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        
        this.numAno = cal.get(Calendar.YEAR);
        this.numMes = cal.get(Calendar.MONTH);

        this.nombreMes = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        this.diasMes = cal.getActualMaximum(Calendar.DATE);
        
    }
    
    /**
     *  Constructor
     *  Crea un objeto Mes con el año y mes especificados
     * @param numAno = numero de año
     * @param numMes = numero de mes
     */
    public Mes(int numAno, int numMes) {
        this.numAno = (numAno);
        this.numMes = (numMes);
        Date fecha = new Date(numAno, numMes, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        this.nombreMes = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        this.diasMes = cal.getActualMaximum(Calendar.DATE);
        
    }
    
    /**
     * Getters y Setters
     */
    
    public String getNombreMes() {
        return this.nombreMes;
    }
    public int getNumMes() {
        return this.numMes;
    }
    public int getNumAno() {
        return this.numAno;
    }
    public int getDiasMes() {
        return this.diasMes;
    }
    
}
