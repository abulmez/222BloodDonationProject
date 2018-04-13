package service;

import utils.ServerConnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class DonorService {

    private ServerConnection serverConnection;
    private HttpURLConnection con;

    public DonorService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public void handleAdress(String street,String streetNr,String blockNr,String entrance,String floor,String apartNr,String city,String county,String country){
        String urlParameters = String.format("sreet=%s&streetNr=%s&blockNr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s",street,streetNr,blockNr,entrance,floor,apartNr,city,county,country);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "adress");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            con.disconnect();
        }
    }
}
