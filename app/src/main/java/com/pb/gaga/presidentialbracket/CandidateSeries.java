package com.pb.gaga.presidentialbracket;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class CandidateSeries extends LineGraphSeries<DataPoint> {
    String name;

    CandidateSeries(String name, DataPoint[] inheritedSeries, Carousel nAcontext) {
        super(inheritedSeries);
        this.name = name;
    }


}
