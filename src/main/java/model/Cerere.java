package model;

import java.util.*;

/**
 * 
 */
public class Cerere {

    /**
     * Default constructor
     */
    public Cerere() {
    }

    public Cerere(Integer idBD, Integer idH, String neededType, String description, String priority, Integer quantity) {
        this.idBD = idBD;
        this.idH = idH;
        NeededType = neededType;
        Description = description;
        Priority = priority;
        Quantity = quantity;
    }

    /**
     * 
     */
    private Integer idBD;

    /**
     * 
     */
    private Integer idH;

    /**
     * 
     */
    private String NeededType;

    /**
     * 
     */
    private String Description;

    /**
     * 
     */
    private String Priority;

    /**
     * 
     */
    private Integer Quantity;


    public Integer getIdBD() {
        return idBD;
    }

    public void setIdBD(Integer idBD) {
        this.idBD = idBD;
    }

    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }

    public String getNeededType() {
        return NeededType;
    }

    public void setNeededType(String neededType) {
        NeededType = neededType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}