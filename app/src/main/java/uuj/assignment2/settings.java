package uuj.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class settings extends AppCompatActivity {

    int bgChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiding the action bar to make the app appear full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        //setting up buttons for the settings and backgrounds
        Button btnMood = findViewById(R.id.btnMood);
        Button btnReturn = findViewById(R.id.btnReturn);
        ImageButton bgOne = findViewById(R.id.ibOne);
        ImageButton bgTwo = findViewById(R.id.ibTwo);
        ImageButton bgThree = findViewById(R.id.ibThree);

        //setting the layout equal to the the id of the constraint layout
        final ConstraintLayout layout = findViewById(R.id.layoutBg);

        //data storage to retrieve the background choice
        SharedPreferences prefs = this.getSharedPreferences("usernamePrefs", Context.MODE_PRIVATE);
        bgChoice = prefs.getInt("bg", 0);
        final SharedPreferences.Editor editor = prefs.edit();

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

        bgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the background upon click and set the bgChoice = 1
                layout.setBackgroundResource(R.drawable.homev2);
                bgChoice = 1;
            }
        });

        bgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the background upon click and set the bgChoice = 2
                layout.setBackgroundResource(R.drawable.homev3);
                bgChoice = 2;
            }
        });

        bgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the background upon click and set the bgChoice = 3
                layout.setBackgroundResource(R.drawable.homev4);
                bgChoice = 3;
            }
        });


        btnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a new intent
                Intent screenChange = new Intent(getApplicationContext(), CurrentMood.class);
                //creating a bundle to store the variables that will be passed into the next
                //activity
                //starting our activity for a result
                startActivityForResult(screenChange, 1);

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //returning to the previous activity with our result
                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                editor.putInt("bg", bgChoice);
                editor.commit();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
