package com.example.gaga.presidentialbracket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// This class extends and returns the support Fragment
public class MyFragment extends Fragment {

    // Takes Fragment, returns Fragment
    public static Fragment newInstance(Carousel context, int pos, float scale, String candidate, String username, int PosterID) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        b.putString("candidate", candidate);
        b.putString("UN", username);
        b.putInt("PosterID", PosterID);

        // MyPagerAdapter->MyFragment->Fragment
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String candidate = this.getArguments().getString("candidate");
        final String username = this.getArguments().getString("UN");
        final int PosterID = this.getArguments().getInt("PosterID");
        final int pos = this.getArguments().getInt("pos");
        float scale = this.getArguments().getFloat("scale");


        if (container == null) {
            return null;
        }

          LinearLayout l = (LinearLayout) inflater.inflate(R.layout.mf, container, false);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            l.setBackground(candidateProfilePicture);
//        } else {
//            l.setBackgroundDrawable(candidateProfilePicture);
//        }

        TextView tv = (TextView) l.findViewById(R.id.CandidateName);
        tv.setText(candidate.toUpperCase());

        Button candidateButtonIcon = (Button) l.findViewById(R.id.content);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            candidateButtonIcon.setBackground(findCandidateProfilePictureByName(candidate));
        } else {
            candidateButtonIcon.setBackgroundDrawable(findCandidateProfilePictureByName(candidate));
        }

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.Root);
        root.setScaleBoth(scale);


        MyLinearLayout listener = (MyLinearLayout) l.findViewById(R.id.Root);
        listener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent biosIntent = new Intent(getActivity(), Bios.class);
                biosIntent.putExtra("candidatePosition", pos);
                biosIntent.putExtra("candidateName", candidate);
                biosIntent.putExtra("UN", username);
                biosIntent.putExtra("PosterID", PosterID);
                startActivity(biosIntent);
            }
        });

        return l;
    }

    private Drawable findCandidateProfilePictureByName(String cname) {
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
                    return getResources().getDrawable(R.drawable.omalley, null);
                case "Chafee":
                    return getResources().getDrawable(R.drawable.chafee, null);

                // Republicans
                case "Trump":
                    return getResources().getDrawable(R.drawable.trump, null);
                case "Carson":
                    return getResources().getDrawable(R.drawable.carson, null);
                case "Fiorina":
                    return getResources().getDrawable(R.drawable.fiorina, null);
                case "Rubio":
                    return getResources().getDrawable(R.drawable.rubio, null);
                case "Bush":
                    return getResources().getDrawable(R.drawable.bush, null);
                case "Cruz":
                    return getResources().getDrawable(R.drawable.cruz, null);
                case "Kasich":
                    return getResources().getDrawable(R.drawable.kasich, null);
                case "Huckabee":
                    return getResources().getDrawable(R.drawable.huckabee, null);
                case "Christie":
                    return getResources().getDrawable(R.drawable.christie, null);
                case "Paul":
                    return getResources().getDrawable(R.drawable.paul, null);
                case "Jindal":
                    return getResources().getDrawable(R.drawable.jindal, null);
                case "Santorum":
                    return getResources().getDrawable(R.drawable.santorum, null);
                case "Graham":
                    return getResources().getDrawable(R.drawable.graham, null);
                case "Pataki":
                    return getResources().getDrawable(R.drawable.pataki, null);
                case "Walker":
                    return getResources().getDrawable(R.drawable.walker, null);
                case "Perry":
                    return getResources().getDrawable(R.drawable.perry, null);

                // Cannot find; return cat
                default:
                    return getResources().getDrawable(R.drawable.cat, null);
            }


        } else {
            // TODO: ADD MORE THAN CATS

            return getResources().getDrawable(R.drawable.cat);
        }
    }

}