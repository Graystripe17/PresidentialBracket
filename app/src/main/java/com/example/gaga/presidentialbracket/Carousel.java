package com.example.gaga.presidentialbracket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Carousel extends FragmentActivity {
    // final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);
    public final static int R_PAGES = 16; // # of Candidates
    public final static int D_PAGES = 6;
    public final static int LOOPS = 1000;
    public final static int R_FIRST_PAGE = R_PAGES * LOOPS / 2;
    public final static int D_FIRST_PAGE = D_PAGES * LOOPS / 2;
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.4f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    public final static String[] Republican = {
            "Trump",
            "Carson",
            "Fiorina",
            "Rubio",
            "Bush",
            "Cruz",
            "Kasich",
            "Huckabee",
            "Christie",
            "Paul",
            "Jindal",
            "Santorum",
            "Graham",
            "Pataki",
            "Walker",
            "Perry",
    };
    public final static String[] Democrat = {
            "Clinton",
            "Sanders",
            "Biden",
            "Webb",
            "O'Malley",
            "Chafee",
    };

    public MyPagerAdapter Radapter, Dadapter;
    public ViewPager Rpager, Dpager;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);


        Rpager = (ViewPager) findViewById(R.id.GOPviewpager);
        Dpager = (ViewPager) findViewById(R.id.democratViewPager);

        // Carousel->MyPagerAdapter->MyFragment->Fragment
        Radapter = new MyPagerAdapter(this, // context
                                    this.getSupportFragmentManager(), // fm
                                    MainActivity.username, // UN
                                    MainActivity.PosterID, // PosterID
                                    true // Republican adapter is TRUE
        );
        Dadapter = new MyPagerAdapter(this,
                                    this.getSupportFragmentManager(),
                                    MainActivity.username,
                                    MainActivity.PosterID,
                                    false
        );


        Rpager.setAdapter(Radapter);
        Rpager.addOnPageChangeListener(Radapter);

        Dpager.setAdapter(Dadapter);
        Dpager.addOnPageChangeListener(Dadapter);


        // Set current item to the middle page so we can fling to both
        // directions left and right
        Rpager.setCurrentItem(R_FIRST_PAGE);
        Dpager.setCurrentItem(D_FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        Rpager.setOffscreenPageLimit(3);
        Dpager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        Rpager.setPageMargin(-800);
        Dpager.setPageMargin(-800);




        // DATES OF POLLING
        Date poll0 = new Date(115, 6, 1); // YMD since 1900
        Date poll1 = new Date(115, 6, 5);
        Date poll2 = new Date(115, 6, 9);

        // GOP Graph
        GraphView Rgraph = (GraphView) findViewById(R.id.RepublicanGraph);
        Rgraph.setTitle("Republican");
        Rgraph.getViewport().setXAxisBoundsManual(true);
        Rgraph.getViewport().setYAxisBoundsManual(true);
        Rgraph.getViewport().setMinX((double)(poll0.getTime()));
        Rgraph.getViewport().setMaxX((double)(poll2.getTime()));
        Rgraph.getViewport().setMinY(0);
        Rgraph.getViewport().setMaxY(40);
        Rgraph.getViewport().setScalable(true);
        Rgraph.getViewport().setScrollable(true);
        // Set date label formatter
        Rgraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this) {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show added symbol
                    return super.formatLabel(value, isValueX) + "%";
                }
            }
        });
        Rgraph.getGridLabelRenderer().setNumHorizontalLabels(3);




        // DNC Graph
        GraphView Dgraph = (GraphView) findViewById(R.id.DemocratGraph);
        Dgraph.setTitle("Democrat");
        Dgraph.getViewport().setXAxisBoundsManual(true);
        Dgraph.getViewport().setYAxisBoundsManual(true);
        Dgraph.getViewport().setMinX(poll0.getTime());
        Dgraph.getViewport().setMaxX(poll2.getTime());
        Dgraph.getViewport().setMinY(0);
        Dgraph.getViewport().setMaxY(40);
        Dgraph.getViewport().setScalable(true);
        Dgraph.getViewport().setScrollable(true);
        // Set date label formatter
        Dgraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        Dgraph.getGridLabelRenderer().setNumHorizontalLabels(3); // Number of poll dates





        Resources res = getResources();
        // Array of colors
        int[] RedRainbow = res.getIntArray(R.array.red_rainbow);
        // Poll data Democrats
        int[][] demPollData = new int[][] {
                {0, 2, 13, 15}, // Clinton
                {0, 2, 3, 15}, // Sanders
                {0, 12, 3, 15}, // Biden
                {1, 23, 23, 25}, // Webb
                {6, 16, 26, 36}, // O'Malley
                {2, 12, 22, 32}, // Chafee
        };

        int[][] repPollData = new int[][] {
                {0, 2, 3, 15}, // Trump
                {0, 2, 7, 5}, // Carson
                {0, 2, 3, 8}, // Fiorina
                {4, 8, 16, 32}, // Rubio
                {4, 8, 16, 32}, // Bush
                {4, 8, 16, 32}, // Cruz
                {4, 8, 16, 32}, // Kasich
                {4, 8, 16, 32}, // Huckabee
                {4, 8, 16, 32}, // Christie
                {4, 8, 16, 32}, // Paul
                {4, 8, 16, 32}, // Jindal
                {4, 8, 16, 32}, // Santorum
                {4, 8, 16, 32}, // Graham
                {4, 8, 16, 32}, // Pataki
                {4, 8, 16, 32}, // Walker
                {4, 8, 16, 32}, // Perry
        };



        for(int i = 0; i < Carousel.Republican.length; i++) {
            DataPoint[] popularity = new DataPoint[] {
                new DataPoint(poll0, repPollData[i][0]),
                new DataPoint(poll1, repPollData[i][1]),
                new DataPoint(poll2, repPollData[i][2]),
            };
            CandidateSeries targetRepublican = new CandidateSeries(Republican[i], popularity, this);
            targetRepublican.setColor(RedRainbow[i]);
            Rgraph.addSeries(targetRepublican);
        }




    }

    @Override
    public void onBackPressed() {
    }
}
