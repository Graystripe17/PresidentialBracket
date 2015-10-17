package com.example.gaga.presidentialbracket;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class CandidateSeries extends LineGraphSeries<DataPoint> {
    String name;
    String description;

    CandidateSeries(String name, DataPoint[] inheritedSeries, Carousel nAcontext) {
        super(inheritedSeries);
        this.name = name;
        this.description = getStringDescriptionByName(name, nAcontext);
    }

    public static String getStringDescriptionByName(String aString, Carousel nAcontext) {
        int resId = nAcontext.getResources().getIdentifier(aString, "string", nAcontext.getPackageName());
        return nAcontext.getString(resId);
    }


}
