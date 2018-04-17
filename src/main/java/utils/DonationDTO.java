package utils;

import com.google.gson.annotations.SerializedName;

public class DonationDTO {
    @SerializedName("idD")
    private Integer idD;
    @SerializedName("name")
    private String name;
    @SerializedName("idU")
    private Integer idU;
    @SerializedName("cnp")
    private String cnp;
    @SerializedName("status")
    private String status;
    @SerializedName("quantity")
    private Double quantity;
    public DonationDTO(Integer idD,String name,Integer idU,String cnp,String status,Double quantity){
        this.idD=idD;
        this.name=name;
        this.idU=idU;
        this.cnp=cnp;
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

    public String getCnp() {
        return cnp;
    }

    public String getStatus() {
        return status;
    }

    public Double getQuantity() {
        return quantity;
    }

}
