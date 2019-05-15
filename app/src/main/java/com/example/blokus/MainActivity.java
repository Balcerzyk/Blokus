package com.example.blokus;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setButtonsClickListener();
    }

    public void setButtonsClickListener(){
        final Button playButton = (Button) findViewById(R.id.playButton);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.playButton:
                        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(gameIntent);
                        break;
                    case R.id.exitButton:
                        finish();
                        System.exit(0);
                        break;
                    default:;
                }
            }
        };

        playButton.setOnClickListener(myClickListener);
        exitButton.setOnClickListener(myClickListener);
    }
}
