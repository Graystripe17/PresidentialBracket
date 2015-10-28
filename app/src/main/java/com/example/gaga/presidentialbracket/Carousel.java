package com.example.gaga.presidentialbracket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Carousel extends FragmentActivity {
    // final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);
    public final static int R_PAGES = 15; // # of Candidates
    public final static int D_PAGES = 3;
    public final static int LOOPS = 1000;
    public final static int R_FIRST_PAGE = R_PAGES * LOOPS / 2;
    public final static int D_FIRST_PAGE = D_PAGES * LOOPS / 2;
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.4f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    public final static int PAGE_MARGINS = -800;
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
            "Gilmore"
    };
    public final static String[] Democrat = {
            "Clinton",
            "Sanders",
            "O'Malley",
    };

    public MyPagerAdapter Radapter, Dadapter;
    public ViewPager Rpager, Dpager;
    public final int poll_data_quantity = 23;
    public final int graph_partitions = 4;

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
        Rpager.setBackgroundColor(getResources().getColor(R.color.backdrop_shadow));

        Dpager.setAdapter(Dadapter);
        Dpager.addOnPageChangeListener(Dadapter);
        Dpager.setBackgroundColor(getResources().getColor(R.color.backdrop_shadow));

        // Set current item to the middle page so we can fling to both
        // directions left and right
        Rpager.setCurrentItem(R_FIRST_PAGE);
        Dpager.setCurrentItem(D_FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        Rpager.setOffscreenPageLimit(poll_data_quantity);
        Dpager.setOffscreenPageLimit(poll_data_quantity);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        Rpager.setPageMargin(PAGE_MARGINS);
        Dpager.setPageMargin(PAGE_MARGINS);




        // DATES OF POLLING
        ArrayList<Date> PollDates = new ArrayList<Date>();
        for(int days_of_October = 1; days_of_October <= poll_data_quantity; days_of_October++) {
            PollDates.add(new Date(115, 9, days_of_October));
        }
//        for(int month = 6; month <= 10; month++) {
//            // June 1-30
//            for (int day = 1; day <= 30; day++) {
//                PollDates.add(new Date(115, month, day));
//            }
//            if (month == 7 || month == 8 || month == 10) {
//                PollDates.add(new Date(115, month, 31));
//            }
//        }


        // GOP Graph
        GraphView Rgraph = (GraphView) findViewById(R.id.RepublicanGraph);
        Rgraph.setTitle("Republican");
        Rgraph.getViewport().setXAxisBoundsManual(true);
        Rgraph.getViewport().setYAxisBoundsManual(true);
        Rgraph.getViewport().setMinX((double)(PollDates.get(0).getTime()));
        Rgraph.getViewport().setMaxX((double)(PollDates.get(poll_data_quantity - 1).getTime()));
        Rgraph.getViewport().setMinY(0);
        Rgraph.getViewport().setMaxY(30);
        Rgraph.getViewport().setScalable(false);
        Rgraph.getViewport().setScrollable(false);
        // Set date label formatter
        Rgraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this) {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    // show normal x values
                    String full_date = super.formatLabel(value, isValueX);
                    return full_date.substring(0, full_date.length() - 5);
                } else {
                    // show added symbol
                    return super.formatLabel(value, isValueX) + "%";
                }
            }
        });
        Rgraph.getGridLabelRenderer().setNumHorizontalLabels(graph_partitions);




        // DNC Graph
        GraphView Dgraph = (GraphView) findViewById(R.id.DemocratGraph);
        Dgraph.setTitle("Democrat");
        Dgraph.getViewport().setXAxisBoundsManual(true);
        Dgraph.getViewport().setYAxisBoundsManual(true);
        Dgraph.getViewport().setMinX(PollDates.get(0).getTime());
        Dgraph.getViewport().setMaxX(PollDates.get(poll_data_quantity - 1).getTime());
        Dgraph.getViewport().setMinY(0);
        Dgraph.getViewport().setMaxY(50);
        Dgraph.getViewport().setScalable(false);
        Dgraph.getViewport().setScrollable(false);
        // Set date label formatter
        Dgraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this) {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    // show normal x values
                    String full_date = super.formatLabel(value, isValueX);
                    return full_date.substring(0, full_date.length() - 5);
                } else {
                    // show added symbol
                    return super.formatLabel(value, isValueX) + "%";
                }
            }
        });
        Dgraph.getGridLabelRenderer().setNumHorizontalLabels(graph_partitions); // Number of poll dates





        Resources res = getResources();
        // Array of colors
        int[] RedRainbow = res.getIntArray(R.array.red_rainbow);
        int[] BlueRainbow = res.getIntArray(R.array.blue_rainbow);
        // Poll data Democrats
        final double[][] demPollData = new double[][] {
                //   1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27    28    29    30    31
                { 40.8, 40.8, 41.4, 41.5, 41.6, 41.6, 41.6, 41.6, 41.6, 41.6, 42.0, 42.0, 43.3, 43.3, 43.3, 43.5, 43.2, 43.8, 44.7, 47.0, 47.8, 47.8, 47.8}, // Clinton
                { 26.8, 26.8, 26.4, 25.4, 25.4, 25.2, 25.2, 25.2, 25.2, 25.2, 25.4, 25.4, 25.1, 25.1, 25.1, 23.5, 23.4, 23.5, 24.0, 25.4, 25.7, 25.7, 25.7}, // Sanders
                {  0.7,  0.7,  0.6,  0.6,  0.6,  0.7,  0.7,  0.7,  0.7,  0.7,  0.6,  0.6,  0.4,  0.4,  0.4,  0.5,  0.6,  0.8,  0.5,  0.6,  0.5,  0.5,  0.5}, // O'Malley
        };

        final double[][] repPollData = new double[][] {
                //   1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27    28    29    30    31
                { 23.3, 23.6, 22.8, 22.8, 22.8, 23.2, 23.2, 23.2, 23.1, 22.9, 23.7, 23.7, 23.4, 23.4, 23.4, 23.8, 23.6, 23.8, 24.0, 26.2, 27.2, 27.2, 27.2,}, // Trump
                { 16.3, 16.3, 17.3, 17.3, 17.3, 17.2, 17.2, 17.2, 17.6, 17.7, 18.4, 18.4, 19.1, 19.1, 19.1, 19.0, 19.6, 21.3, 21.4, 21.2, 21.3, 21.3, 21.4,}, // Carson
                { 11.8, 11.3, 11.0, 11.0, 11.0, 10.4, 10.4, 10.4,  9.9,  9.9,  8.9,  8.9,  8.3,  8.3,  8.3,  7.8,  7.8,  6.5,  6.6,  5.6,  5.5,  5.5,  5.4,}, // Fiorina
                {  9.5,  9.3,  9.5,  9.5,  9.5,  9.9,  9.9,  9.9,  9.8,  9.9,  9.9,  9.9,  9.9,  9.9,  9.9,  9.7, 10.0, 10.3, 10.3,  8.8,  9.0,  9.0,  9.2,}, // Rubio
                {  9.0,  8.3,  8.3,  8.3,  8.3,  8.4,  8.4,  8.4,  8.4,  9.6,  7.1,  7.1,  7.3,  7.3,  7.3,  7.3,  8.0,  8.0,  8.0,  7.0,  7.0,  7.0,  7.2,}, // Bush
                {  6.2,  6.1,  6.1,  6.1,  6.1,  6.2,  6.2,  6.2,  6.3,  6.1,  6.7,  6.7,  7.0,  7.0,  7.0,  7.3,  7.6,  8.0,  8.2,  8.4,  8.0,  8.0,  7.8,}, // Cruz
                {  3.3,  3.0,  3.1,  3.1,  3.1,  3.2,  3.2,  3.2,  3.4,  3.6,  3.3,  3.3,  2.9,  2.9,  2.9,  2.3,  2.6,  2.8,  2.8,  2.0,  2.0,  2.0,  2.0,}, // Kasich
                {    0,  2.9,  2.8,  2.8,  2.8,  2.9,  2.9,  2.9,  2.5,  2.6,  2.4,  2.4,  2.7,  2.7,  2.7,  2.8,  3.0,  3.3,  3.2,  3.8,  3.7,  3.7,  4.0,}, // Huckabee
                {  3.0,  2.7,  2.6,  2.6,  2.6,  2.6,  2.6,  2.6,  2.5,  2.6,  2.4,  2.4,  1.9,  1.9,  1.9,  1.7,  1.8,  2.0,  1.8,  2.4,  2.5,  2.5,  2.4,}, // Christie
                {  9.0,  2.3,  2.4,  2.4,  2.4,  2.3,  2.3,  2.3,  2.1,  0.3,  2.6,  2.6,  2.7,  2.7,  2.7,  2.7,  2.8,  3.0,  2.8,  3.6,  3.3,  3.3,  3.2,}, // Paul
                {  0.5,  0.4,  0.5,  0.5,  0.5,  0.6,  0.6,  0.6,  0.6,  0.7,  0.6,  0.6,  0.7,  0.7,  0.7,  0.7,  0.8,  0.8,  0.6,  0.4,  0.3,  0.3,  0.4,}, // Jindal
                {  0.5,  0.4,  0.4,  0.4,  0.4,  0.6,  0.6,  0.6,  0.5,  0.6,  0.6,  0.6,  0.6,  0.6,  0.6,  0.5,  0.6,  0.8,  0.6,  0.6,  0.5,  0.5,  0.4,}, // Santorum
                {  0.2,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.4,  0.4,  0.4,  0.4,  0.4,  0.4,  0.4,  0.5,  0.4,  0.3,  0.2,  0.4,  0.5,  0.5,  0.6,}, // Graham
                {  0.0,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.4,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.3,  0.4,  0.5,  0.4,  0.2,  0.3,  0.3,  0.4,}, // Pataki
                {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,}, // Gilmore
        };

