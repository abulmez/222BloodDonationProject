package model;

import java.util.*;

/**
 * 
 */
public class Adress {

    /**
     * Default constructor
     */
    public Adress() {
    }

    public Adress(Integer idA, String street, Integer streetNumber, Integer blockNumber, Integer entrance, Integer floor, Integer apartmentNumber, String city, String county, String country) {
        this.idA = idA;
        this.street = street;
        this.streetNumber = streetNumber;
        this.blockNumber = blockNumber;
        this.entrance = entrance;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.county = county;
        this.country = country;
    }

    /**
     * 
     */
    private Integer idA;

    /**
     * 
     */
    private String street;

    /**
     * 
     */
    private Integer streetNumber;

    /**
     * 
     */
    private Integer blockNumber;

    /**
     * 
     */
    private Integer entrance;

    /**
     * 
     */
    private Integer floor;

    /**
     * 
     */
    private Integer apartmentNumber;

    /**
     * 
     */
    private String city;

    /**
     * 
     */
    private String county;

    /**
     * 
     */
    private String country;

    public Integer getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getEntrance() {
        return entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}