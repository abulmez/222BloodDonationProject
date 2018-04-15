package model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class DonationReport {

    /**
     * Default constructor
     */
    public DonationReport() {
    }

    public DonationReport(Integer idDR, LocalDate dataProba, Boolean validitateProba, String observatii) {
        this.idDR = idDR;
        this.dataProba = dataProba;
        this.validitateProba = validitateProba;
        this.observatii = observatii;
    }

    /**
     * 
     */
    @SerializedName("iddr")
    private Integer idDR;

    /**
     * 
     */
    @SerializedName("dataproba")
    private LocalDate dataProba;

    /**
     * 
     */
    @SerializedName("validitateproba")
    private Boolean validitateProba;

    /**
     * 
     */
    @SerializedName("observatii")
    private String observatii;

    public Integer getIdDR() {
        return idDR;
    }

    public void setIdDR(Integer idDR) {
        this.idDR = idDR;
    }

    public LocalDate getDataProba() {
        return dataProba;
    }

    public void setDataProba(LocalDate dataProba) {
        this.dataProba = dataProba;
    }

    public Boolean getValiditateProba() {
        return validitateProba;
    }

    public void setValiditateProba(Boolean validitateProba) {
        this.validitateProba = validitateProba;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }
}