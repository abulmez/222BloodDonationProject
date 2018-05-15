package model.DTO;

import com.google.gson.annotations.SerializedName;

public class BloodRequestDTO {
    /**
     * Default constructor
     */
    public BloodRequestDTO() {
    }

    public BloodRequestDTO(Integer idBD, Integer idH, String neededType, String description, String priority, Double quantity, String bloodType,Double delivered,String status) {
        this.idBD = idBD;
        this.idH = idH;
        NeededType = neededType;
        Description = description;
        Priority = priority;
        Quantity = quantity;
        BloodProductType = bloodType;
        this.Delivered=delivered;
        this.Status=status;
    }


    @SerializedName("idbd")
    private Integer idBD;
    @SerializedName("idh")
    private Integer idH;
    @SerializedName("neededtype")
    private String NeededType;

    @SerializedName("description")
    private String Description;

    @SerializedName("priority")
    private String Priority;

    @SerializedName("quantity")
    private Double Quantity;

    @SerializedName("bloodproducttype")
    private String BloodProductType;

    @SerializedName("delivered")
    private Double Delivered;

    @SerializedName("status")
    private String Status;
    public Double getDelivered() {
        return Delivered;
    }

    public void setDelivered(Double cantitateLivrata) {
        Delivered = cantitateLivrata;
    }



    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


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
