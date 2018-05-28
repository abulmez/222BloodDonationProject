package viewController;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Adress;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import service.LoginService;
import utils.CommonUtils;
import java.net.*;
import java.util.List;

public class CentersInfoController {

    @FXML
    private JFXComboBox<DonationCenter> centerCombobox;

    @FXML
    private WebView webView;

    @FXML
    private TextArea addressLabel;

    @FXML
    private TextArea phoneLabel;
    private Timeline locationUpdateTimeline=null;
    private WebEngine webEngine;
    private List<Adress> addresses;
    private List<DonationCenter> donationCenters;
    private List<DonationCenter> currentDonationCenter;

    @FXML
    private void initialize(){
        initMap();
        initLists();
        initComboBox();
        initTextAreas();
    }

    private void initTextAreas() {
        addressLabel.setEditable(false);
        phoneLabel.setEditable(false);
    }

    private void initLists() {
        ApplicationContext context = CommonUtils.getFactory();
        DonorService donorService = context.getBean(DonorService.class);
        Integer userId = LoginService.getIdU();
        addresses = donorService.getAllAdressForCenters();
        donationCenters = donorService.getAllDonationCenter();
        currentDonationCenter = donorService.getCurrentDonationCenter(userId);
    }

    private void addComboBoxListener() {
        centerCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            Integer idAddress=newValue.getIdA();
            Adress addressFinal = null;
            for (Adress adress: addresses) {
                if(adress.getIdA().equals(idAddress))
                    addressFinal=adress;
            }
            if(addressFinal!=null) {
                setClientLocation(webEngine, addressFinal.toStringSmall());
                String address=addressFinal.getFullAdress();
                if (address != null && address.length() > 0 && address.charAt(address.length() - 1) == ',') {
                    System.out.println("Multa magiex");
                    address = address.substring(0, address.length() - 1);
                }
                addressLabel.setText("Adresă : " + address);
                phoneLabel.setText("Telefon : " + newValue.getPhoneNumber());
            }
        });
    }

    private void initComboBox() {
        for (DonationCenter centerInfoService:donationCenters) {
            centerCombobox.getItems().add(centerInfoService);
        }
        addComboBoxListener();
        if(currentDonationCenter.size()!=0)
            for(DonationCenter donationCenter1:donationCenters)
                if(donationCenter1.getIdDC().equals(currentDonationCenter.get(0).getIdDC()))
                    centerCombobox.getSelectionModel().select(donationCenter1);
    }

    private void initMap(){
        webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        URL url = getClass().getResource("/viewController/googlemaps.html");
        if (url != null)
            webEngine.load(url.toExternalForm());
    }

    private void setClientLocation(WebEngine webEngine,String address) {
        if (locationUpdateTimeline!=null) locationUpdateTimeline.stop();
        locationUpdateTimeline = new Timeline();
        locationUpdateTimeline.getKeyFrames().add(
                new KeyFrame(new javafx.util.Duration(2900),
                        actionEvent -> webEngine.executeScript("geocodeAddress(\"" + "'"+address+"'"+"\")"))
        );
        locationUpdateTimeline.play();
    }


}
