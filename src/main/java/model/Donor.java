package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Donor extends User {

    public Donor(Integer idU, Integer CNP, String name, LocalDate birthday, String mail, Integer phone, Integer idA, String bloodGroup, Float weight) {
        super(idU, CNP, name, birthday, mail, phone, idA);
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