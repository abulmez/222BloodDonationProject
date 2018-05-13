package model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Donor extends User {

    public Donor(Integer idU, String cnp, String name, LocalDate birthday, String mail, String phone, String bloodGroup, Float weight) {
        super(idU, cnp, name, birthday, mail, phone);
        this.bloodGroup = bloodGroup;
        this.weight = weight;
    }

    /**
     * 
     */
    @SerializedName("bloodGroup")
    private String bloodGroup;

    /**
     * 
     */
    @SerializedName("weight")
    private Float weight;


    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}