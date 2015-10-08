package com.example.gaga.presidentialbracket;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class PostComment extends AsyncTask<String, Void, Void> {

    public PostComment() {

    }

    @Override
    protected Void doInBackground(String... arg0) {
        // Otherwise SocketException Connection reset by peer
        try {
            String comment = arg0[0];
            String username = arg0[1];
            String PosterName = arg0[2];


            // EDIT LINK
            String link = "http://particlecollider.net76.net/postComment.php";


            URL url = new URL(link);
            String data = URLEncoder.encode("comment", "UTF-8") + "="
                    + URLEncoder.encode(comment, "UTF-8")
                    + "&"
                    + URLEncoder.encode("UN", "UTF-8") + "="
                    + URLEncoder.encode(username, "UTF-8");
            URLConnection conn = url.openConnection();

            System.setProperty("http.keepAlive", "false");
            // Kill keep-alive property
            conn.setRequestProperty("connection", "close");


            // Write
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            // Read
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}