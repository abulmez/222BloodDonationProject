package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public abstract class User {

    User(Integer idU, Integer CNP, String name, LocalDate birthday, String mail, Integer phone, Integer idA) {
        IdU = idU;
        this.CNP = CNP;
        Name = name;
        Birthday = birthday;
        Mail = mail;
        Phone = phone;
        IdA = idA;
    }

    /**
     * 
     */
    private Integer IdU;

    /**
     * 
     */
    private Integer CNP;

    /**
     * 
     */
    private String Name;

    /**
     * 
     */
    private LocalDate Birthday;

    /**
     * 
     */
    private String Mail;

    /**
     * 
     */
    private Integer Phone;

    /**
     * 
     */
    private Integer IdA;

    public Integer getIdU() {
        return IdU;
    }

    public void setIdU(Integer idU) {
        IdU = idU;
    }

    public Integer getCNP() {
        return CNP;
    }

    public void setCNP(Integer CNP) {
        this.CNP = CNP;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDate getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public Integer getPhone() {
        return Phone;
    }

    public void setPhone(Integer phone) {
        Phone = phone;
    }

    public Integer getIdA() {
        return IdA;
    }

    public void setIdA(Integer idA) {
        IdA = idA;
    }
}