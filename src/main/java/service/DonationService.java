package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Donation;
import model.UserType;
import utils.DonationDTO;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonationService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public List<DonationDTO> handlePopulate(){
       // String urlParameters = String.format("username=%s&password=%s",username,password);
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonations");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            int code = con.getResponseCode();

            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<DonationDTO>>(){}.getType();
                Collection<DonationDTO> donations = gson.fromJson(response.toString(), collectionType);
                List<DonationDTO> list=new ArrayList<>(donations);
                return list;
            }
            else if(code == 401){
                return null;
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return null;
    }


    public void handleModify() {
    }
}
