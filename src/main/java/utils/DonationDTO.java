package utils;

import com.google.gson.annotations.SerializedName;

public class DonationDTO {
    @SerializedName("idD")
    private Integer idD;
    @SerializedName("name")
    private String name;
    @SerializedName("idU")
    private Integer idU;
    @SerializedName("status")
    private String status;
    @SerializedName("quantity")
    private Double quantity;
    public DonationDTO(Integer idD,String name,Integer idU,String status,Double quantity){
        this.idD=idD;
        this.name=name;
        this.idU=idU;
        this.status=status;
        this.quantity=quantity;
    }

    public Integer getIdD() {
        return idD;
    }

    public String getName() {
        return name;
    }

    public Integer getIdU() {
        return idU;
    }

    public String getStatus() {
        return status;
    }

    public Double getQuantity() {
        return quantity;
    }

}
