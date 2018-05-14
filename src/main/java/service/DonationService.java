package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Donation;
import model.ProductType;
import model.UserType;
import utils.DonationDTO;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonationService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public List<DonationDTO> handleGetDonations(){
       // String urlParameters = String.format("username=%s&password=%s",username,password);
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<DonationDTO> list=new ArrayList<>();
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
                list=new ArrayList<>(donations);
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



    public String handleModifyDonation(String status,Integer idD) {
        String urlParameters = String.format("status=%s&idd=%d",status,idD);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/modifyDonation");
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

    public String handleIsDecomposed(Integer idD){
        String urlParameters = String.format("idd=%d",idD);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/isDecomposed");
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
            else if(code == 404){
                return "Http code 404: Not found";
            }

        } catch (Exception e) {
            return e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";

    }

    public String handleAddBloodProduct(Integer idD, String type, LocalDate date, Double quantity){
        String urlParameters = String.format("idd=%d&type=%s&date=%s&quantity=%f",idD,type,date.toString(),quantity);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addBloodProduct");
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
            else if(code == 422){
               return "Http code 422: Unprocessed entity.";
            }
        } catch (Exception e) {
            return e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";
    }


}
