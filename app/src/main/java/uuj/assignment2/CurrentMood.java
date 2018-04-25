package uuj.assignment2;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class CurrentMood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiding the action bar to make the app appear full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_current_mood);

        ImageView image = findViewById(R.id.ivGif);

        //String gifs[] = ["", ""];

        Resources res = getResources();
        String[] gif = res.getStringArray(R.array.gifs);

        Random r = new Random();

        int choice = r.nextInt(gif.length);

        Glide
                .with(getApplicationContext())
                .load(gif[choice])
                .into(image);
    }
}
