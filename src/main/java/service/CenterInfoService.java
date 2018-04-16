package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Adress;
import model.DonationCenter;
import model.DonationSchedule;
import model.UserType;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CenterInfoService {

    private HttpURLConnection con;
    private static int idU;
    private UserType userType;
    private ServerConnection serverConnection;

    public CenterInfoService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public List<Adress> getAllAdress(){
        return null;
    }

    public List<DonationCenter> getAllDonationCenter(){
        //String urlParameters = String.format("username=%s&password=%s");
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<DonationCenter> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationCenter");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            System.out.println("Aici ajung sigur");

            int code = con.getResponseCode();
            System.out.println("CODUL: "+code);

            if(code == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<DonationCenter>>(){}.getType();
                Collection<DonationCenter> donationCenters = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donationCenters);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea Donation Center: "+list.size());
                System.out.println("-------------------------------------------");
                for(DonationCenter dc:list){
                    System.out.println(dc.getIdDC());
                }
                System.out.println("-------------------------------------------");
                return list;
            }
            else if(code == 401){
                return list;
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return list;
    }

    /*public static int getIdU() {
        return idU;
    }

    public UserType getUserType() {
        return userType;
    }*/
}
