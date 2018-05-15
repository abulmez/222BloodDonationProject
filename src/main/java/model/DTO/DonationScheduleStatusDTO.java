package model.DTO;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class DonationScheduleStatusDTO {
    public DonationScheduleStatusDTO() {
    }

    public DonationScheduleStatusDTO(Integer idDS, Integer idDC, LocalDate donationDateTime, Integer availableSpots, String status, String name) {
        this.idDS = idDS;
        this.idDC = idDC;
        this.donationDateTime = donationDateTime;
        this.availableSpots = availableSpots;
        this.status = status;
        this.name = name;
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

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
