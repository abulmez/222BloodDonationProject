package service;

import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class AdminService {
    private ServerConnection serverConnection;
    private HttpURLConnection con;

    public AdminService(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public String updateAdmin(String cnpVechi,String cnp,String nume,String data,String mail,String telefon){
        String urlParameters=String.format("cnp=%s&id=%s&c=%s&d=%s&mail=%s&t=%s",cnpVechi,cnp,nume,data,mail,telefon);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateAdmin");
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

    public String addAdmin(String cnp,String nume,String data,String mail,String telefon,String user,String pass,String type){
        String urlParameters=String.format("cnp=%s&c=%s&d=%s&mail=%s&t=%s&blood=%s&weight=%s&type=%s",cnp,nume,data,mail,telefon,user,pass,type);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addAdmin");
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

    public String addMedic(String cnp,String nume,String data,String mail,String telefon,String user,String pass,String hospital){
        String urlParameters=String.format("cnp=%s&c=%s&d=%s&mail=%s&t=%s&blood=%s&weight=%s&type=%s",cnp,nume,data,mail,telefon,user,pass,hospital);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addMedic");
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

    public String addTCP(String cnp,String nume,String data,String mail,String telefon,String user,String pass,String hospital){
        String urlParameters=String.format("cnp=%s&c=%s&d=%s&mail=%s&t=%s&blood=%s&weight=%s&type=%s",cnp,nume,data,mail,telefon,user,pass,hospital);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addTCP");
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

    public String updateDonor(String cnpVechi,String cnp,String nume,String data,String mail,String telefon,String blood,String weight){
        String urlParameters=String.format("cnp=%s&id=%s&c=%s&d=%s&mail=%s&t=%s&blood=%s&weight=%s",cnpVechi,cnp,nume,data,mail,telefon,blood,weight);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateDonor");
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

    public String updateMedicHospital(String cnp,String hospital){
        String urlParameters=String.format("id=%s&c=%s",cnp,hospital);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateMedicHospital");
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

    public String updateTCPCenter(String cnp,String center){
        String urlParameters=String.format("id=%s&c=%s",cnp,center);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateTCPCenter");
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

    public String deleteUser(String type,String cnp){
        String urlParameters=String.format("id=%s&c=%s",type,cnp);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/deleteUser");
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

    public String getUsers(String type){
        String urlParameters=String.format("id=%s&c=%s",type,"ds");
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getUsers");
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

    public String getHospitals(String type) {
        String urlParameters = String.format("id=%s&c=%s", type, "ds");
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getHospitals");
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

    public String getUsernames(String type){
        String urlParameters=String.format("id=%s&c=%s",type,"ds");
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getUsernames");
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