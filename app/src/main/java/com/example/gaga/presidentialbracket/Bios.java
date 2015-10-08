package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Bios extends AppCompatActivity implements View.OnClickListener {

    MyExpandableListAdapter adapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    int arbitraryIDCounter = 8888;
    String CommentLinearListJSONString;
    Context context = Bios.this;
    String candidate;

    // Create list of items
    String[] Categories = {
            "Description", // Tag line
            "Specs", // Polls
            "Stats", // Years, speaking style, scandals
            "Plans", // Ideology
            "Weakness", // Scandals
            "Record", // History and voting
    };

    ArrayList<Comment> commentArrayList;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bios);


        // Create Tabs
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();

        TabHost.TabSpec ts = tabhost.newTabSpec("tag1");
        ts.setContent(R.id.Details);
        ts.setIndicator("Description");
        tabhost.addTab(ts);

        ts = tabhost.newTabSpec("tag2");
        ts.setContent(R.id.Discussion);
        ts.setIndicator("Discussion");
        tabhost.addTab(ts);
        // Create Tabs

        // Inflate ListView Comments
//        ListView commentsListView = (ListView) findViewById(R.id.commentsListView);
//        CommentsListAdapter commentAdapter = new CommentsListAdapter(context, R.layout.bios, commentArrayList);
//        commentsListView.setAdapter(commentAdapter);
        // Inflate ListView Comments

        populateExpandableListView();
        registerClickCallback();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            candidate = "Trump";
        } else {
            candidate = extras.getString("candidateName");
        }

        // Change header title
        TextView TITLE = (TextView) findViewById(R.id.Candidate);
        TITLE.setText(candidate.toUpperCase());

        // Start DownloadComments
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DownloadCommentsUpdated().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, candidate);
        } else {
            new DownloadCommentsUpdated().execute(candidate);
        }

        // TODO: Form new thread
        // Abandon BaseAdapter
        // Warning may not be done loading


        // Set on tab change listener (refresh comments when discussion triggered)
        final TabHost myTH = (TabHost)findViewById(R.id.tabHost);
        myTH.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // It is only possible to inflate the ListView when the tab has changed,
                // giving DownloadCommentsUpdated an ample amount of time to load comments
                CommentsListAdapter ContentAdapter = null;
                if (DownloadCommentsUpdated.candidateCommentArrayList != null) {
                    ContentAdapter = new CommentsListAdapter(context, R.layout.comment_items, DownloadCommentsUpdated.candidateCommentArrayList);
                    // Configure list view
                    ListView list = (ListView) findViewById(R.id.commentsListView);
                    list.setAdapter(ContentAdapter);
                } else {
                    Toast toast = Toast.makeText(context, "Loading Error", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case(R.id.Post):
                // Post Comment
                EditText commentHandler = (EditText) findViewById(R.id.comment);
                String commentString = commentHandler.getText().toString();
                if(!commentString.isEmpty()) {
                    // Execute doInBackground
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new PostComment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, commentString, MainActivity.username, Integer.toString(MainActivity.PosterID));
                    } else {
                        new PostComment().execute(commentString, MainActivity.username, Integer.toString(MainActivity.PosterID));
                    }
                }
                commentHandler.setText("");
                break;
            default:
                break;
        }
    }


    private void populateExpandableListView() {
        // Prepare LDH
        prepareListData();

        adapter = new MyExpandableListAdapter(this, listDataHeader, listDataChild);
        ExpandableListView list = (ExpandableListView) findViewById(R.id.ELVbios);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.commentsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked #" + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(Bios.this, message, Toast.LENGTH_LONG).show();

                LinearLayout discussionLL = (LinearLayout) findViewById(R.id.Discussion);

                EditText newReply = new EditText(Bios.this);
                newReply.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                newReply.setId(arbitraryIDCounter++);
                discussionLL.addView(newReply);
                // Remove previous expired View
                discussionLL.removeView(findViewById(arbitraryIDCounter - 1));
            }
        });
    }

    public void prepareListData() {
        listDataHeader = new ArrayList<String>(Arrays.asList(Categories));
        listDataChild = new HashMap<String, List<String>>();

        // Add child data
        listDataHeader.add("HEADER 1");
        listDataHeader.add("HEADER 2");
        listDataHeader.add("HEADER 3");

        List<String> header1 = new ArrayList<String>();
        header1.add("A");
        header1.add("B");

        List<String> header2 = new ArrayList<String>();
        header2.add("C");
        header2.add("D");

        List<String> header3 = new ArrayList<String>();
        header3.add("E");
        header3.add("F");

        List<String> header4 = new ArrayList<String>();
        header4.add("G");
        header4.add("H");

        listDataChild.put(listDataHeader.get(0), header1);
        listDataChild.put(listDataHeader.get(1), header2);
        listDataChild.put(listDataHeader.get(2), header3);
        listDataChild.put(listDataHeader.get(3), header4);


    }


}