//        //   1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    21    22    23    24    25    26    27    28    29    30    31
//        {  4.5,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  4.0,  3.6,  3.6,  3.6,  3.6,  3.6,  3.6,  3.2,  3.2,  4.3,  4.3,  4.3,  4.3,  4.2,  4.2,  4.2,
//                6.0,  6.0,  6.0,  6.0,  6.5,  6.5,  6.5,  6.5,  6.5,  6.5,  6.5,  6.5,  9.3, 10.8, 10.8, 15.0, 15.0, 15.0, 15.0, 16.8, 18.0, 18.2, 18.2, 18.2, 18.2, 18.2, 18.2, 19.2, 19.8, 20.8,
//                21.2, 20.8, 22.5, 22.2, 23.2, 24.3, 24.3, 24.3, 24.3, 24.3, 22.8, 22.8, 22.5, 22.5, 22.5, 22.3, 22.3, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 23.5, 23.5, 23.5, 25.7, 25.7,
//                26.5, 26.5, 27.2, 27.2, 27.8, 27.8, 27.8, 29.0, 29.8, 29.8, 29.8, 29.8, 30.4, 30.2, 30.5, 30.5, 30.5, 30.5, 28.5, 28.5, 28.0, 25.7, 24.0, 24.0, 24.0, 23.4, 23.4, 23.4, 23.3,
//                23.3, 23.6}, // Trump


        for(int i = 0; i < Carousel.Republican.length; i++) {
            final int innerClassOrder = i;
            DataPoint[] Rpopularity = new DataPoint[poll_data_quantity];
            for(int numdate = 0; numdate < poll_data_quantity; numdate++) {
                Rpopularity[numdate] = new DataPoint(PollDates.get(numdate), repPollData[i][numdate]);
            }
            CandidateSeries targetRepublican = new CandidateSeries(Carousel.Republican[i], Rpopularity, this);
            targetRepublican.setColor(RedRainbow[i]);
            targetRepublican.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dp) {
                    final Toast declare = Toast.makeText(Carousel.this, Republican[innerClassOrder] + " " + repPollData[innerClassOrder][poll_data_quantity - 1], Toast.LENGTH_SHORT);
                    declare.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            declare.cancel();
                        }
                    }, 500);
                }
            });
            Rgraph.addSeries(targetRepublican);
        }

        for(int j = 0; j < Carousel.Democrat.length; j++) {
            final int innerClassOrder = j;
            DataPoint[] Dpopularity = new DataPoint[poll_data_quantity];
            for(int numdate = 0; numdate < poll_data_quantity; numdate++) {
                Dpopularity[numdate] = new DataPoint(PollDates.get(numdate), demPollData[j][numdate]);
            }
            CandidateSeries targetDemocrat = new CandidateSeries(Carousel.Democrat[j], Dpopularity, this);
            targetDemocrat.setColor(BlueRainbow[j]);
            targetDemocrat.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dp) {
                    final Toast declare = Toast.makeText(Carousel.this, Democrat[innerClassOrder] + " " + demPollData[innerClassOrder][poll_data_quantity - 1], Toast.LENGTH_SHORT);
                    declare.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            declare.cancel();
                        }
                    }, 500);
                }
            });
            Dgraph.addSeries(targetDemocrat);
        }



    }

    @Override
    public void onBackPressed() {
    }
}
