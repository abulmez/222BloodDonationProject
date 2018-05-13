package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Donor;
import utils.DonationDTO;
import utils.IdentifierDTO;
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

public class AddDonationService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public AddDonationService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public String handleAdd(String name,String cnp,String status, String quantity,String receiver){
        String urlParameters = String.format("name=%s&cnp=%s&status=%s&quantity=%s&receiver=%s",name,cnp,status,quantity,receiver);
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
        // String urlParameters = String.format("username=%s&password=%s",username,password);
        //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
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
                System.out.println(response);
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

}
