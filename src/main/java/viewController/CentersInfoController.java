package viewController;

import com.jfoenix.controls.JFXComboBox;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Adress;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.DonorService;
import utils.CommonUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class CentersInfoController {



    @FXML
    private JFXComboBox<DonationCenter> centerCombobox;

    @FXML
    private WebView webView;

    @FXML
    private TextArea adressLabel;

    @FXML
    private TextArea phoneLabel;
    private Timeline locationUpdateTimeline=null;
    private CenterInfoService centerInfoService;
    private WebEngine webEngine;
    private List<Adress>adresses;
    @FXML
    private void initialize() throws IOException, GeoIp2Exception, URISyntaxException {
        initMap();
        ApplicationContext context = CommonUtils.getFactory();
        centerInfoService = context.getBean(CenterInfoService.class);
        adresses=centerInfoService.getAllAdressForCenters();
        initComboBox();
        adressLabel.setEditable(false);
        phoneLabel.setEditable(false);
    }

    private void addComboBoxListener() {
        centerCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            Integer idAddress=newValue.getIdA();
            Adress addressFinal = null;
            for (Adress adress:adresses) {
                if(adress.getIdA().equals(idAddress))
                    addressFinal=adress;
            }
            setClientLocation(webEngine,addressFinal.toStringSmall());
            adressLabel.setText("Adresă : "+addressFinal.getFullAdress());
            phoneLabel.setText("Telefon : "+newValue.getPhoneNumber());
        });
    }

    private void initComboBox() {
        for (DonationCenter centerInfoService:centerInfoService.getAllDonationCenter()) {
            System.out.print(centerInfoService.getCenterName());
            centerCombobox.getItems().add(centerInfoService);
        }
        addComboBoxListener();
    }

    private String getClientCity() throws IOException, GeoIp2Exception {
        URL url1 =getClass().getResource( "/viewController/GeoLite2-City.mmdb");
        String dbLocation = url1.getPath();
        dbLocation=dbLocation.substring(1);
        dbLocation=dbLocation.replaceAll("/","//");


        File database = new File(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

        InetAddress ipAddress = InetAddress.getByName(getIpAdress());
        CityResponse response = dbReader.city(ipAddress);
        return response.getCity().getName();
    }
    private void initMap() throws IOException, GeoIp2Exception {
        webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        URL url = getClass().getResource("/viewController/googlemaps.html");
        if (url != null)
            webEngine.load(url.toExternalForm());
    }
    @FXML
    private void doSth(){

        List<Adress> adressList=centerInfoService.getAllAdressForCenters();
        adressList.forEach(x->{System.out.println(x.toString());});
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

    private String getIpAdress() throws IOException {
        URL url1 = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url1.openStream()));
        return br.readLine();
    }

}
