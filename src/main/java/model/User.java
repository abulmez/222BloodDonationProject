package model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public abstract class User {

    public User(Integer idU, String cnp, String name, LocalDate birthday, String mail, String phone) {
        this.idU = idU;
        this.cnp = cnp;
        this.name = name;
        this.birthday = birthday;
        this.mail = mail;
        this.phone = phone;
    }

    /**
     * 
     */
    @SerializedName("idU")
    private Integer idU;

    /**
     * 
     */
    @SerializedName("cnp")
    private String cnp;

    /**
     * 
     */
    @SerializedName("name")
    private String name;

    /**
     * 
     */
    @SerializedName("birthday")
    private LocalDate birthday;

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
    public Integer getIdU() {
        return idU;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

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
}