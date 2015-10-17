package com.example.gaga.presidentialbracket;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class Gesture extends nextActivity implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {


    ImageSwitcher Rswitcher, Dswitcher;
    ArrayList<Drawable> allImages = new ArrayList<Drawable>();
    ImageView Rfaces, Dfaces;
    float initialX;
    private Cursor cursor;
    private int columnIndex, position = 0;
    private static final String DEBUG_TAG = "Gestures";


    // This example shows an Activity, but you would use the same approach if
    // you were subclassing a View.

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Extract action from event through "getActionMasked"
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case(MotionEvent.ACTION_DOWN):
                initialX = event.getX();
                return true;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                if(initialX > finalX) {
                    cursor.moveToPosition(position);
                    int imageID = cursor.getInt(columnIndex);
//                    Rfaces.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));
//                    Dfaces.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));
//                    Rfaces.setImageResource(R.drawable.rabbits);
//                    Dfaces.setImageResource(R.drawable.rabbits);
                }
                GestureListener cat = new GestureListener();
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
//        // New gesture detector with context and OnGestureListener
//        mDetector = new GestureDetectorCompat(this, this);
//        // Set gesture detector as double tap listener
//        mDetector.setOnDoubleTapListener(this);

        getImages(); // Load the images into ArrayList allImages

        Rswitcher = (ImageSwitcher) findViewById(R.id.peek);
        Dswitcher = (ImageSwitcher) findViewById(R.id.democratSwitcher);
        Rfaces = (ImageView) findViewById(R.id.republicanFaces);
        Rfaces.setImageResource(R.drawable.rabbits);
        Dfaces = (ImageView) findViewById(R.id.democratFaces);
        Dfaces.setImageResource(R.drawable.rabbits);

        // START ANIMATION
        Rswitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        Dswitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        Rswitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        Dswitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        // END ANIMATION

    }




    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {

    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    private void getImages() {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            allImages.add(this.getDrawable(R.drawable.meerkat));
//            allImages.add(this.getDrawable(R.drawable.rabbits));
        } else {
//            allImages.add(this.getResources().getDrawable(R.drawable.meerkat));
//            allImages.add(this.getResources().getDrawable(R.drawable.rabbits));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
        try {
//            Rswitcher.setImageDrawable(allImages.get(position));
//            Dswitcher.setImageDrawable(allImages.get(position));
        } catch(Exception e) {
            System.out.println(e);
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF660000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
        return i;
    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return allImages.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView galleryview = new ImageView(mContext);
            galleryview.setImageDrawable(allImages.get(position));
            galleryview.setAdjustViewBounds(true);
            galleryview.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT, ImageSwitcher.LayoutParams.WRAP_CONTENT));
            galleryview.setPadding(5, 0, 5, 0);
            galleryview.setBackgroundResource(android.R.drawable.picture_frame);
            return galleryview;
        }
    }
}