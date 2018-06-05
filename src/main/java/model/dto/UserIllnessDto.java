package model.dto;

public class UserIllnessDto {

    private Integer idU;

    private String cnp;

    private String bloodGroup;

    private String nameUser;

    //private Integer idI;

    private String nameIllness;

    private String description;

    public UserIllnessDto(Integer idU, String cnp, String bloodGroup, String nameUser, String nameIllness, String description) {
        this.idU = idU;
        this.cnp = cnp;
        this.bloodGroup = bloodGroup;
        this.nameUser = nameUser;
        //this.idI = idI;
        this.nameIllness = nameIllness;
        this.description = description;
    }

    public Integer getIdU() {
        return idU;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /*public Integer getIdI() {
        return idI;
    }

    public void setIdI(Integer idI) {
        this.idI = idI;
    }*/

    public String getNameIllness() {
        return nameIllness;
    }

    public void setNameIllness(String nameIllness) {
        this.nameIllness = nameIllness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
