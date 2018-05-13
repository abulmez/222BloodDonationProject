package model;

import com.google.gson.annotations.SerializedName;

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

    public Donation(Integer idD, Integer idDC, Integer idU, Double quantity, String status,String receiverName) {
        this.idD = idD;
        this.idDC = idDC;
        this.idU = idU;
        this.quantity = quantity;
        this.status = status;
        this.receiverName=receiverName;
    }

    /**
     * 
     */
    @SerializedName("idd")
    private Integer idD;

    /**
     * 
     */
    @SerializedName("iddc")
    private Integer idDC;

    /**
     * 
     */
    @SerializedName("idu")
    private Integer idU;

    /**
     * 
     */
    @SerializedName("quantity")
    private Double quantity;

    /**
     * 
     */
    @SerializedName("status")
    private String status;

    /**
     * 
     */
    @SerializedName("receiverName")
    private String receiverName;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}