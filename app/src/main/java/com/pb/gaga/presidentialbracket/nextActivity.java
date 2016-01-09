package com.pb.gaga.presidentialbracket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class nextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        // Class extends Context
        // Alternatively nextActivity.this...
        Context context = nextActivity.this;
        Bundle extras = getIntent().getExtras();
//
//        // GOP Graph
//        GraphView Rgraph = (GraphView) findViewById(R.id.RepublicanGraph);
//        Rgraph.getViewport().setYAxisBoundsManual(true);
//        Rgraph.getViewport().setMinY(0);
//        Rgraph.getViewport().setMaxY(40);
//        Rgraph.getViewport().setScrollable(true);
//        Rgraph.setTitle("R Graph");
//
//        // DNC Graph
//        GraphView Dgraph = (GraphView) findViewById(R.id.DemocratGraph);
//        Dgraph.getViewport().setYAxisBoundsManual(true);
//        Dgraph.getViewport().setMinY(0);
//        Dgraph.getViewport().setMaxY(40);
//        Dgraph.getViewport().setScrollable(true);
//        Dgraph.setTitle("D graph");
//
//
//        Resources res = getResources();
//        int[] RedRainbow = context.getResources().getIntArray(R.array.red_rainbow);

//        for(int i = 0; i < Carousel.Republican.length; i++) {
//            DataPoint[] popularity = new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 2),
//                new DataPoint(3, i),
//            };
//            CandidateSeries Republican = new CandidateSeries(Carousel.Republican[i], popularity, this);
//            Republican.setColor(RedRainbow[i / 3]);
//            graph.addSeries(Republican);
//        }

        Intent switchToCarousel = new Intent(nextActivity.this, Carousel.class);
        if(extras != null) {
            switchToCarousel.putExtras(extras);
        }
        startActivity(switchToCarousel);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static public boolean isRepublicanByName(String cName) {
        if(cName == null) return true;
        switch(cName) {
            // Democrats
            case "Clinton":
                return false;
            case "Sanders":
                return false;
            case "Biden":
                return false;
            case "Webb":
                return false;
            case "O'Malley":
                return false;
            case "Chafee":
                return false;

            // Republicans
            case "Trump":
                return true;
            case "Carson":
                return true;
            case "Fiorina":
                return true;
            case "Rubio":
                return true;
            case "Bush":
                return true;
            case "Cruz":
                return true;
            case "Kasich":
                return true;
            case "Huckabee":
                return true;
            case "Christie":
                return true;
            case "Paul":
                return true;
            case "Jindal":
                return true;
            case "Santorum":
                return true;
            case "Graham":
                return true;
            case "Pataki":
                return true;
            case "Walker":
                return true;
            case "Perry":
                return true;

            // Cannot find; return cat
            default:
                return true;
        }

    }

}
