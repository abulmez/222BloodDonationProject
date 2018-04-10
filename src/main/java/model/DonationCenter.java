package model;

import java.util.*;

/**
 * 
 */
public class DonationCenter {

    /**
     * Default constructor
     */
    public DonationCenter() {
    }

    public DonationCenter(Integer idDC, Integer idA, String centerName, String phoneNumber) {
        this.idDC = idDC;
        this.idA = idA;
        this.centerName = centerName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     */
    private Integer idDC;

    /**
     * 
     */
    private Integer idA;

    /**
     * 
     */
    private String centerName;

    /**
     * 
     */
    private String phoneNumber;

    public Integer getIdDC() {
        return idDC;
    }

    public void setIdDC(Integer idDC) {
        this.idDC = idDC;
    }

    public Integer getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}