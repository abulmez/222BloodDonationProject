package service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Hospital;
import org.springframework.context.ApplicationContext;
import utils.CommonUtils;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
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
}
