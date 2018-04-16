package uuj.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import uuj.assignment2.data_to_fetch.MyResponse;

public class StatScreen extends AppCompatActivity {

    //creating the TAG for logging
    String TAG = "TESTCHECK";
    //these strings will be used to connect to the server and pull information in JSON format.
    //We will be using GSON to parse the returned JSON.
    String platform;
    String username;

    //the header, key and url will be used for the connection
    String header;
    String key;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiding the action bar to make the app appear fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stat_screen);

        //condtional statement to determine whether the information was correctly passed from the
        //previous activity
        if(getIntent().getExtras() != null) {
            platform = getIntent().getExtras().getString("platform");
            username = getIntent().getExtras().getString("username");
        }

        //using shared preferences for our persistant data storage
        SharedPreferences prefs = this.getSharedPreferences("usernamePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.commit();

        //the header will be used in conjuction with the API KEY to allow access to the server
        //(view README.md if you have not got an API Key)
        header = "TRN-Api-Key";
        key = getApplicationContext().getString(R.string.API_KEY);

        //the url that we will be connecting to
        url = "https://api.fortnitetracker.com/v1/profile/" + platform + "/" + username +"";

        //getting the UI elements
        final TextView tvPlatform = findViewById(R.id.tvPlatform);
        final TextView tvUsername = findViewById(R.id.tvUsername);

        final TextView tvSoloKills = findViewById(R.id.tvSoloKills);
        final TextView tvSoloWins = findViewById(R.id.tvSoloWins);
        final TextView tvSoloKpm = findViewById(R.id.tvSoloKpm);
        final TextView tvSoloWinPerc = findViewById(R.id.tvSoloWinPerc);
        final TextView tvSoloKd = findViewById(R.id.tvSoloKd);
        final TextView tvSoloMatches = findViewById(R.id.tvSoloMatches);

        final TextView tvDuoKills = findViewById(R.id.tvDuoKills);
        final TextView tvDuoWins = findViewById(R.id.tvDuoWins);
        final TextView tvDuoKpm = findViewById(R.id.tvDuoKpm);
        final TextView tvDuoWinPerc = findViewById(R.id.tvDuoWinPerc);
        final TextView tvDuoKd = findViewById(R.id.tvDuoKd);
        final TextView tvDuoMatches = findViewById(R.id.tvDuoMatches);

        final TextView tvSquadKills = findViewById(R.id.tvSquadKills);
        final TextView tvSquadWins = findViewById(R.id.tvSquadWins);
        final TextView tvSquadKpm = findViewById(R.id.tvSquadKpm);
        final TextView tvSquadWinPerc = findViewById(R.id.tvSquadWinPerc);
        final TextView tvSquadKd = findViewById(R.id.tvSquadKd);
        final TextView tvSquadMatches = findViewById(R.id.tvSquadMatches);

        final TextView tvOverallKills = findViewById(R.id.tvOverallKills);
        final TextView tvOverallWins = findViewById(R.id.tvOverallWins);
        final TextView tvOverallKpm = findViewById(R.id.tvOverallKpm);
        final TextView tvOverallWinPerc = findViewById(R.id.tvOverallWinPerc);
        final TextView tvOverallKd = findViewById(R.id.tvOverallKd);
        final TextView tvOverallMatches = findViewById(R.id.tvOverallMatches);

        //opening an async task
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //creating the URL
                URL trackingEndPoint = null;
                try {
                    trackingEndPoint = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                //creating a connection
                HttpsURLConnection myConnection = null;
                try {
                    myConnection = (HttpsURLConnection) trackingEndPoint.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //setting the header request information (API Key)
                myConnection.setRequestProperty(header, key);

                //checking the connection
                try {
                    if (myConnection.getResponseCode() == 200) {
                        //this is where we will be getting our JSON from the request
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");

                        //here we will be getting the JSON and adding it to a string so that
                        //we can use GSON to parse it
                        BufferedReader bReader = new BufferedReader(responseBodyReader);
                        String read;
                        String entireResponse;
                        StringBuffer sBuffer = new StringBuffer();
                        while((read = bReader.readLine()) != null) {
                            sBuffer.append(read);
                        }
                        entireResponse = sBuffer.toString();

                        //if the entireResponse string contains this value, it means that it
                        //worked and the JSON was correctly fetched
                        if(entireResponse.contains("accountId")) {

                            //this is where we will be able to pull all of our information from
                            MyResponse response = new Gson().fromJson(entireResponse, MyResponse.class);

                            //setting up variables with the specific info we need and want to display
                            final String soloKills = response.getStats().getP2().getKills().getDisplayValue();
                            final String soloWins = response.getStats().getP2().getTop1().getDisplayValue();
                            final String soloKpm = response.getStats().getP2().getKpg().getDisplayValue();
                            final String soloWinPerc = response.getStats().getP2().getWinRatio().getDisplayValue();
                            final String soloKd = response.getStats().getP2().getKd().getDisplayValue();
                            final String soloMatches = response.getStats().getP2().getMatches().getDisplayValue();

                            final String duoKills = response.getStats().getP10().getKills().getDisplayValue();
                            final String duoWins = response.getStats().getP10().getTop1().getDisplayValue();
                            final String duoKpm = response.getStats().getP10().getKpg().getDisplayValue();
                            final String duoWinPerc = response.getStats().getP10().getWinRatio().getDisplayValue();
                            final String duoKd = response.getStats().getP10().getKd().getDisplayValue();
                            final String duoMatches = response.getStats().getP10().getMatches().getDisplayValue();

                            final String squadKills = response.getStats().getP9().getKills().getDisplayValue();
                            final String squadWins = response.getStats().getP9().getTop1().getDisplayValue();
                            final String squadKpm = response.getStats().getP9().getKpg().getDisplayValue();
                            final String squadWinPerc = response.getStats().getP9().getWinRatio().getDisplayValue();
                            final String squadKd = response.getStats().getP9().getKd().getDisplayValue();
                            final String squadMatches = response.getStats().getP9().getMatches().getDisplayValue();

                            //for the overall numbers we will just be adding the 3 groups together to get an overall score.
                            //we could do this by just grabbing the info from .getLifetimeStats() but we wanted to
                            //use different methods for getting information in this application.
                            final int overallKills = Integer.parseInt(response.getStats().getP2().getKills().getValue())
                                    +Integer.parseInt(response.getStats().getP10().getKills().getValue())
                                    +Integer.parseInt(response.getStats().getP9().getKills().getValue());
                            final int overallWins = Integer.parseInt(response.getStats().getP2().getTop1().getValue())
                                    +Integer.parseInt(response.getStats().getP10().getTop1().getValue())
                                    +Integer.parseInt(response.getStats().getP9().getTop1().getValue());
                            final double overallKpmToBeFixed = Double.parseDouble(response.getStats().getP2().getKpg().getValue())
                                    +Double.parseDouble(response.getStats().getP10().getKpg().getValue())
                                    +Double.parseDouble(response.getStats().getP9().getKpg().getValue());
                            final double overallWinPerc = Double.parseDouble(response.getStats().getP2().getWinRatio().getValue())
                                    +Double.parseDouble(response.getStats().getP10().getWinRatio().getValue())
                                    +Double.parseDouble(response.getStats().getP9().getWinRatio().getValue());
                            final double overallKdToBeFixed = Double.parseDouble(response.getStats().getP2().getKd().getValue())
                                    +Double.parseDouble(response.getStats().getP10().getKd().getValue())
                                    +Double.parseDouble(response.getStats().getP9().getKd().getValue());
                            final int overallMatches = Integer.parseInt(response.getStats().getP2().getMatches().getValue())
                                    +Integer.parseInt(response.getStats().getP10().getMatches().getValue())
                                    +Integer.parseInt(response.getStats().getP9().getMatches().getValue());

                            //bringing the overallKd value to 2 decimal places
                            final String overallKd = String.format("%.2f", overallKdToBeFixed);
                            final String overallKpm = String.format("%.2f", overallKpmToBeFixed);

                            //we have to use runOnUiThread to be able to populate and alter our
                            //UI elements from within the AsyncTask
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //populating our UI elements
                                    tvPlatform.setText("Platform: " + platform);
                                    tvUsername.setText("Username: " + username);

                                    tvSoloKills.setText("Kills: " + soloKills);
                                    tvSoloWins.setText("Wins: " + soloWins);
                                    tvSoloKpm.setText("KPG: " + soloKpm);
                                    tvSoloWinPerc.setText("Win %: " + soloWinPerc + "%");
                                    tvSoloKd.setText("K/D: " + soloKd);
                                    tvSoloMatches.setText("Matches: " + soloMatches);

                                    tvDuoKills.setText("Kills: " + duoKills);
                                    tvDuoWins.setText("Wins: " + duoWins);
                                    tvDuoKpm.setText("KPG: " + duoKpm);
                                    tvDuoWinPerc.setText("Win %: " + duoWinPerc + "%");
                                    tvDuoKd.setText("K/D: " + duoKd);
                                    tvDuoMatches.setText("Matches: " + duoMatches);

                                    tvSquadKills.setText("Kills: " + squadKills);
                                    tvSquadWins.setText("Wins: " + squadWins);
                                    tvSquadKpm.setText("KPG: " + squadKpm);
                                    tvSquadWinPerc.setText("Win %: " + squadWinPerc + "%");
                                    tvSquadKd.setText("K/D: " + squadKd);
                                    tvSquadMatches.setText("Matches: " + squadMatches);

                                    tvOverallKills.setText("Kills: " + addFormatting(overallKills));
                                    tvOverallWins.setText("Wins: " + addFormatting(overallWins));
                                    tvOverallKpm.setText("KPG: " + overallKpm);
                                    tvOverallWinPerc.setText("Win %: " + overallWinPerc + "%");
                                    tvOverallKd.setText("K/D: " + overallKd);
                                    tvOverallMatches.setText("Matches: " + addFormatting(overallMatches));
                                }
                            });
                            //else if the entireResponse string did not contain the value, run this block
                        } else {
                            //close the connection
                            myConnection.disconnect();
                            //returning to the previous activity with our result
                            Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }

                    }

                    //closing the connection
                    myConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


            }

            //this function allows us to add a comma to large numbers (10000 becomes 10,000)
    private String addFormatting(int amount){
        return NumberFormat.getNumberInstance(Locale.UK).format(amount);
    }

    //invoking the onStart callback with logging for testing purposes
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "Invoked onStart()");

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
