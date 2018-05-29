package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.dto.DonationScheduleStatusDTO;
import model.dto.UserPacientDTO;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonationAppointmentsAdminService {

    private HttpURLConnection con;
    private static int idU;
    private UserType userType;
    private ServerConnection serverConnection;

    public DonationAppointmentsAdminService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public List<DonationSchedule> getAllDonationSchedule(){
        //String urlParameters = String.format("username=%s&password=%s");
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<DonationSchedule> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationSchedule");
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
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String date=json.toString();
                        String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");
                        System.out.println(newdate);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
                        return LocalDate.parse(newdate,formatter);
                    }
                }).create();
                Type collectionType = new TypeToken<Collection<DonationSchedule>>(){}.getType();
                Collection<DonationSchedule> donationSchedules = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donationSchedules);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea Donation Schedule: "+list.size());
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

    public List<UserPacientDTO> getAllUserPacient(){
        //String urlParameters = String.format("username=%s&password=%s");
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<UserPacientDTO> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getUserPacient");
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
                /*Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String date=json.toString();
                        String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");
                        System.out.println(newdate);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
                        return Date.parse(newdate,formatter);
                    }
                }).create();*/
                Type collectionType = new TypeToken<Collection<UserPacientDTO>>(){}.getType();
                Collection<UserPacientDTO> userPacientDTOS = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(userPacientDTOS);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea UserPacientDTO Schedule: "+list.size());
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

    public  List<DonationScheduleStatusDTO> getAllDonationStatus(){
        List<DonationSchedule> donationSchedules = getAllDonationSchedule();
        List<Reservation> reservations = getAllReservation();
        List<UserPacientDTO> userPacientDTOS = getAllUserPacient();

        List<DonationScheduleStatusDTO> bun = new ArrayList<>();

        for(DonationSchedule donationSchedule : donationSchedules){
            for (Reservation reservation : reservations){
                if(donationSchedule.getIdDS() == reservation.getIdDS()){

                    for(UserPacientDTO userPacientDTO : userPacientDTOS){
                        if(donationSchedule.getIdDC() == userPacientDTO.getIdDC()){
                            DonationScheduleStatusDTO status = new DonationScheduleStatusDTO(donationSchedule.getIdDS(),donationSchedule.getIdDC(),donationSchedule.getDonationDateTime(null),donationSchedule.getAvailableSpots(),reservation.getStatus(), userPacientDTO.getName());
                            bun.add(status);
                        }
                    }

                    /*DonationScheduleStatusDTO status = new DonationScheduleStatusDTO(donationSchedule.getIdDS(),donationSchedule.getIdDC(),donationSchedule.getDonationDateTime(),donationSchedule.getAvailableSpots(),reservation.getStatus(),"");
                    bun.add(status);*/
                }
            }
        }

        return bun;
    }

    public List<Reservation> getAllReservation(){
        List<Reservation> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getReservation");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            System.out.println("Aici ajung sigur Reservation");

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
                Type collectionType = new TypeToken<Collection<Reservation>>(){}.getType();
                Collection<Reservation> reservations = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(reservations);
                System.out.println("-------------------------------------------");
                System.out.println("Lungimea Reservation: "+list.size());
                System.out.println("-------------------------------------------");
                for(Reservation dc:list){
                    System.out.println(dc.getStatus());
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

    public String handleStatusUpdate(Integer idDS,String status){
        String urlParameters=String.format("idds=%s&status=%s",idDS.toString(),status);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/updateDonationScheduleStatus");
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
                    return response;
                }

            }
            else if(code == 401){
                return "HttpCode:401";
            }



        } catch (IOException e) {
            return  e.getMessage();


        } finally {

            con.disconnect();
        }
        return "";
    }
}
