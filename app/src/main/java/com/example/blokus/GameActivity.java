package com.example.blokus;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    BigField bigField = new BigField();
    int activeBlockInt = 0;
    String playername = "red";

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

            }
        });

            View game = findViewById(R.id.game_layout);
            game.setOnTouchListener(this);

        //Player redPlayer = new Player("red");

    }

    public void setActiveBlock(View view) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        switch(view.getId()) {
            case R.id.leftButton:
                if(activeBlockInt != 0) activeBlockInt--;
                break;
            case R.id.rightButton:
                if(activeBlockInt != 7) activeBlockInt++;
                break;
            default:;
        }

        ImageView block = findViewById(R.id.block0);
        switch(activeBlockInt){
            case 0: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(R.drawable.red1); break;
            case 1: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(R.drawable.red2); break;
            case 2: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(R.drawable.red3); break;
            case 3: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(R.drawable.red1); break;
            case 4: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(R.drawable.red2); break;
            case 5: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(R.drawable.red3); break;
            case 6: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(R.drawable.red1); break;
            case 7: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(R.drawable.red2); break;

        }




    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        Log.v("222222", Integer.toString(v.getId()));
        //if(activeBlock == "") return false;

        //int id = getResources().getIdentifier(activeBlock, "id", getPackageName());
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        ImageView activeBlockImg = findViewById(R.id.block0);
        Field currentField;

        float clickX = event.getX();
        float clickY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                activeBlockImg.animate()
                        .x(clickX - (activeBlockImg.getWidth() / 2))
                        .y(clickY - (activeBlockImg.getHeight() / 2))
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                currentField = bigField.searchField(clickX, clickY);
                if (currentField != null) {
                    activeBlockImg.setX(currentField.getTopLeftX() + (currentField.getWidth() / 2) * displayMetrics.density );
                    activeBlockImg.setY(currentField.getTopLeftY() + (currentField.getHeight() / 2) * displayMetrics.density-60);
                    activeBlockImg.animate()
                            .x(activeBlockImg.getX())
                            .y(activeBlockImg.getY())
                            .setDuration(0)
                            .start();
                }
                else{

                }

                break;
            default:
                return false;
        }
        return true;
    }
}
