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
        this.cantitate = quantity;
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
    private Double cantitate;

    /**
     * 
     */
    @SerializedName("status")
    private String status;

    /**
     * 
     */
    @SerializedName("receivername")
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

    public String getReceiverName() {
        return receiverName;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "idD=" + idD +
                ", idDC=" + idDC +
                ", idU=" + idU +
                ", cantitate=" + cantitate +
                ", status='" + status + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}