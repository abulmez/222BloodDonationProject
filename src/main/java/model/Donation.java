package model;

import java.util.*;

/**
 * 
 */
public class Donation {

    /**
     * Default constructor
     */
    public Donation() {
    }

    public Donation(Integer idD, Integer idDC, Integer idU, Double quantity, String status) {
        this.idD = idD;
        this.idDC = idDC;
        this.idU = idU;
        this.cantitate = quantity;
        this.status = status;
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
    private Double cantitate;

    /**
     * 
     */
    private String status;

    /**
     * 
     */

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

    public Double getCantitate() {
        return cantitate;
    }

    public void setCantitate(Double cantitate) {
        this.cantitate = cantitate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}