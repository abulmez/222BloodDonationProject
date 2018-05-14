package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.BloodProduct;
import model.DTO.BloodProductShipmentAddressDTO;
import model.DTO.BloodRequestHospitalDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import model.ProductType;
import utils.ServerConnection;
import utils.customDeserializer.CustomBloodProductDeserializer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TCPService {

    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public TCPService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public ArrayList<BloodProduct> getAllBloodProducts(){
        ArrayList<BloodProduct> products = new ArrayList<>();
        try {
            String urlParameters = String.format("IdU=%s", LoginService.getIdU());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllAvailableBloodProducts");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();

            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(BloodProduct.class,new CustomBloodProductDeserializer())
                        .create();
                Type collectionType = new TypeToken<ArrayList<BloodProduct>>(){}.getType();
                products = gson.fromJson(response.toString(), collectionType);
                return products;

            }
            else if(code == 404){
                return products;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return products;
    }

    public ArrayList<DonationReceiverNameBloodGroupDTO> getAllDonationReceiverNames(){
        ArrayList<DonationReceiverNameBloodGroupDTO> donationsDTOArray = new ArrayList<>();
        try {
            String urlParameters = String.format("IdU=%s", LoginService.getIdU());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllDonationReceiverNames");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();

            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<DonationReceiverNameBloodGroupDTO>>(){}.getType();
                donationsDTOArray = gson.fromJson(response.toString(), collectionType);
                return donationsDTOArray;

            }
            else if(code == 404){
                return donationsDTOArray;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return donationsDTOArray;
    }

    public ArrayList<BloodRequestHospitalDTO> getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(ProductType productType,String bloodGroup) {
        ArrayList<BloodRequestHospitalDTO> bloodRequests = new ArrayList<>();
        try {
            String urlParameters = String.format("ProductType=%s&BloodGroup=%s", productType.toString(),bloodGroup);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();

            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<BloodRequestHospitalDTO>>(){}.getType();
                bloodRequests = gson.fromJson(response.toString(), collectionType);
                return bloodRequests;

            }
            else if(code == 404){
                return bloodRequests;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return bloodRequests;
    }

    public String getDonationCenterAddress(Integer idD) {

        try {
            String urlParameters = String.format("IdD=%s", idD);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationCenterAddressFromDonationId");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();

            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();

            }
            else if(code == 404){
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return null;
    }

    public boolean deleteBloodProduct(Integer idBP) {
        try {
            String urlParameters = String.format("IdBP=%s", idBP);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/deleteBloodProduct");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();

            if(code == 200){
                return true;
            }
            else if(code == 404){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return false;
    }

    public Boolean sendBloodProduct(Integer idBP, Integer idBD) {
        try {
            String urlParameters = String.format("IdBP=%s&IdBD=%s", idBP,idBD);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/sendBloodProduct");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                return true;
            }
            else if(code == 404){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return false;
    }

    public ArrayList<BloodProductShipmentAddressDTO> getAllBloodProductShipmentForDonationCenter(Integer idDC){
        ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs = new ArrayList<>();
        try {
            String urlParameters = String.format("IdDC=%s", idDC);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getAllBloodProductShipmentForDonationCenter");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<BloodProductShipmentAddressDTO>>(){}.getType();
                bloodProductShipmentAddressDTOs = gson.fromJson(response.toString(),collectionType);
                in.close();
            }
            else if(code == 404){
                return bloodProductShipmentAddressDTOs;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return bloodProductShipmentAddressDTOs;
    }

    public Integer getDonationCenterIdForUser(int idU) {
        Integer idDC = -1;
        try {
            String urlParameters = String.format("IdTCP=%s", idU);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getDonationCenterIdForTCP");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                idDC = Integer.parseInt(response.toString());
                in.close();
            }
            else if(code == 404){
                return idDC;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return idDC;
    }

    public Integer splitBloodProduct(Integer idBP, Double quantity) {
        Integer newIdBP = -1;
        try {
            String urlParameters = String.format("IdBP=%s&quantity=%s", idBP,quantity);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/splitBloodProduct");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                newIdBP = Integer.parseInt(response.toString());
                in.close();
            }
            else if(code == 404){
                return newIdBP;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return newIdBP;
    }
}
