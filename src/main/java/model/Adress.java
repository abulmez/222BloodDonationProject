package model;

import com.google.gson.annotations.SerializedName;

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

    public Adress(Integer idA, String street, Integer streetNumber, Integer blockNumber, String entrance, Integer floor, Integer apartmentNumber, String city, String county, String country) {
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

    public String toStringSmall(){
        return this.country+", "+city+", "+this.county+", "+this.street+", "+this.streetNumber;
    }
    private boolean verifyField(Object object){
        if(object!=null&&!object.equals(""))
            return true;
        return false;
    }
    public String getFullAdress(){
        String string= ""+country+", " + city+", "+county+", "+"strada:"+street+", ";
        if(verifyField(streetNumber)) string+="nr. stradă: "+streetNumber+", ";
        if(verifyField(blockNumber)) string+="nr. bloc: "+blockNumber+", ";
        if(verifyField(entrance)) string+="intrare: "+entrance+", ";
        if(verifyField(floor)) string+="etaj: "+floor+", ";
        if(verifyField(apartmentNumber)) string+="apartament: "+apartmentNumber+", ";


        return string;
    }

    /**
     * 
     */


    @SerializedName("ida")
    private Integer idA;

    /**
     * 
     */
    @SerializedName("street")
    private String street;

    /**
     * 
     */
    @SerializedName("streetnumber")
    private Integer streetNumber;

    /**
     * 
     */
    @SerializedName("blocknumber")
    private Integer blockNumber;

    /**
     *
     */
    @SerializedName("entrance")
    private String entrance;

    /**
     * 
     */
    @SerializedName("floor")
    private Integer floor;

    /**
     * 
     */
    @SerializedName("apartmentnumber")
    private Integer apartmentNumber;

    /**
     * 
     */
    @SerializedName("city")
    private String city;

    /**
     * 
     */
    @SerializedName("county")
    private String county;

    /**
     * 
     */
    @SerializedName("country")
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

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
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

    public String toString(){
        return street + " " + streetNumber + " " + blockNumber + " " + entrance + " " +floor + " " + apartmentNumber + " " + city + " " + county + " " + country;
    }
}