package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class DownloadComments extends AsyncTask<String, Void, Void> {
    static ArrayList<Comment> candidateCommentArrayList = new ArrayList<Comment>();

    DownloadComments() {
        Comment sample = new Comment("Trump", "MyPosterName", 1, "maermgamr");
        candidateCommentArrayList.clear();
        candidateCommentArrayList.add(sample);

    }

    @Override
    protected Void doInBackground(String... passedIn) {
        try {
            String candidate = passedIn[0];
            String link = "http://particlecollider.net76.net/downloadComments.php?candidate="+candidate;
            int CommentsInLinearList;

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            System.setProperty("http.keepAlive", "false");
            // Kill keep-alive property
            conn.setRequestProperty("connection", "close");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            // Skip until it works
            while (!(line = reader.readLine()).contains("COMMENTLINEARLIST")) {
                // Finished
            }
            CommentsInLinearList = Integer.parseInt(reader.readLine());
            // The Real JSON
            line = reader.readLine();
            sb.append(line);

            JSONObject jObject = new JSONObject(sb.toString());
            JSONArray myJSONArray = jObject.getJSONArray("CommentLinearList");
            if(myJSONArray != null) {
                for(int i=0; i<myJSONArray.length(); i++) {
                    JSONObject targetComment = myJSONArray.getJSONObject(i);
                    Comment PushComment = new Comment(candidate);
                    PushComment._ID = Integer.parseInt(targetComment.getString("ID"));
                    PushComment._Candidate = candidate;
                    PushComment._PosterName = targetComment.getString("PosterName");
                    PushComment._ReplyToId = Integer.parseInt(targetComment.getString("ReplyToID"));
                    PushComment._Rating = Integer.parseInt(targetComment.getString("Rating"));
                    PushComment._Comment = targetComment.getString("Comment");
                    // PushComment into candidateCommentArrayList
                    DownloadComments.candidateCommentArrayList.add(PushComment);
                }
                // getMenuInflater().inflate(R.menu., candidateCommentArrayList);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
