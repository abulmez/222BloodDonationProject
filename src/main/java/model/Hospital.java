package model;

import com.google.gson.annotations.SerializedName;

public class Hospital {

    public Hospital() {
    }

    public Hospital(Integer idH, Integer idA, String hospitalName, String phoneNumber) {
        this.idH = idH;
        this.idA = idA;
        this.hospitalName = hospitalName;
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     */
    @SerializedName("idh")
    private Integer idH;

    /**
     *
     */
    @SerializedName("ida")
    private Integer idA;
    /**
     *
     */
    @SerializedName("hospitalname")
    private String hospitalName;

    /**
     *
     */
    @SerializedName("phonenumber")
    private String phoneNumber;

    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }

    public Integer getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
