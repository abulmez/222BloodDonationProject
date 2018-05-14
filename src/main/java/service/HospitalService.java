package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Hospital;
import org.springframework.context.ApplicationContext;
import utils.CommonUtils;
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

public class HospitalService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public HospitalService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }


    public List<Hospital> getAllHospitals(){
        List<Hospital> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllHospitalsHandler");
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
                System.out.println(response);
                in.close();
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Hospital>>(){}.getType();
                Collection<Hospital> hospitals  = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(hospitals);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea  Hospital: "+list.size());
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

    public int updateHospital(Integer IdH,Integer IdA,String hospitalName,String phoneNumber){
        String urlParameters=String.format("IdH=%s&IdA=%s&HospitalName=%s&PhoneNumber=%s",IdH,IdA,hospitalName,phoneNumber);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateHospital");
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

    public int addHospital(Integer IdA,String hospitalName,String phoneNumber){
        String urlParameters=String.format("IdA=%s&HospitalName=%s&HospitalPhone=%s",IdA,hospitalName,phoneNumber);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addHospital");
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

    public int deleteHospital(Integer IdH){
        String urlParameters=String.format("IdH=%s",IdH);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/deleteHospital");
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
}
