package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class TCP extends User {

    public TCP(Integer idU, Integer CNP, String name, LocalDate birthday, String mail, Integer phone, Integer idA, Integer idDC) {
        super(idU, CNP, name, birthday, mail, phone, idA);
        this.idDC = idDC;
    }

    /**
     * 
     */
    private Integer idDC;


    public Integer getIdDC() {
        return idDC;
    }

    public void setIdDC(Integer idDC) {
        this.idDC = idDC;
    }
}