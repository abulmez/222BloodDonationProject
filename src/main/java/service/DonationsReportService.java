package service;

import model.DonationReport;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class DonationsReportService {
    private HttpURLConnection con;
    private ServerConnection serverConnection;

    public DonationsReportService(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public String handleAdd(DonationReport report){
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
            e.getMessage();

        } finally {
            con.disconnect();
        }
        return "";
    }


}
