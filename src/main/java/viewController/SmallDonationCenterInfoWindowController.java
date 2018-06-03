package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.dto.BloodRequestHospitalDTO;



public class SmallDonationCenterInfoWindowController {

    @FXML
    Label hospitalNameLabel,hospitalPhoneLabel,hospitalAddressLabel,hospitalCityLabel,hospitalCountryLabel;

    BloodRequestHospitalDTO entity;


    public void setEntity(BloodRequestHospitalDTO entity){
        this.entity = entity;
        hospitalNameLabel.setText(entity.getHospitalName());
        hospitalPhoneLabel.setText(entity.getHospitalPhone());
        String[] splitAddress = entity.getHospitalAdress().split(" ");
        hospitalCityLabel.setText(splitAddress[splitAddress.length-2]);
        hospitalCountryLabel.setText(splitAddress[splitAddress.length-1]);
        StringBuilder address = new StringBuilder();
        for(int i=0;i<splitAddress.length-2;i++)
            address.append(splitAddress[i]).append(" ");
        hospitalAddressLabel.setText(address.toString());
    }
}
