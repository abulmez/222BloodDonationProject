package model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class UserPacientDTO {
    public UserPacientDTO() {
    }

    public UserPacientDTO(Integer idU, String CNP, String name, /*LocalDate birthday,*/ String mail, String phone, String bloodGroup, Double weight, Integer idA, Integer idDC, Integer idH) {
        this.idU = idU;
        this.CNP = CNP;
        this.name = name;
        //this.birthday = birthday;
        this.mail = mail;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
        this.idA = idA;
        this.idDC = idDC;
        this.idH = idH;
    }

    /**
     *
     */


    @SerializedName("idu")
    private Integer idU;

    /**
     *
     */
    @SerializedName("cnp")
    private String CNP;

    /**
     *
     */
    @SerializedName("name")
    private String name;

    /**
     *
     */
    /*@SerializedName("birthday")
    private LocalDate birthday;*/

    /**
     *
     */
    @SerializedName("mail")
    private String mail;

    /**
     *
     */
    @SerializedName("phone")
    private String phone;

    /**
     *
     */
    @SerializedName("bloodgroup")
    private String bloodGroup;

    /**
     *
     */
    @SerializedName("weight")
    private Double weight;

    /**
     *
     */
    @SerializedName("ida")
    private Integer idA;

    /**
     *
     */
    @SerializedName("iddc")
    private Integer idDC;

    @SerializedName("idh")
    private Integer idH;

    public Integer getIdU() {
        return idU;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }*/

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    public Integer getIdDC() {
        return idDC;
    }

    public void setIdDC(Integer idDC) {
        this.idDC = idDC;
    }

    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }
}
