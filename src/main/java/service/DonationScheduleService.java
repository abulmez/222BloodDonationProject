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

}
