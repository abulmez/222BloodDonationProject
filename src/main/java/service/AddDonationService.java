package service;

import utils.DonationDTO;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class AddDonationService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public AddDonationService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public String handleAdd(String name,String idU,String status, String quantity){
        String urlParameters = String.format("name=%s&idu=%s&status=%s&quantity=%s",name,idU,status,quantity);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addDonation");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String response = in.readLine();
                    in.close();
                    return response;
                }
            }
            else if(code == 401){
                return "HttpCode:401";
            }



        } catch (Exception e) {
            return e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";
    }

}
