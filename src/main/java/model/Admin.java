package model;

import java.time.LocalDate;

public class Admin extends User {

    public Admin(Integer idU,String cnp, String name, LocalDate birthday, String mail, String phone){
        super(idU,cnp,name,birthday,mail,phone);
    }
}
