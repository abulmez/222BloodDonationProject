package model.DTO;

public class AdressDTO {


    private String adress;
    private Double distance;
    public AdressDTO(String adress1,Double distance){
        this.adress=adress1;
        this.distance=distance;
    }


    public void setAdress(String adress) {
        this.adress = adress;
    }


    public String getAdress() {
        return adress;
    }


    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
