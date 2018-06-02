package model;

import java.util.*;

/**
 * 
 */
public class Illness {

    /**
     * Default constructor
     */
    public Illness() {
    }

    public Illness(Integer idI, String name, String description) {
        this.idI = idI;
        this.name = name;
        this.description = description;
    }

    /**
     * 
     */
    private Integer idI;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String description;

    public Integer getIdI() {
        return idI;
    }

    public void setIdI(Integer idI) {
        this.idI = idI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "" + this.name + "\n" + this.description;
    }
}