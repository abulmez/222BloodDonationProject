package viewController;

import com.jfoenix.controls.JFXComboBox;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

public class CentersInfoController {



    @FXML
    private JFXComboBox<?> centerCombobox;

    @FXML
    private WebView webView;

    @FXML
    private Label adressLabel;

    @FXML
    private Label phoneLabel;
    private Timeline locationUpdateTimeline=null;

    @FXML
    private void initialize() throws IOException, GeoIp2Exception, URISyntaxException {

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