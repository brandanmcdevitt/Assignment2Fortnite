package uuj.assignment2;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseLongArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //View README.md to get an API Key

    //I used http://www.jsonschema2pojo.org to create POJO for the GSON

    //creating the TAG for logging
    String TAG = "TESTCHECK";

    //these strings will be used for storing the selected platform and username and passed
    //into the next activity
    String platform;
    String username;

    //data storage strings
    static String lastSearch;
    //int for the background choice
    int bgChoice;

    //getting UI elements
    Spinner spPlatform;
    //EditText tbUsername;
    Button btnSubmit;
    AutoCompleteTextView tbUsername;
    ImageButton btnSettings;

    //setting up the layout so that we can set the background
    ConstraintLayout layout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiding the action bar to make the app appear full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //connecting the UI elements by their id
        spPlatform = findViewById(R.id.spinner);
        tbUsername = findViewById(R.id.tbUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSettings = findViewById(R.id.btnSettings);
        //autoTextView = findViewById(R.id.tbUsername);

        //using shared preferences for retrieving data regarding the username and bg colour
        SharedPreferences prefs = this.getSharedPreferences("usernamePrefs", Context.MODE_PRIVATE);
        lastSearch = prefs.getString("username", "");
        bgChoice = prefs.getInt("bg", 0);
        SharedPreferences.Editor editor = prefs.edit();

        //linking the layout to the UI element
        layout = findViewById(R.id.layoutMain);

        //if statement to determine the background colour
        if(bgChoice == 1) {
            //if the bgChoice int that has been returned is equal to 1 then set the background to
            //homev2
            layout.setBackgroundResource(R.drawable.homev2);
        } else if(bgChoice == 2) {
            //if the bgChoice int that has been returned is equal to 2 then set the background to
            //homev3
            layout.setBackgroundResource(R.drawable.homev3);
        } else if(bgChoice == 3) {
            //if the bgChoice int that has been returned is equal to 3 then set the background to
            //homev4
            layout.setBackgroundResource(R.drawable.homev4);
        }

        //using an array adapter for the previosuly searched usernames to display in the
        //autocompletetextbox
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Collections.singletonList(lastSearch));
        tbUsername.setAdapter(adapter);

        //when the submit button is clicked it will run this block of code
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //getting the text from the spinner and textbox
               platform = spPlatform.getSelectedItem().toString();
               username = tbUsername.getText().toString();

               //changing the string value that will be appended to the HttpRequest url in the
               //next activity
                switch (platform) {
                    case "Xbox":
                        platform = "xbl";
                        break;
                    case "PC":
                        platform = "pc";
                        break;
                    case "PlayStation":
                        platform = "psn";
                        break;
                }

               //if the username is not empty run this block of code
               if(!username.equals("")) {

                   //creating a new intent
                   Intent screenChange = new Intent(getApplicationContext(), StatScreen.class);
                   //creating a bundle to store the variables that will be passed into the next
                   //activity
                   Bundle bndleStorage = new Bundle();
                   bndleStorage.putString("platform", platform);
                   bndleStorage.putString("username", username);
                   bndleStorage.putInt("bg", bgChoice);
                   screenChange.putExtras(bndleStorage);
                   //starting our activity for a result
                   startActivityForResult(screenChange, 1);

                 //if the username is empty, display a toast message to the user
               } else {
                   Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
               }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "settings button clicked", Toast.LENGTH_SHORT).show();

                //creating a new intent
                Intent screenChange = new Intent(getApplicationContext(), settings.class);
                //creating a bundle to store the variables that will be passed into the next
                //activity
                //starting our activity for a result
                startActivity(screenChange);
            }
        });

    }

    //setting the code that will run once the application has returned to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Checking which request we're responding to
        if (requestCode == 1) {
            //Making sure the request was successful
            if (resultCode == RESULT_OK) {

                //display a toast message if the username could not be resolved on the server
                Toast.makeText(getApplicationContext(), "Username Not Found On Server", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private static final String[] previousSearch = new String[] {
        lastSearch
    };

    //invoking the onStart callback with logging for testing purposes
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "Invoked onStart()");

        //checking the background colour returned from the settings screen.
        //invoking this code within the onStart method so that when we finish() the settings activity
        //it will load the new colour on the home activity
        SharedPreferences prefs = this.getSharedPreferences("usernamePrefs", Context.MODE_PRIVATE);
        bgChoice = prefs.getInt("bg", 0);
        SharedPreferences.Editor editor = prefs.edit();

        layout = findViewById(R.id.layoutMain);

        if(bgChoice == 1) {
            //if the bgChoice int that has been returned is equal to 1 then set the background to
            //homev2
            layout.setBackgroundResource(R.drawable.homev2);
        } else if(bgChoice == 2) {
            //if the bgChoice int that has been returned is equal to 2 then set the background to
            //homev3
            layout.setBackgroundResource(R.drawable.homev3);
        } else if(bgChoice == 3) {
            //if the bgChoice int that has been returned is equal to 3 then set the background to
            //homev4
            layout.setBackgroundResource(R.drawable.homev4);
        }

    }

    //invoking the onResume callback with logging for testing purposes
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "Invoked onResume()");

    }

    //invoking the onPause callback with logging for testing purposes
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "Invoked onPause()");

    }

    //invoking the onStop callback with logging for testing purposes
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "Invoked onStop()");

    }

    //invoking the onRestart callback with logging for testing purposes
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "Invoked onRestart()");

    }

    //invoking the onDestroy callback with logging for testing purposes
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Invoked onDestroy()");

    }
}
