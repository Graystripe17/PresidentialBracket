package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


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
            "Description",
            "Strategy",
            "EconomyPlans",
            "SocialIssues",
            "ForeignPolicy",
            "Faults",
            "Record",
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

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            candidate = "Trump";
        } else {
            candidate = extras.getString("candidateName");
        }

        populateExpandableListView();
        registerClickCallback();


        // Change header title
        TextView TITLE = (TextView) findViewById(R.id.Candidate);
        TITLE.setText(getFullNameGivenName(candidate));

        // Start DownloadComments
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DownloadCommentsUpdated().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, candidate);
        } else {
            new DownloadCommentsUpdated().execute(candidate);
        }



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
                        new PostComment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, commentString, MainActivity.username, Integer.toString(MainActivity.PosterID), candidate);
                    } else {
                        new PostComment().execute(commentString, MainActivity.username, Integer.toString(MainActivity.PosterID), candidate);
                    }
                }
                commentHandler.setText("");
                Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }


    private void populateExpandableListView() {
        // Prepare LDH
        prepareListData();

        adapter = new MyExpandableListAdapter(this, listDataHeader, listDataChild);
        //adapter.notifyDataSetChanged();
        ExpandableListView list = (ExpandableListView) findViewById(R.id.ELVbios);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.commentsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
//                TextView textView = (TextView) viewClicked;
//                String message = "You clicked #" + position + ", which is string: " + textView.getText().toString();
//                Toast.makeText(Bios.this, message, Toast.LENGTH_LONG).show();
//
//                LinearLayout discussionLL = (LinearLayout) findViewById(R.id.Discussion);
//
//                EditText newReply = new EditText(Bios.this);
//                newReply.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT
//                ));
//                newReply.setId(arbitraryIDCounter++);
//                discussionLL.addView(newReply);
//                // Remove previous expired View
//                discussionLL.removeView(findViewById(arbitraryIDCounter - 1));
            }
        });
    }

    public void prepareListData() {
        // Load description based on candidate
        CandidateBioParagraphs cParagraphs = new CandidateBioParagraphs(candidate, this);

        listDataHeader = new ArrayList<String>(Arrays.asList(Categories));
        listDataChild = new HashMap<String, List<String>>();


        List<String> Description = new ArrayList<String>();
        Description.addAll(Arrays.asList(cParagraphs.Description));

        List<String> Strategy = new ArrayList<String>();
        Strategy.addAll(Arrays.asList(cParagraphs.Strategy));

        List<String> EconomyPlans = new ArrayList<String>();
        EconomyPlans.addAll(Arrays.asList(cParagraphs.EconomyPlans));


        List<String> SocialIssues = new ArrayList<String>();
        SocialIssues.addAll(Arrays.asList(cParagraphs.SocialIssues));

        List<String> ForeignPolicy = new ArrayList<String>();
        ForeignPolicy.addAll(Arrays.asList(cParagraphs.ForeignPolicy));

        List<String> Faults = new ArrayList<String>();
        Faults.addAll(Arrays.asList(cParagraphs.Faults));

        List<String> Record = new ArrayList<String>();
        Record.addAll(Arrays.asList(cParagraphs.Record));

        listDataChild.put(listDataHeader.get(0), Description);
        listDataChild.put(listDataHeader.get(1), Strategy);
        listDataChild.put(listDataHeader.get(2), EconomyPlans);
        listDataChild.put(listDataHeader.get(3), SocialIssues);
        listDataChild.put(listDataHeader.get(4), ForeignPolicy);
        listDataChild.put(listDataHeader.get(5), Faults);
        listDataChild.put(listDataHeader.get(6), Record);
    }

    public String getFullNameGivenName (String cname) {
        switch(cname) {
            // Democrats
            case "Clinton":
                return "HILLARY RODHAM CLINTON";
            case "Sanders":
                return "BERNARD SANDERS";
            case "Biden":
                return "JOSEPH BIDEN";
            case "Webb":
                return "JAMES HENRY WEBB JR.";
            case "O'Malley":
                return "MARTIN O'MALLEY";
            case "Chafee":
                return "LINCOLN DAVENPORT CHAFEE";

            // Republicans
            case "Trump":
                return "DONALD JOHN TRUMP SR.";
            case "Carson":
                return "BEN SOLOMON CARSON";
            case "Fiorina":
                return "CARA CARLETON FIORINA";
            case "Rubio":
                return "MARCO RUBIO";
            case "Bush":
                return "JOHN ELLIS BUSH";
            case "Cruz":
                return "RAFAEL EDWARD CRUZ";
            case "Kasich":
                return "JOHN RICHARD KASICH";
            case "Huckabee":
                return "MICHAEL DALE HUCKABEE";
            case "Christie":
                return "CHRISTOPHER JAMES CHRISTIE";
            case "Paul":
                return "RANDAL HOWARD PAUL";
            case "Jindal":
                return "PIYUSH JINDAL";
            case "Santorum":
                return "RICHARD JOHN SANTORUM";
            case "Graham":
                return "LINDSEY OLIN GRAHAM";
            case "Pataki":
                return "GEORGE ELMER PATAKI";
            case "Walker":
                return "SCOTT KEVIN WALKER";
            case "Perry":
                return "JAMES RICHARD PERRY";
            case "Gilmore":
                return "JAMES STUART GILMORE III";

            // Cannot find; return cat
            default:
                return "";
        }
    }
}