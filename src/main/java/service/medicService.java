package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.BloodRequest;
import model.BloodRequestDTO;
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

public class medicService {

    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public medicService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }
    public String handleAdd(Integer idU,String neededType,
                             String description,String priority,Double quantity,String bloodDemandType){
        String urlParameters=String.format("IdU=%s&NeededType=%s&Description=%s&Priority=%s&Quantity=%s&BloodProductType=%s",idU,neededType,description,priority,quantity,bloodDemandType);
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

    public String handleRemove(Integer idBd){
        String urlParameters = String.format("IdBd=%s",idBd);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/removeBloodDemand");
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

    public String handleVizualizare(Integer idBd){
        String urlParameters = String.format("IdBd=%s",idBd);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/vizualizareBloodDemands");
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


    public List<BloodRequestDTO> handleFiltrarePlasate(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/filtrarePlasateBloodDemands");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                List<BloodRequestDTO> list;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    Gson g=new Gson();
                    Type collectionType= new TypeToken<ArrayList<BloodRequestDTO>>(){}.getType();
                    list=g.fromJson(in.readLine(),collectionType);
                    Integer i=0;
//                    for(BloodRequestDTO br:list){
//                        System.out.println("Valoarea: "+ br.getIdBD()+ " IdH: "+br.getIdH()+" Descriere: "+br.getDescription()+" Prioritate: "+br.getPriority()+
//                                " Cantitate: "+br.getQuantity()+" Tip: "+br.getNeededType()+" Delivered: "+br.getDelivered()+" Status: "+br.getStatus() + " BloodType: "+br.getBloodProductType());
//                    }
                    return list;
                }

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


    public List<BloodRequestDTO> handleFiltrareLivrate(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/filtrareLivrateBloodDemands");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                List<BloodRequestDTO> list;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    Gson g=new Gson();
                    Type collectionType= new TypeToken<ArrayList<BloodRequestDTO>>(){}.getType();
                    list=g.fromJson(in.readLine(),collectionType);
                    Integer i=0;
//                    for(BloodRequestDTO br:list){
//                        System.out.println("Valoarea: "+ br.getIdBD()+ " IdH: "+br.getIdH()+" Descriere: "+br.getDescription()+" Prioritate: "+br.getPriority()+
//                                " Cantitate: "+br.getQuantity()+" Tip: "+br.getNeededType()+" Delivered: "+br.getDelivered()+" Status: "+br.getStatus() + " BloodType: "+br.getBloodProductType());
//                    }
                    return list;
                }

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

    public String handleModificare(Integer idBd,Integer idU,String neededType,
                                   String description,String priority,Double quantity,String bloodDemandType){
        String urlParameters = String.format("IdBd=%s&IdU=%s&NeededType=%s&Description=%s&Priority=%s&Quantity=%s&BloodProductType=%s",idBd,idU,neededType,description,priority,quantity,bloodDemandType);
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

    public List<BloodRequestDTO> findAllDemands(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/findBloodDemands");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int code = con.getResponseCode();
            if(code == 200){
                List<BloodRequestDTO> list;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    Gson g=new Gson();
                    Type collectionType= new TypeToken<ArrayList<BloodRequestDTO>>(){}.getType();
                    list=g.fromJson(in.readLine(),collectionType);
                    Integer i=0;
//                    for(BloodRequestDTO br:list){
//                        System.out.println("Valoarea: "+ br.getIdBD()+ " IdH: "+br.getIdH()+" Descriere: "+br.getDescription()+" Prioritate: "+br.getPriority()+
//                        " Cantitate: "+br.getQuantity()+" Tip: "+br.getNeededType()+" Delivered: "+br.getDelivered()+" Status: "+br.getStatus() + " BloodType: "+br.getBloodProductType());
//                    }
                    return list;
                }

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
