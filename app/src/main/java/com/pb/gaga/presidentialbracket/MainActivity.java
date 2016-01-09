package com.pb.gaga.presidentialbracket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AsyncTaskCompleteListener<String> {

    private TextView status;
    private EditText usernameField, passwordField;
    private EditText Handle;
    public static String username;
    public static int PosterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateListView();
        registerClickCallback();

        Handle = (EditText)findViewById(R.id.UserName);

        SharedPreferences prefs = getSharedPreferences("UN", MODE_PRIVATE);
        String restoredText = prefs.getString("UN", null);
        if(restoredText != null) {
            // Fill in Static Variable
            username = restoredText;
            // When Task completes, PosterID will be updated
            launchTask(restoredText);
            // An existing name
            Intent intent1 = new Intent(this, nextActivity.class);
            intent1.putExtra("UN", restoredText);
            this.startActivity(intent1);
        } else {
            // New User!
        }

    }

    public void onTaskComplete(int passedPID) {
        PosterID = passedPID;
    }

    public void launchTask(String username) {
//        findPosterIDGivenUN a = new findPosterIDGivenUN(this);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
//            a.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);
//        } else {
//            a.execute(username);
//        }
        // Calls findPosterIDGivenUN
    }

    public void loginPost(View view){
//        String username = usernameField.getText().toString();
//        String password = passwordField.getText().toString();
//        new SignInActivity(this, status).execute(username, password);
        String UN = Handle.getText().toString();
        saveUNSharedPrefs(UN);

        addUser(UN);
        username = UN;
        Intent GO = new Intent(this, nextActivity.class);
        GO.putExtra("UN", UN);
        this.startActivity(GO);
    }

    private void populateListView() {
        // Create list of items
        String[] menuItems = { "R-Polls",
                               "D-Polls",
                               "R-Bracket",
                               "D-Bracket",
                               "R-Biographies",
                               "D-Biographies",
                               "Maps",
                             };

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.main_items, menuItems);

        // Configure list view
        ListView list = (ListView) findViewById(R.id.homeListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.homeListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked #" + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();


                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(MainActivity.this, nextActivity.class);
                        intent0.putExtra("R-Polls", "IntentKey");
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, nextActivity.class);
                        intent1.putExtra("D-Polls", "IntentKey");
                        startActivity(intent1);
                        break;
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void itemTapped(View view) {
        Intent intent = new Intent(this, nextActivity.class);
        startActivity(intent);
    }

    public void saveUNSharedPrefs(String UN) {
        // First time; no previous UN
        SharedPreferences.Editor editor = getSharedPreferences("UN", MODE_PRIVATE).edit();
        editor.putString("UN", UN);
        editor.apply();
    }

    public void savePosterIDSharedPrefs(int ID) {
        SharedPreferences.Editor editor = getSharedPreferences("PosterID", MODE_PRIVATE).edit();
        editor.putInt("PosterID", ID);
        editor.apply();
    }

    public void addUser(String username) {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            new addUser().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);
        } else {
            new addUser().execute(username);
        }
    }

}
