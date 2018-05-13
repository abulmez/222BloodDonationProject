package model;

import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * 
 */
public class BloodRequest {

    /**
     * Default constructor
     */
    public BloodRequest() {
    }

    public BloodRequest(Integer idBD, Integer idH, String neededType, String description, String priority, Double quantity, String bloodType) {
        this.idBD = idBD;
        this.idH = idH;
        NeededType = neededType;
        Description = description;
        Priority = priority;
        Quantity = quantity;
        BloodProductType = bloodType;
    }

    /**
     * 
     */
    @SerializedName("idbd")
    private Integer idBD;

    /**
     * 
     */
    @SerializedName("idh")
    private Integer idH;

    /**
     * 
     */
    @SerializedName("neededtype")
    private String NeededType;

    /**
     * 
     */
    @SerializedName("description")
    private String Description;

    /**
     * 
     */
    @SerializedName("priority")
    private String Priority;

    /**
     * 
     */
    @SerializedName("quantity")
    private Double Quantity;

    @SerializedName("bloodproducttype")
    private String BloodProductType;


    public Integer getIdBD() {
        return idBD;
    }

    public void setIdBD(Integer idBD) {
        this.idBD = idBD;
    }

    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }

    public String getNeededType() {
        return NeededType;
    }

    public void setNeededType(String neededType) {
        NeededType = neededType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public void setBloodProductType(String bloodType){
        BloodProductType= bloodType;
    }

    public String getBloodProductType(){
        return BloodProductType;
    }
}