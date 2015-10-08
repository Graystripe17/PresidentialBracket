package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Gaga on 9/26/2015.
 */
public class addUser extends AsyncTask<String, Void, Void> {



    public addUser() {
    }

    @Override
    protected Void doInBackground(String... args) {
        try {
            String username = args[0];
            String link = "http://particlecollider.net76.net/addUser.php/";
            URL url = new URL(link);
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            URLConnection conn = url.openConnection();

            System.setProperty("http.keepAlive", "false");
            // Kill keep-alive property
            conn.setRequestProperty("connection", "close");

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
