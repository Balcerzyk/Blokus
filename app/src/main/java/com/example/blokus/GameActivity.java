package com.example.blokus;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    BigField bigField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bigField = new BigField();

        View myView = findViewById(R.id.game_playGround);
        myView.setOnTouchListener(touchListener);


    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();

                TextView lel = findViewById(R.id.textView);
                TextView lel2 = findViewById(R.id.textView3);

                lel2.setText(x+"   "+y);
                Field currentField = bigField.searchField(x, y);
                if(currentField != null){
                    String currentFieldId = "imageView" + currentField.getPositionX() + currentField.getPositionY();
                    lel.setText(currentFieldId);
                    ImageView currentFieldImageView = findViewById(getResources().getIdentifier(currentFieldId, "id", getPackageName()));
                    currentFieldImageView.setImageResource(R.drawable.ic_launcher_background);
                }
                else lel.setText("blad");

            }

            return false;
        }

    };


}
