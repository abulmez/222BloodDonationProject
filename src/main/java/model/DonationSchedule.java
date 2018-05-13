package model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
/**
 * 
 */
public class DonationSchedule {

    /**
     * Default constructor
     */
    public DonationSchedule() {
    }

    public DonationSchedule(Integer idDS, Integer idDC, Timestamp donationDateTime, Integer availableSpots) {
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
    private Timestamp donationDateTime;

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

    public Timestamp getDonationDateTime() {
        return donationDateTime;
    }

    public void setDonationDateTime(Timestamp donationDateTime) {
        this.donationDateTime = donationDateTime;
    }

    public Integer getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }

    @Override
    public String toString() {
        return "DonationSchedule{" +
                "idDS=" + idDS +
                ", idDC=" + idDC +
                ", donationDateTime=" + donationDateTime +
                ", availableSpots=" + availableSpots +
                '}';
    }
}