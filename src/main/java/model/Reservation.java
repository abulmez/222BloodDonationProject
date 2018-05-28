package model;

import com.google.gson.annotations.SerializedName;

public class Reservation {
    public Reservation() {
    }

    public Reservation(Integer idDS, Integer idU, String status) {
        this.idDS = idDS;
        this.idU = idU;
        this.status = status;
    }

    @SerializedName("idds")
    private Integer idDS;

    @SerializedName("idu")
    private Integer idU;

    @SerializedName("status")
    private String status;

    public Integer getIdDS(){
        return idDS;
    }

    public Integer getIdU(){
        return idU;
    }

    public void setIdDS(Integer idDS) {
        this.idDS = idDS;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idDS=" + idDS +
                ", idU=" + idU +
                ", status='" + status + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
