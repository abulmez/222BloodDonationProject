package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ServerConnection {

    private Properties serverProps;

    public ServerConnection(Properties serverProp){
        this.serverProps = serverProp;
    }

    public HttpURLConnection getServerConnection(){
        try {
            String url = "http://" + serverProps.getProperty("host") + ":" + serverProps.getProperty("port");
            URL myurl = new URL(url);
            return  (HttpURLConnection) myurl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
