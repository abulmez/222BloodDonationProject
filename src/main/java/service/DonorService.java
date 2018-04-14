package service;


import utils.ServerConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

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
}
