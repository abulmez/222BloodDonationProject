package model;

import java.util.*;

/**
 * 
 */
public class Donatie{

    /**
     * Default constructor
     */
    public Donatie() {
    }

    public Donatie(Integer idD, Integer idDC, Integer idU, Float cantitate, String status, String numeUtilizator) {
        this.idD = idD;
        this.idDC = idDC;
        this.idU = idU;
        this.cantitate = cantitate;
        this.status = status;
        this.numeUtilizator = numeUtilizator;
    }

    /**
     * 
     */
    private Integer idD;

    /**
     * 
     */
    private Integer idDC;

    /**
     * 
     */
    private Integer idU;

    /**
     * 
     */
    private Float cantitate;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String numeUtilizator;

    public Integer getIdD() {
        return idD;
    }

    public void setIdD(Integer idD) {
        this.idD = idD;
    }

    public Integer getIdDC() {
        return idDC;
    }

    public void setIdDC(Integer idDC) {
        this.idDC = idDC;
    }

    public Integer getIdU() {
        return idU;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    public Float getCantitate() {
        return cantitate;
    }

    public void setCantitate(Float cantitate) {
        this.cantitate = cantitate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }
}