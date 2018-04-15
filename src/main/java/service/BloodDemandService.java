package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.BloodRequest;
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
import java.util.List;

public class BloodDemandService {

    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public BloodDemandService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }
    public String handleAdd(Integer idH,String neededType,
                             String description,String priority,Integer quantity){
        String urlParameters=String.format("IdH=%s&NeededType=%s&Description=%s&Priority=%s&Quantity=%s",idH,neededType,description,priority,quantity);;
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addBloodDemand");
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

                }
            }
            else if(code == 423){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();

                }
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return response;
    }

    public String handleModificare(Integer idBd,Integer idH,String neededType,
                                   String description,String priority,Integer quantity){
        String urlParameters = String.format("IdBd=%s&IdH=%s&NeededType=%s&Description=%s&Priority=%s&Quantity=%s",idBd,idH,neededType,description,priority,quantity);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/modifyBloodDemand");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();

                }
                return response;
            }
            else if(code == 404){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();

                }
                return response;
            }
            else{
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    response = in.readLine();

                }
                return response;
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return "Eroare la IO";
    }

    public List<BloodRequest> findAllDemands(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/findBloodDemands");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                List<BloodRequest> list;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    Gson g=new Gson();
                    String intermediar=in.readLine();
                    while(intermediar!=null) {
                        response = response + intermediar;
                        intermediar=in.readLine();
                    }
                    System.out.println(response);
                    Type collectionType= new TypeToken<ArrayList<BloodRequest>>(){}.getType();
                    list=g.fromJson(response,collectionType);
                    System.out.println(list.get(0).getIdBD());
                    for(BloodRequest b : list){
                        System.out.println(b.getDescription());
                    }
                }
                return list;
            }
            else{
                return null;
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return null;

    }



}
