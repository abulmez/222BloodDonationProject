package model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class DonationSchedule {

    /**
     * Default constructor
     */
    public DonationSchedule() {
    }

    public DonationSchedule(Integer idDS, Integer idDC, LocalDate donationDateTime, Integer availableSpots) {
        this.idDS = idDS;
        this.idDC = idDC;
        this.donationDateTime = donationDateTime;
        this.availableSpots = availableSpots;
    }

    /**
     * 
     */
    @SerializedName("idds")
    private Integer idDS;

    /**
     * 
     */
    @SerializedName("iddc")
    private Integer idDC;

    /**
     * 
     */
    @SerializedName("donationdatetime")
    private LocalDate donationDateTime;

    /**
     * 
     */
    @SerializedName("availablespots")
    private Integer availableSpots;


    public Integer getIdDS() {
        return idDS;
    }

    public void setIdDS(Integer idDS) {
        this.idDS = idDS;
    }

    public Integer getIdDC() {
        return idDC;
    }

    public void setIdDC(Integer idDC) {
        this.idDC = idDC;
    }

    public LocalDate getDonationDateTime() {
        return donationDateTime;
    }

    public void setDonationDateTime(LocalDate donationDateTime) {
        this.donationDateTime = donationDateTime;
    }

    public Integer getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }
}