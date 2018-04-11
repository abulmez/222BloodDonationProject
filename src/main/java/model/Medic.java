package model;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Medic extends User {

    public Medic(Integer idU, String cnp, String name, LocalDate birthday, String mail, String phone, Integer idH) {
        super(idU, cnp, name, birthday, mail, phone);
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