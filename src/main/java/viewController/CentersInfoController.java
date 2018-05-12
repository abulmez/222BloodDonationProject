package viewController;

import com.jfoenix.controls.JFXComboBox;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Adress;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import utils.CommonUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class CentersInfoController {
    private ObservableList<DonationCenter> model=FXCollections.observableArrayList();

    ApplicationContext context = CommonUtils.getFactory();

    @FXML
    private JFXComboBox<String> centerCombobox;

    @FXML
    private WebView webView;

    @FXML
    private Label adressLabel;

    @FXML
    private Label phoneLabel;
    private Timeline locationUpdateTimeline=null;

    private CenterInfoService service;

    @FXML
    private void initialize() throws IOException, GeoIp2Exception, URISyntaxException {

        service = context.getBean(CenterInfoService.class);
        this.model= FXCollections.observableArrayList(service.getAllDonationCenter());
        ObservableList<String> options =
                FXCollections.observableArrayList();

        List<DonationCenter> centers=service.getAllDonationCenter();

        List<Adress> adresses = service.getAllAdress();

        //ObservableList<String> centre=null;
        for(DonationCenter dc : centers){
            //centre.add(adr.getCenterName());
            System.out.println(dc.getCenterName());
            System.out.println(dc.getPhoneNumber());
            System.out.println(dc.getIdA());
            System.out.println(dc.getIdDC());
            options.add(dc.getCenterName());
        }
        //centerCombobox.setItems(centre);

        for (Adress adress : adresses){
            System.out.println(adress.toString());
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }

        System.out.println("============");
        for (String s:options) {
            System.out.println(s);
        }

        centerCombobox.setItems(options);
        centerCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                for (DonationCenter donationCenter:centers){
                    System.out.println(donationCenter.getCenterName()+" "+newValue);
                    if(newValue.equals(donationCenter.getCenterName())){
                        for (Adress adress : adresses){
                            if(adress.getIdA() == donationCenter.getIdA()){
                                adressLabel.setText(adress.toString());
                            }
                        }
                        System.out.println(donationCenter.getCenterName()+" "+newValue);
                        //adressLabel.setText(donationCenter.getCenterName());
                        phoneLabel.setText(donationCenter.getPhoneNumber());
                    }
                }

            }
        });

        initMap();

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
        WebEngine webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        URL url = getClass().getResource("/viewController/googlemaps.html");
        if (url != null)
            webEngine.load(url.toExternalForm());
        setClientLocation(webEngine);
    }

    private void setClientLocation(WebEngine webEngine) throws IOException, GeoIp2Exception {
        String cityName = getClientCity();


        if (locationUpdateTimeline!=null) locationUpdateTimeline.stop();
        locationUpdateTimeline = new Timeline();
        locationUpdateTimeline.getKeyFrames().add(
                new KeyFrame(new javafx.util.Duration(400),
                        actionEvent -> {
                            webEngine.executeScript("document.goToLocation(\"" + cityName + "\")");
                        })
        );
        locationUpdateTimeline.play();
    }

    private String getIpAdress() throws IOException {
        URL url1 = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url1.openStream()));
        return br.readLine();
    }

}
