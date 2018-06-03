package model.dto;

import java.util.Date;

public class TableSetterGetterDTO {


    public TableSetterGetterDTO(int numarRezervare, int numarCentruDonatie, int locuriDisponibile, Date dataDonarii) {
        NumarRezervare = numarRezervare;
        NumarCentruDonatie = numarCentruDonatie;
        LocuriDisponibile = locuriDisponibile;
        DataDonarii = dataDonarii;
    }

    public int getNumarRezervare() {
        return NumarRezervare;
    }

    public void setNumarRezervare(int numarRezervare) {
        NumarRezervare = numarRezervare;
    }

    public int getNumarCentruDonatie() {
        return NumarCentruDonatie;
    }

    public void setNumarCentruDonatie(int numarCentruDonatie) {
        NumarCentruDonatie = numarCentruDonatie;
    }

    public int getLocuriDisponibile() {
        return LocuriDisponibile;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        LocuriDisponibile = locuriDisponibile;
    }

    public Date getDataDonarii() {
        return DataDonarii;
    }

    public void setDataDonarii(Date dataDonarii) {
        DataDonarii = dataDonarii;
    }

    int NumarRezervare,NumarCentruDonatie,LocuriDisponibile;
    Date DataDonarii;


}
