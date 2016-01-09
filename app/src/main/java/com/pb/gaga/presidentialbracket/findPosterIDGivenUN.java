package com.pb.gaga.presidentialbracket;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Works; Beware of socket exception
 */
public class findPosterIDGivenUN extends AsyncTask<String, Void, Integer> {

    private AsyncTaskCompleteListener<String> callback;

    public findPosterIDGivenUN(Context context) {

    }

    @Override
    protected Integer doInBackground(String... args) {
        try {
            String username = args[0];
            // TODO: Replace POST request with GET request
            String link = "http://particlecollider.net76.net/findPosterIDGivenUN.php?username=" + username;
            URL url = new URL(link);
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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

            StringBuilder sb = new StringBuilder();

            String line = "Initial Line";
            // Read Server Response
            for(int timeoutLineCounter = 0; timeoutLineCounter < 100; timeoutLineCounter++) {
                line = reader.readLine();
                if(line.contains("POSTERID")) {
                    // Quit loop if good line found
                    break;
                }
                // Skip all irrelevant lines
            }

            // Starting at index 8, take the suffix
            int id = Integer.parseInt(line.substring(8));

//            sb.append(reader.readLine());
//
//            callback.onTaskComplete(Integer.parseInt(sb.toString()));

            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}