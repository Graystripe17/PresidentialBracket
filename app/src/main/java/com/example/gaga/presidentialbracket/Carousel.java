package com.example.gaga.presidentialbracket;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Carousel extends FragmentActivity {
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
    public final static String[] Democrats = {
            "Clinton",
            "Sanders",
            "Biden",
            "Webb",
            "O'Malley",
            "Chafee",
    };

    public MyPagerAdapter adapter;
    public ViewPager pager;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);


        pager = (ViewPager) findViewById(R.id.myviewpager);

        // Carousel->MyPagerAdapter->MyFragment->Fragment
        adapter = new MyPagerAdapter(this, // context
                                    this.getSupportFragmentManager(), // fm
                                    MainActivity.username, // UN
                                    MainActivity.PosterID // PosterID
        );
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(adapter);


        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(R_FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        pager.setPageMargin(-800);
    }

    public void addView(ImageView newPage) {
        pager.setCurrentItem(0, true);
    }

}
