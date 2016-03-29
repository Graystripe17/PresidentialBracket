package com.pb.gaga.presidentialbracket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

// This class extends and returns the support Fragment
public class MyFragment extends Fragment {

    Map<String, Drawable> mLLFacesCache = new HashMap<String, Drawable>();

    public static int small_pos;
    public static boolean isR;
    public static String current_front_republican = "Trump";
    public static String current_front_democrat = "Clinton";
    public static Context tcontext;

    // Takes Fragment, returns Fragment
    public static Fragment newInstance(Carousel context, int pos, float scale, String candidate, String username, int PosterID, boolean isRepub) {
        tcontext = context;
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        b.putString("candidate", candidate);
        b.putString("UN", username);
        b.putInt("PosterID", PosterID);
        b.putBoolean("isRepublican", isRepub);
        small_pos = pos;
        isR = isRepub;
        // MyPagerAdapter->MyFragment->Fragment
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }


    // Source of lag
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
//        mLLFacesCache = new LruCache<String, Drawable>(cacheSize) {
//            @Override
//            protected int sizeOf(String key, Drawable targetDrawable) {
//                return targetDrawable.getByteCount() / 1024;
//            }
//        };



        final String candidate = this.getArguments().getString("candidate");
        final String username = this.getArguments().getString("UN");
        final int PosterID = this.getArguments().getInt("PosterID");
        final int pos = this.getArguments().getInt("pos");
        float scale = this.getArguments().getFloat("scale");
        final boolean isRepublican = this.getArguments().getBoolean("isRepublican");

        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.mf, container, false);

        TextView tv = (TextView) l.findViewById(R.id.CandidateName);
        tv.setText(candidate.toUpperCase());
        if(isRepublican) {
            tv.setTextColor(getResources().getIntArray(R.array.red_rainbow)[pos % Carousel.Republican.length]);
        } else {
            tv.setTextColor(getResources().getIntArray(R.array.blue_rainbow)[pos % Carousel.Republican.length]);
        }

        Button candidateButtonIcon = (Button) l.findViewById(R.id.content);
        // final Drawable faceDrawable = getDrawableFromMemCache(candidate);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if(/*faceDrawable != null*/true) {
                candidateButtonIcon.setBackground(findCandidateProfilePictureByName(candidate));
            } else {
                // Lollipop
                candidateButtonIcon.setBackground(getResources().getDrawable(R.drawable.cat, null));
//                DrawableWorkerTask dTask = new DrawableWorkerTask(candidateButtonIcon);
//                dTask.execute();

            }
        } else {
            candidateButtonIcon.setBackgroundDrawable(findCandidateProfilePictureByName(candidate));
        }

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.Root);
        root.setScaleBoth(scale);


        // This onClickListener is overwritten each time.
        // The only element it grabs is ROOT
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView childCandidateLabel = (TextView) ((ViewGroup)v).getChildAt(0);
                String found_candidate = (String) childCandidateLabel.getText();
                Intent biosIntent = new Intent(getActivity(), Bios.class);
                biosIntent.putExtra("candidatePosition", pos);
                if(isRepublican)
                    biosIntent.putExtra("candidateName", current_front_republican);
                else
                    biosIntent.putExtra("candidateName", current_front_democrat);
                biosIntent.putExtra("UN", username);
                biosIntent.putExtra("PosterID", PosterID);
                biosIntent.putExtra("isRepublican", isRepublican);
                startActivity(biosIntent);
            }
        });

        Button candidateButton = (Button) l.findViewById(R.id.content);
        candidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent biosIntent = new Intent(getActivity(), Bios.class);
                biosIntent.putExtra("candidatePosition", pos);
                if(isRepublican)
                    biosIntent.putExtra("candidateName", current_front_republican);
                else
                    biosIntent.putExtra("candidateName", current_front_democrat);
                biosIntent.putExtra("UN", username);
                biosIntent.putExtra("PosterID", PosterID);
                biosIntent.putExtra("isRepublican", isRepublican);
                startActivity(biosIntent);
            }
        });


        return l;
    }

    public void addDrawableToMemoryCache(String key, Drawable tDrawable) {
        if(getDrawableFromMemCache(key) == null) {
            mLLFacesCache.put(key, tDrawable);
        }
    }

    public Drawable getDrawableFromMemCache(String key) {
        return mLLFacesCache.get(key);
    }

    private Drawable findCandidateProfilePictureByName(String cname) {
        Drawable withdrawn;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch(cname) {
                // Democrats
                case "Clinton":
                    return getResources().getDrawable(R.drawable.clinton, null);
                case "Sanders":
                    return getResources().getDrawable(R.drawable.sanders, null);
                case "Biden":
                    return getResources().getDrawable(R.drawable.biden, null);
                case "Webb":
                    return getResources().getDrawable(R.drawable.webb, null);
                case "O'Malley":
                    withdrawn = getResources().getDrawable(R.drawable.omalley, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Chafee":
                    return getResources().getDrawable(R.drawable.chafee, null);

                // Republicans
                case "Trump":
                    return getResources().getDrawable(R.drawable.trump, null);
                case "Carson":
                    withdrawn = getResources().getDrawable(R.drawable.carson, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return getResources().getDrawable(R.drawable.carson, null);
                case "Fiorina":
                    withdrawn = getResources().getDrawable(R.drawable.fiorina, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Rubio":
                    withdrawn = getResources().getDrawable(R.drawable.rubio, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return getResources().getDrawable(R.drawable.rubio, null);
                case "Bush":
                    withdrawn = getResources().getDrawable(R.drawable.bush, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return getResources().getDrawable(R.drawable.bush, null);
                case "Cruz":
                    return getResources().getDrawable(R.drawable.cruz, null);
                case "Kasich":
                    return getResources().getDrawable(R.drawable.kasich, null);
                case "Huckabee":
                    withdrawn = getResources().getDrawable(R.drawable.huckabee, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Christie":
                    withdrawn = getResources().getDrawable(R.drawable.christie, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return getResources().getDrawable(R.drawable.christie, null);
                case "Paul":
                    withdrawn = getResources().getDrawable(R.drawable.paul, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Jindal":
                    withdrawn = getResources().getDrawable(R.drawable.jindal, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Santorum":
                    withdrawn = getResources().getDrawable(R.drawable.santorum, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Graham":
                    withdrawn = getResources().getDrawable(R.drawable.graham, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Pataki":
                    withdrawn = getResources().getDrawable(R.drawable.pataki, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Walker":
                    withdrawn = getResources().getDrawable(R.drawable.walker, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Perry":
                    withdrawn = getResources().getDrawable(R.drawable.perry, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                case "Gilmore":
                    withdrawn = getResources().getDrawable(R.drawable.gilmore, null);
                    withdrawn.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    return withdrawn;
                // Cannot find; return cat
                default:
                    return getResources().getDrawable(R.drawable.cat, null);
            }
        } else {
            // TODO: ADD MORE THAN CATS
            return getResources().getDrawable(R.drawable.cat);
        }
    }

//    private int findCandidateResourceIDByName(String cname) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            switch(cname) {
//                // Democrats
//                case "Clinton":
//                    return R.drawable.clinton;
//                case "Sanders":
//                    return R.drawable.sanders;
//                case "Biden":
//                    return R.drawable.biden;
//                case "Webb":
//                    return R.drawable.webb;
//                case "O'Malley":
//                    return R.drawable.omalley;
//                case "Chafee":
//                    return R.drawable.chafee;
//
//                // Republicans
//                case "Trump":
//                    return R.drawable.trump;
//                case "Carson":
//                    return R.drawable.carson;
//                case "Fiorina":
//                    return R.drawable.fiorina;
//                case "Rubio":
//                    return R.drawable.rubio;
//                case "Bush":
//                    return R.drawable.bush;
//                case "Cruz":
//                    return R.drawable.cruz;
//                case "Kasich":
//                    return R.drawable.kasich;
//                case "Huckabee":
//                    return R.drawable.huckabee;
//                case "Christie":
//                    return R.drawable.christie;
//                case "Paul":
//                    return R.drawable.paul;
//                case "Jindal":
//                    return R.drawable.jindal;
//                case "Santorum":
//                    return R.drawable.santorum;
//                case "Graham":
//                    return R.drawable.graham;
//                case "Pataki":
//                    return R.drawable.pataki;
//                case "Walker":
//                    return R.drawable.walker;
//                case "Perry":
//                    return R.drawable.perry;
//
//                // Cannot find; return cat
//                default:
//                    return R.drawable.cat;
//            }
//        } else {
//            // TODO: ADD MORE THAN CATS
//
//            return R.drawable.cat;
//        }
//    }
//
//    /*
//    * The following two methods resize the jpgs
//    * and make bitmap loading more efficient
//    */
//    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
//        // Decode with inJustDecodeBoudns=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate in SampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight
//    ) {
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if(height > reqHeight || width> reqWidth) {
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Find largest inSampleSize value that is a power of 2 and keeps height and width larger than requested height and width
//            while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *=2;
//            }
//        }
//        return inSampleSize;
//    }
//
//
}