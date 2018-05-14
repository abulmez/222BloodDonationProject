package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Donor extends User {

    public Donor(Integer idU, String cnp, String name, LocalDate birthday, String mail, String phone, String bloodGroup, Double weight) {
        super(idU, cnp, name, birthday, mail, phone);
        this.bloodGroup = bloodGroup;
        this.weight = weight;
    }

    /**
     * 
     */
    private String bloodGroup;

    /**
     * 
     */
    private Double weight;


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
}