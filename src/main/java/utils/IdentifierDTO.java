package utils;

public class IdentifierDTO {
    private String cnp;
    private String name;
    public IdentifierDTO(String cnp,String name){
        this.cnp=cnp;
        this.name=name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
