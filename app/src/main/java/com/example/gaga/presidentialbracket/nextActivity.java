package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;


public class nextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        // Class extends Context
        // Alternatively nextActivity.this...
        Context context = nextActivity.this;
        Bundle extras = getIntent().getExtras();

        // GOP Graph
        GraphView graph = (GraphView) findViewById(R.id.RepublicanGraph);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(40);
        graph.getViewport().setScrollable(true);
        graph.setTitle("R Graph");


        Resources res = getResources();
        int[] RedRainbow = context.getResources().getIntArray(R.array.red_rainbow);

        for(int i = 0; i < Carousel.Republican.length; i++) {
            DataPoint[] popularity = new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(3, i),
            };
//            CandidateSeries Republican = new CandidateSeries(Carousel.Republican[i], popularity, this);
//            Republican.setColor(RedRainbow[i / 3]);
//            graph.addSeries(Republican);
        }

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

}
