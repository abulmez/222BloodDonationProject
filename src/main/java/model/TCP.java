package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */

public class TCP extends User {

    public TCP(Integer idU, String cnp, String name, LocalDate birthday, String mail, String phone, Integer idDC) {
        super(idU, cnp, name, birthday, mail, phone);
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