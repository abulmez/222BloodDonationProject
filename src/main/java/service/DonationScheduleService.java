package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.DonationSchedule;
import model.Reservation;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonationScheduleService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationScheduleService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }
    public Integer settingAvailableSpots(Integer IdDS){ {
            Integer IdDs = -1;
            try {
                String urlParameters = String.format("IdDS=%s", IdDS);
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                con = serverConnection.getServerConnection();
                con.setDoOutput(true);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/setAvailableSpots");
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
                    IdDs = Integer.parseInt(response.toString());
                    in.close();
                }
                else if(code == 404){
                    return IdDs;
                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {

                con.disconnect();
            }
            return IdDs;
        }
    }
    public List<DonationSchedule> getAllDonationSchedule(){
        List<DonationSchedule> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationSchedule");
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
                Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
                    @Override
                    public Timestamp deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String date=json.toString();
                        String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");
                        return Timestamp.valueOf(newdate);
//                         Instant instant = Instant.parse(date);
//                        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
//                        return result.toLocalDate();

                    }
                }).create();

                Type collectionType = new TypeToken<Collection<DonationSchedule>>(){}.getType();
                Collection<DonationSchedule> donationSchedules = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donationSchedules);

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
    public List<Reservation> getAllReservation(){
        List<Reservation> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getReservation");
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
                Type collectionType = new TypeToken<Collection<Reservation>>(){}.getType();
                Collection<Reservation> reservations = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(reservations);

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
    public int deleteReservation(Integer IdU){
        String urlParameters=String.format("IdU=%s",IdU);
        byte[] postData=urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="Conexiunea nu s-a realizat";
        try{
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/deleteReservation");
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
    public String addReservation(Integer IdDS,Integer IdU){
        String urlParameters = String.format("idDS=%d&IdU=%s",IdDS,IdU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addReservation");
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
