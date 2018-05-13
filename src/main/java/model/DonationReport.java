package model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
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

    public DonationReport(Integer idDR, String dataProba, Boolean validitateProba, String observatii) {
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
    @SerializedName("samplingdate")
    @SerializedName("dataproba")
    private String dataProba;

    /**
     * 
     */
    @SerializedName("bloodstatus")
    @SerializedName("validitateproba")
    private Boolean validitateProba;

    /**
     * 
     */
    @SerializedName("bloodreport")
    @SerializedName("observatii")
    private String observatii;

    public Integer getIdDR() {
        return idDR;
    }

    public void setIdDR(Integer idDR) {
        this.idDR = idDR;
    }

    public String getDataProba() {
        return dataProba;
    }

    public void setDataProba(String dataProba) {
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