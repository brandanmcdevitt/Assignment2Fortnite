package uuj.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiding the action bar to make the app appear full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        Button btnMood = findViewById(R.id.btnMood);

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

    }
}
