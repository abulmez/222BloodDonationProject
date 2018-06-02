package service;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import utils.ServerConnection;
import utils.customDeserializer.CustomDonorDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<String> getAllDonorsEmailsForBloodType(String bloodType){
        String urlParameters=String.format("BloodType=%s",bloodType);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<String> list = new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/emailsForBloodType");
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
                Type collectionType = new TypeToken<Collection<String>>(){}.getType();
                Collection<String> emails = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(emails);
                return list;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return list;

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

    public String handleDiseasesChecks(String idU){
        String urlParameters=String.format("id=%s&c=%s",idU,"ds");
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/diseasesChecks");
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

    public int createLogin(UserLoginData loginData, Donor donor) {
        Gson gSon = new Gson();
        int code = 401;
        String loginString = gSon.toJson(loginData, UserLoginData.class);
        String donorString = gSon.toJson(donor,Donor.class);
        String urlParameters = loginString+"%1%1%"+donorString;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/register");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            code = con.getResponseCode();
            return code;
        } catch (IOException e) {
            return 401;
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
                Gson gson = new GsonBuilder().registerTypeAdapter(DonationReport.class,new CustomDonationReportDeserialize()).create();
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

    public class CustomDonationReportDeserialize implements JsonDeserializer<DonationReport> {
        @Override
        public DonationReport deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject jobject = jsonElement.getAsJsonObject();
            String[] text = jobject.get("samplingdate").getAsString().split("-");
            int[] bdayAsInt = Arrays.stream(text).mapToInt(Integer::parseInt).toArray();
            return new DonationReport(
                    jobject.get("iddr").getAsInt(),
                    LocalDate.of(bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]),
                    jobject.get("bloodstatus").getAsBoolean(),
                    jobject.get("bloodreport").getAsString()
            );
        }
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
        List<DonationCenter> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationCenter");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            int code = con.getResponseCode();
            if(code == 200) {
                return extractDonationCenters(list);
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

    private List<DonationCenter> extractDonationCenters(List<DonationCenter> list) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<DonationCenter>>(){}.getType();
        Collection<DonationCenter> donationCenters = gson.fromJson(response.toString(),collectionType);
        list = new ArrayList<>(donationCenters);
        return list;
    }

    public List<DonationCenter> getCurrentDonationCenter(Integer userId) {
        List<DonationCenter> currentDonationCenter=new ArrayList<>();
        String urlParameters = userId.toString();
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/currentReservationDC");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                return extractDonationCenters(currentDonationCenter);
            }


        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return currentDonationCenter;

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
//            System.out.println("Aici ajung sigur");

            int code = con.getResponseCode();
//            System.out.println("CODUL: "+code);

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
//                System.out.println("-------------------------------------------");
//                System.out.println("Lungimea Donation Schedule: "+list.size());
//                System.out.println("-------------------------------------------");
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
//            System.out.println("Aici ajung sigur Reservation");

            int code = con.getResponseCode();
//            System.out.println("CODUL: "+code);

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
//                System.out.println("-------------------------------------------");
//                System.out.println("Lungimea Reservation: "+list.size());
//                System.out.println("-------------------------------------------");
                for(Reservation dc:list){
                    System.out.println(dc.getStatus());
                }
//                System.out.println("-------------------------------------------");
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

    public List<SuffersOf> getSuffersOfByIdU(Integer idU){
        String urlParameters=String.format("IdU=%s",idU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<SuffersOf> list = new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getSuffersOfByIdU");
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
                Type collectionType = new TypeToken<Collection<SuffersOf>>(){}.getType();
                Collection<SuffersOf> donations = gson.fromJson(response.toString(),collectionType);
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
    public Donor getUserByIdU(Integer idU){
        String urlParameters=String.format("IdU=%s",idU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getUserByIdU");
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
                System.out.println(response.toString());
                in.close();
                Gson gson=new GsonBuilder().registerTypeAdapter(Donor.class,new CustomDonorDeserializer()).create();
                Donor  user= gson.fromJson(response.toString(),Donor.class);

                return user;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }
    public List<DonationReport> getAllDonationReportsByIdU(Integer IdU){
        String urlParameters=String.format("IdU=%s",IdU);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<DonationReport> list = new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/donationReportsByIdU");
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
                Gson gson = new GsonBuilder().registerTypeAdapter(DonationReport.class,new CustomDonationReportDeserialize()).create();
                Type collectionType = new TypeToken<Collection<DonationReport>>(){}.getType();
                Collection<DonationReport> donations = gson.fromJson(response.toString(),collectionType);
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

    public Adress getAdressByIdDc(Integer IdDC){
        String urlParameters=String.format("IdDC=%s",IdDC);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<Adress> list = new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getAdressByIdDC");
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
                Type collectionType = new TypeToken<Collection<Adress>>(){}.getType();
                Collection<Adress> donations = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(donations);
                return list.get(0);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            con.disconnect();
        }
    }
}
