package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Adress;
import model.DonationCenter;

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

    public List<Adress> getAllAdressForCenters(){
        List<Adress> list=new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationCenterAddresses");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            int code = con.getResponseCode();
            if(code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Adress>>(){}.getType();
                Collection<Adress> donationCenters = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donationCenters);
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

    public List<Adress> getAllAdress(){
        List<Adress> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAdress");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            int code = con.getResponseCode();

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
                Type collectionType = new TypeToken<Collection<Adress>>(){}.getType();
                Collection<Adress> adresses = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(adresses);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea Adress: "+list.size());
                System.out.println("-------------------------------------------");
                for(Adress dc:list){
                    System.out.println(dc.getStreet());
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


    public int deleteCentre(Integer idDC){
        String urlParameters=String.format("IdDC=%s",idDC);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/deleteCentre");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();
                    return 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return 0;
    }

    public int updateCentre(Integer idDC,Integer idA,String phoneNumber,String centerName){
        String urlParameters=String.format("IdDC=%s&IdA=%s&CentreName=%s&CentrePhone=%s",idDC,idA,centerName,phoneNumber);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateCentre");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();
                    return 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return 0;
    }

    public int addCenter(Integer idA,String phoneNumber,String centerName){
        String urlParameters=String.format("IdA=%s&CentreName=%s&CentrePhone=%s",idA,centerName,phoneNumber);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addCentre");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();
                    return 1;
                }
            }
            else if(code == 401){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();
                    return 0;
                }
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return 0;
    }

    public void addAdress(String street, String streetNr, String blockNr, String entrance, String floor, String apartNr, String city, String county, String country) {
        String urlParameters = String.format("sreet=%s&streetNr=%s&blockNr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s", street, streetNr, blockNr, entrance, floor, apartNr, city, county, country);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addAdress");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
    }

}
