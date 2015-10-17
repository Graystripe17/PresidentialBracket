package com.example.gaga.presidentialbracket;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;


public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private ArrayList<View> views = new ArrayList<View>();

    // CURRENT LINEAR LAYOUT
    private MyLinearLayout cur = null;
    // NEXT LINEAR LAYOUT
    private MyLinearLayout next = null;
    private Carousel context;
    private FragmentManager fm;
    private String username;
    private String candidate;
    private int PosterID;
    private float scale;
    private boolean isRepublican;

    public MyPagerAdapter(Carousel context, FragmentManager fm, String username, int PosterID, boolean isRepub) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.username = username;
        this.PosterID = PosterID;
        this.isRepublican = isRepub;
    }

    @Override
    public Fragment getItem(int position) {
        if(isRepublican) {
            // Make first page big, others small
            if(position == Carousel.R_FIRST_PAGE)
                scale = Carousel.BIG_SCALE;
            else
                scale = Carousel.SMALL_SCALE;
            position = position % Carousel.R_PAGES;
            candidate = Carousel.Republican[position];
            // Carousel->MyPagerAdapter->MyFragment->Fragment
            return MyFragment.newInstance(context, position, scale, candidate, this.username, this.PosterID, isRepublican);
        } else {
            if(position == Carousel.D_FIRST_PAGE)
                scale = Carousel.BIG_SCALE;
            else
                scale = Carousel.SMALL_SCALE;
            position = position % Carousel.D_PAGES;
            candidate = Carousel.Democrat[position];
            return MyFragment.newInstance(context, position, scale, candidate, this.username, this.PosterID, isRepublican);
        }
    }

    @Override
    public int getItemPosition (Object object) {
        int index = views.indexOf(object);
        if(index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public int getCount() {
        if(isRepublican)
            return Carousel.R_PAGES * Carousel.LOOPS;
        else
            return Carousel.D_PAGES * Carousel.LOOPS;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // position: index of the first page currently being displayed. Page position+1 will be visible if positionOffset is nonzero.
        // positionOffset: Value from [0, 1) indicating the offset from the page at position.
        // The following if statement is always true
        if (positionOffset >= 0f && positionOffset <= 1f) {
            if (isRepublican) {
                cur = getRootView(position);
                cur.setScaleBoth(Carousel.BIG_SCALE - Carousel.DIFF_SCALE * positionOffset);


                // If position is not penultimate or last (Swiped Left)
                // TEMP FIX: Modulo position
                // TODO: SIMPLIFY
                if (position % Carousel.R_PAGES < Carousel.R_PAGES /* - 1 */) {
                    next = getRootView(position + 1);
                    next.setScaleBoth(Carousel.SMALL_SCALE + Carousel.DIFF_SCALE * positionOffset);
                }
            } else {
                if (positionOffset >= 0f && positionOffset <= 1f) {
                    cur = getRootView(position);
                    cur.setScaleBoth(Carousel.BIG_SCALE - Carousel.DIFF_SCALE * positionOffset);


                    if (position % Carousel.D_PAGES < Carousel.D_PAGES) {
                        next = getRootView(position + 1);
                        next.setScaleBoth(Carousel.SMALL_SCALE + Carousel.DIFF_SCALE * positionOffset);
                    }
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        // TODO: NEXT TIME RTFM U DIP
        // Called when new Page becomes selected, animation not necessarily complete
        if(isRepublican)
            // Update onClick listener in MyFragment
            MyFragment.current_front_republican = Carousel.Republican[position % Carousel.R_PAGES];
        else
            MyFragment.current_front_democrat = Carousel.Democrat[position % Carousel.D_PAGES];

    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private MyLinearLayout getRootView(int position) {
        String t0 = this.getFragmentTag(position);
        Fragment t1 = fm.findFragmentByTag(t0);
        View t2 = t1.getView();
        View t3 = t2.findViewById(R.id.Root);
        return (MyLinearLayout) t3;
//        return (MyLinearLayout) fm.findFragmentByTag(this.getFragmentTag(position))
//                .getView().findViewById(R.id.Root);
    }

    private String getFragmentTag(int position) {
        if(isRepublican) {
            return "android:switcher:" + context.Rpager.getId() + ":" + position;
        } else {
            return "android:switcher:" + context.Dpager.getId() + ":" + position;
        }
    }


}
