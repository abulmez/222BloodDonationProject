package service;

import model.UserType;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginService {

    private HttpURLConnection con;
    static int idU;
    private UserType userType;
    private ServerConnection serverConnection;

    public LoginService(ServerConnection serverConnection){
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
                    idU = Integer.parseInt(data[0].split("=")[1]);
                    userType = UserType.valueOf( data[1].split("=")[1]);

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

    public static int getIdU() {
        return idU;
    }

    public UserType getUserType() {
        return userType;
    }
}
