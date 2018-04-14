package service;

import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class DonationsReportService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationsReportService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public boolean handleLogin(String username,String password){
        String urlParameters = String.format("username=%s&password=%s",username,password);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/login");
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
                    String[] data = response.split("&");
                    in.close();
                }
                return true;
            }
            else if(code == 401){
                return false;
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            con.disconnect();
        }
        return false;
    }


}
