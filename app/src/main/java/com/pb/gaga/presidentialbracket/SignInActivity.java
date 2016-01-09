package com.pb.gaga.presidentialbracket;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SignInActivity extends AsyncTask<String, Void, String> {
    private TextView statusField, roleField;
    private Context context;
    private String returned;

    public SignInActivity(Context context, TextView statusField) {
        this.context = context;
        this.statusField = statusField;
    }


    @Override
    protected String doInBackground(String... arg0) {
        int tries = 6;
        // Give the server a max of 5 tries
        // Otherwise SocketException Connection reset by peer
        try {
            String username = (String) arg0[0];
            String password = (String) arg0[1];
            // EDIT LINK
            String link = "http://particlecollider.net76.net/loginPost.php";


            URL url = new URL(link);
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                // break;
            }
            returned = sb.toString();

            try {
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(context, returned, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }


            Intent intent1 = new Intent(context, nextActivity.class);
            intent1.putExtra("D-Polls", "IntentKey");
            context.startActivity(intent1);


            // Return PHP Server's response!
            return sb.toString();

        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        this.statusField.setText("Login Attempted");
    }
}