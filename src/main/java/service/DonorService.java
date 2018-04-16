package service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Donation;
import model.DonationReport;
import utils.ServerConnection;
import viewController.DonationsReportDetails;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonorService {

    private ServerConnection serverConnection;
    private HttpURLConnection con;

    public DonorService(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void handleAdress(String street, String streetNr, String blockNr, String entrance, String floor, String apartNr, String city, String county, String country, String idU) {
        String urlParameters = String.format("sreet=%s&streetNr=%s&blockNr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s&idU=%s", street, streetNr, blockNr, entrance, floor, apartNr, city, county, country, idU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/adress");
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

    public String handleUserUpdate(String idU,String weight,String phone,String mail){
        String urlParameters=String.format("idU=%s&weight=%s&phone=%s&mail=%s",idU,weight,phone,mail);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/userUpdate");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
                return response;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }

    public String handleDiseases(String transfer) {
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/diseases");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeUTF(transfer);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }

    public String handleAdditional(String additional,String idU){
        String urlParameters= String.format("add=%s&id=%s",additional,idU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/additional");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
                return response;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }

    public String handleFields(String idU) {
        String urlParameters=String.format("id=%s&c=%s",idU,"ds");
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/fields");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String response = in.readLine();
                return response;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }

    public List<Donation> getAllDonations(Integer idU){
        String urlParameters=String.format("IdU=%s",idU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<Donation> list = new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/donations");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = in.readLine())!=null)
                {
                    response.append(inputLine);
                }

                in.close();
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Donation>>(){}.getType();
                Collection<Donation> donations = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donations);
                return list;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return list;

        } finally {

            con.disconnect();
        }
    }

    public DonationReport getDonationReport(Integer idDR){
        String urlParameters=String.format("idDR=%s",idDR);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        DonationReport don = new DonationReport();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/donationreport");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                inputLine = in.readLine();
                response.append(inputLine);
                in.close();
                Gson gson = new Gson();
                Type type = new TypeToken<DonationReport>(){}.getType();
                DonationReport donationReport= gson.fromJson(response.toString(),type);
                don = donationReport;
                return don;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return don;

        } finally {

            con.disconnect();
        }
    }
}
