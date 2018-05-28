package service;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import utils.ServerConnection;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
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



}
