package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.dto.*;
import org.springframework.context.ApplicationContext;
import utils.CommonUtils;
import utils.MailSender;
import utils.ServerConnection;
import utils.customDeserializer.CustomBloodProductDeserializer;

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

public class TCPService {

    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public TCPService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public ArrayList<BloodProduct> getAllBloodProducts(){
        ArrayList<BloodProduct> products = new ArrayList<>();
        try {
            String urlParameters = String.format("IdU=%s", LoginService.getIdU());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllAvailableBloodProducts");
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
                in.close();

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(BloodProduct.class,new CustomBloodProductDeserializer())
                        .create();
                Type collectionType = new TypeToken<ArrayList<BloodProduct>>(){}.getType();
                products = gson.fromJson(response.toString(), collectionType);
                return products;

            }
            else if(code == 404){
                return products;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return products;
    }

    public ArrayList<DonationReceiverNameBloodGroupDTO> getAllDonationReceiverNames(){
        ArrayList<DonationReceiverNameBloodGroupDTO> donationsDTOArray = new ArrayList<>();
        try {
            String urlParameters = String.format("IdU=%s", LoginService.getIdU());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllDonationReceiverNames");
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
                in.close();

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<DonationReceiverNameBloodGroupDTO>>(){}.getType();
                donationsDTOArray = gson.fromJson(response.toString(), collectionType);
                return donationsDTOArray;

            }
            else if(code == 404){
                return donationsDTOArray;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return donationsDTOArray;
    }

    public ArrayList<BloodRequestHospitalDTO> getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(ProductType productType,String bloodGroup) {
        ArrayList<BloodRequestHospitalDTO> bloodRequests = new ArrayList<>();
        try {
            String urlParameters = String.format("ProductType=%s&BloodGroup=%s", productType.toString(),bloodGroup);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup");
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
                in.close();

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<BloodRequestHospitalDTO>>(){}.getType();
                bloodRequests = gson.fromJson(response.toString(), collectionType);
                return bloodRequests;

            }
            else if(code == 404){
                return bloodRequests;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return bloodRequests;
    }

    public String getDonationCenterAddress(Integer idD) {

        try {
            String urlParameters = String.format("IdD=%s", idD);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonationCenterAddressFromDonationId");
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
                in.close();

                return response.toString();

            }
            else if(code == 404){
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return null;
    }

    public boolean deleteBloodProduct(Integer idBP) {
        try {
            String urlParameters = String.format("IdBP=%s", idBP);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/deleteBloodProduct");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();

            if(code == 200){
                return true;
            }
            else if(code == 404){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return false;
    }

    /**
     * Adding donations goes here
     */

    public String handleAddDonation(String cnp,String status, String quantity,String receiver){
        String urlParameters = String.format("idu=%s&cnp=%s&status=%s&quantity=%s&receiver=%s",LoginService.getIdU(),cnp,status,quantity,receiver);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addDonation");
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
            else if(code == 401){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String response = in.readLine();
                    in.close();
                    return "HttpCode:401 "+response;
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            con.disconnect();
        }
        return "";
    }

    public List<Donor> handleGetDonors(){
        List<Donor> list=new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonors");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);
            int code = con.getResponseCode();
            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
//                System.out.println(response);
                in.close();
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String date=json.toString();
                        String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        return LocalDate.parse(newdate,formatter);
                    }
                }).create();
                Type collectionType = new TypeToken<Collection<Donor>>(){}.getType();
                Collection<Donor> donors = gson.fromJson(response.toString(), collectionType);
                list=new ArrayList<>(donors);
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

    public List<IdentifierDTO> getNamesCNP(List<Donor> donors){
        List<IdentifierDTO> identifiers=new ArrayList<>();
        for(Donor donor:donors){
            identifiers.add(new IdentifierDTO(donor.getCnp(),donor.getName()));
        }
        return identifiers;
    }

    public List<DonationDTO> handleGetDonations(){
        String urlParameters = String.format("idu=%d",LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        List<DonationDTO> list=new ArrayList<>();
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonations");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            if(code == 200){
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
//                    System.out.println(response);
                    in.close();
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<DonationDTO>>() {
                    }.getType();
                    Collection<DonationDTO> donations = gson.fromJson(response.toString(), collectionType);
                    list = new ArrayList<>(donations);
                    return list;
                }
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

    public Donor handleGetDonorFromDonation(Integer idD){
        String urlParameters = String.format("idd=%d",idD);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        Donor donor=null;
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getDonorFromDonation");
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
//                    System.out.println(response);
                    in.close();
                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                        @Override
                        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                            String date=json.toString();
                            String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            return LocalDate.parse(newdate,formatter);
                        }
                    }).create();
                    donor=gson.fromJson(response,Donor.class);
                    return donor;
                }
            }
            else if(code == 401){
                return donor;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return donor;
    }


    public String handleModifyDonation(String status,Integer idD) {
        String urlParameters = String.format("status=%s&idd=%d",status,idD);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/modifyDonation");
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
            else if(code == 401){
                return "HttpCode:401";
            }
        } catch (Exception e) {
            return e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";
    }

    public String handleAddBloodProduct(Integer idD, String type, LocalDate date, Double quantity){
        String urlParameters = String.format("idd=%d&type=%s&date=%s&quantity=%f",idD,type,date.toString(),quantity);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addBloodProduct");
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

    public String handleAddDonationReport(DonationReport report){
        String urlParameters = String.format("iddr=%d&dataproba=%s&validitateproba=%s&observatii=%s",report.getIdDR(),report.getDataProba().toString(),
                report.getValiditateProba().toString(),report.getObservatii());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/addReport");
            con.setConnectTimeout(50000);
            con.setReadTimeout(5000);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int code = con.getResponseCode();
            if(code == 200){
//                System.out.println("AICIS LA 200");
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String response = in.readLine();
                    in.close();
                    return response;
                }
            }
            else if(code == 409){
//                System.out.println("AICIS LA 400");

                return "Http code 409: Conflict";
            }
        } catch (Exception e) {
            return e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";
    }

    public Boolean sendBloodProduct(Integer idBP, Integer idBD) {
        try {
            String urlParameters = String.format("IdBP=%s&IdBD=%s", idBP,idBD);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/sendBloodProduct");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                return true;
            }
            else if(code == 404){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return false;
    }

    public ArrayList<BloodProductShipmentAddressDTO> getAllBloodProductShipmentForDonationCenter(Integer idDC){
        ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs = new ArrayList<>();
        try {
            String urlParameters = String.format("IdDC=%s", idDC);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getAllBloodProductShipmentForDonationCenter");
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
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<BloodProductShipmentAddressDTO>>(){}.getType();
                bloodProductShipmentAddressDTOs = gson.fromJson(response.toString(),collectionType);
                in.close();
            }
            else if(code == 404){
                return bloodProductShipmentAddressDTOs;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return bloodProductShipmentAddressDTOs;
    }

    public Integer getDonationCenterIdForUser(int idU) {
        Integer idDC = -1;
        try {
            String urlParameters = String.format("IdTCP=%s", idU);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/getDonationCenterIdForTCP");
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
                idDC = Integer.parseInt(response.toString());
                in.close();
            }
            else if(code == 404){
                return idDC;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return idDC;
    }

    public Integer splitBloodProduct(Integer idBP, Double quantity) {
        Integer newIdBP = -1;
        try {
            String urlParameters = String.format("IdBP=%s&quantity=%s", idBP,quantity);
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/splitBloodProduct");
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
                newIdBP = Integer.parseInt(response.toString());
                in.close();
            }
            else if(code == 404){
                return newIdBP;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return newIdBP;
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
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String date=json.toString();
                        String newdate=date.replaceAll("\\\"","").replaceAll("[a-zA-Z]"," ");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
                        return LocalDate.parse(newdate,formatter);
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

    public List<Illness> getIllnessPacient(int idU){
        ArrayList<Illness> bloodRequests = new ArrayList<>();
        try {
            String urlParameters = String.format("IdU=%s", String.valueOf(idU));

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getIllness");
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
                in.close();

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<Illness>>(){}.getType();
                bloodRequests = gson.fromJson(response.toString(), collectionType);
                System.out.println(bloodRequests.size()+"-------------------------------------------");
                return bloodRequests;

            }
            else if(code == 404){
                return bloodRequests;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            con.disconnect();
        }
        return bloodRequests;
    }

    public UserIllnessDto getUserIllness(DonationScheduleStatusDTO donationScheduleStatusDTO){
        List<Illness> illnesses = getIllnessPacient(donationScheduleStatusDTO.getIdU());

        List<Illness> last = new ArrayList<>();
        UserIllnessDto userIllnessDto ;

        for(Illness b : illnesses){
            if(b.getName().equals("Suplimentar")){
                last.add(b);
            }
        }

        UserPacientDTO userPacientDTO = new UserPacientDTO();
        List<UserPacientDTO> userPacientDTOS = getAllUserPacient();
        for(UserPacientDTO u : userPacientDTOS){
            if (u.getIdU().equals(donationScheduleStatusDTO.getIdU())){
                userPacientDTO = u;
            }
        }

        String boli = "";
        if(last.isEmpty()){
            boli = "Este sanatos";
        }
        else {
            for (Illness a : last) {
                boli += a.getDescription() + "\n";
            }
        }

        userIllnessDto = new UserIllnessDto(
                donationScheduleStatusDTO.getIdU(),userPacientDTO.getCNP(),userPacientDTO.getBloodGroup(),
                donationScheduleStatusDTO.getName(),"Suplimentar",boli
        );

        return userIllnessDto;
    }

    public List<UserPacientDTO> getAllUserPacient(){
        List<UserPacientDTO> list=new ArrayList<>();
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/getUserPacient");
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

                Type collectionType = new TypeToken<Collection<UserPacientDTO>>(){}.getType();
                Collection<UserPacientDTO> userPacientDTOS = gson.fromJson(response.toString(),collectionType);
                list = new ArrayList<>(userPacientDTOS);

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

        int idU = LoginService.getIdU();
        //System.out.println(idU);
        int idDC = 0;

        for(UserPacientDTO u : userPacientDTOS){
            if(u.getIdU() == idU)
                idDC=u.getIdDC();
        }


        List<DonationScheduleStatusDTO> bun = new ArrayList<>();

        for(DonationSchedule donationSchedule : donationSchedules){
            for (Reservation reservation : reservations){
                if(donationSchedule.getIdDS() .equals(reservation.getIdDS()) && donationSchedule.getIdDC().equals(idDC)){

                    for(UserPacientDTO userPacientDTO : userPacientDTOS){
                        if(reservation.getIdU().equals(userPacientDTO.getIdU())){
                            DonationScheduleStatusDTO status = new DonationScheduleStatusDTO(donationSchedule.getIdDS(),donationSchedule.getIdDC(),donationSchedule.getDonationDateTime(null),donationSchedule.getAvailableSpots(),reservation.getStatus(), userPacientDTO.getName(),userPacientDTO.getIdU());
                            bun.add(status);
                        }
                    }

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
                    System.out.println(dc.getIdU());
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
    public List<BloodRequestDTO> findForAllDemands(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String response="";
        try {

            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/findAllBloodDemands");
            con.setConnectTimeout(505000);
            con.setReadTimeout(505000);

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
    public List<BloodQuantity> findBloodQuantitys(){
        String urlParameters = Integer.toString(LoginService.getIdU());
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            con = serverConnection.getServerConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/findBloodQuantities");
            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            int code = con.getResponseCode();
            if(code == 200){
                List<BloodQuantity> list;

                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String line = in.readLine();
                    System.out.println("====="+line);
                    Gson g=new Gson();
                    Type collectionType= new TypeToken<ArrayList<BloodQuantity>>(){}.getType();
                    list=g.fromJson(line,collectionType);
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

    public void sendEmailForNeededType(String neededType) {
        ApplicationContext context = CommonUtils.getFactory();
        DonorService service = context.getBean(DonorService.class);
        List<String> emails = service.getAllDonorsEmailsForBloodType(neededType);
        String mesaj = "O altă viață este în pericol, un pacient care are aceeași grupă de sânge ca și tine (" + neededType +") " +
                "are nevoie de ajutor. Te rugăm, să folosești aplicația noastră HID Blood pentru a face o donație " +
                "la cel mai apropiat centru. Îți mulțumim, ajutorul tău înseamnă enorm! "+
                "\n\n\nEchipa HID Blood!"+
                "Te rugăm să nu răspunzi acestui mesaj trimis automat!";
        for (String email:emails) {
            MailSender mailSender = new MailSender(email,"Te rugăm să donezi!",mesaj);
            Thread th = new Thread(mailSender);
            th.setDaemon(true);
            th.start();
        }
    }

}
