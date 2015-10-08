package com.example.gaga.presidentialbracket;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;


public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private ArrayList<View> views = new ArrayList<View>();

    private MyLinearLayout cur = null;
    private MyLinearLayout next = null;
    private Carousel context;
    private FragmentManager fm;
    private String username;
    private int PosterID;
    private float scale;

    public MyPagerAdapter(Carousel context, FragmentManager fm, String username, int PosterID) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.username = username;
        this.PosterID = PosterID;
    }

    @Override
    public Fragment getItem(int position) {
        // Make first page big, others small
        if(position == Carousel.R_FIRST_PAGE)
            scale = Carousel.BIG_SCALE;
        else
            scale = Carousel.SMALL_SCALE;
        position = position % Carousel.R_PAGES;

        // MyPagerAdapter->MyFragment->Fragment
        return MyFragment.newInstance(context, position, scale, Carousel.Republican[position], this.username, this.PosterID);

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
        return Carousel.R_PAGES * Carousel.LOOPS;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset >= 0f && positionOffset <= 1f) {
            cur = getRootView(position);
            cur.setScaleBoth(Carousel.BIG_SCALE - Carousel.DIFF_SCALE * positionOffset);

            // If position is not penultimate or last (Swiped Left)
            // TEMP FIX: Modulo position
            if(position % Carousel.R_PAGES < Carousel.R_PAGES /* - 1 */) {
                next = getRootView(position + 1);
                next.setScaleBoth(Carousel.SMALL_SCALE + Carousel.DIFF_SCALE * positionOffset);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {}

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
        return "android:switcher:" + context.pager.getId() + ":" + position;
    }


}
