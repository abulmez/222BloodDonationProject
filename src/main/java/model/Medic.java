package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Medic extends User {

    public Medic(Integer idU, Integer CNP, String name, LocalDate birthday, String mail, Integer phone, Integer idA, Integer idH) {
        super(idU, CNP, name, birthday, mail, phone, idA);
        this.idH = idH;
    }

    /**
     * 
     */
    private Integer idH;


    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }
}