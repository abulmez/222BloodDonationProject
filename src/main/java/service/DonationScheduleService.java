package service;

import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class DonationScheduleService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationScheduleService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }
    public String requestForTableHandle(Integer idDS,Integer idDC){
        String urlParameters = String.format("IdDC=%s&IdDS=%s",idDC,idDS);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/donationSchedule");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code=con.getResponseCode();

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
                return response;
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }

    }
}
