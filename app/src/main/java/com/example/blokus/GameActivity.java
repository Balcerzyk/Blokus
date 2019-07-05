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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    BigField bigField = new BigField();
    String[] playerColors = {"red", "blue", "yellow", "green"};
    int playerId = 0;
    String currPlayer = "red";
    int currBlockId = 0;
    Field currentField;


    DisplayMetrics displayMetrics = new DisplayMetrics();


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
                if (currentField != null && currentField.isEmpty) {
                    ImageView block = findViewById(R.id.block0);
                    setEmpty(currentField, currPlayer, currBlockId);


                    playerId++;
                    if (playerId == 4) playerId = 0;
                    currPlayer = playerColors[playerId];

                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    block.getLayoutParams().width = (int) (30 * displayMetrics.density);
                    block.getLayoutParams().height = (int) (30 * displayMetrics.density);
                    block.setImageResource(getResources().getIdentifier(currPlayer + "1", "drawable", getPackageName()));
                    currBlockId = 0;


                }
            }
        });

        View game = findViewById(R.id.game_layout);
        game.setOnTouchListener(this);

    }

    public void setActiveBlock(View view) {
        switch(view.getId()) {
            case R.id.leftButton:
                if(currBlockId != 0) currBlockId--;
                break;
            case R.id.rightButton:
                if(currBlockId != 7) currBlockId++;
                break;
            default:;
        }

        ImageView block = findViewById(R.id.block0);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        block.setX(width/2);
        block.setY(findViewById(R.id.rightButton).getY());
        block.animate()
                .x(block.getX())
                .y(block.getY())
                .setDuration(0)
                .start();
        String player = currPlayer;
        switch(currBlockId){
            case 0: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "1", "drawable", getPackageName())); currBlockId = 0; break;
            case 1: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "2", "drawable", getPackageName())); currBlockId = 1; break;
            case 2: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "3", "drawable", getPackageName())); currBlockId = 2; break;
            case 3: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(90 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "4", "drawable", getPackageName())); currBlockId = 3; break;
            case 4: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "5", "drawable", getPackageName())); currBlockId = 4; break;
            case 5: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(120 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "6", "drawable", getPackageName())); currBlockId = 5; break;
            case 6: block.getLayoutParams().width = (int)(90 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "7", "drawable", getPackageName())); currBlockId = 6; break;
            case 7: block.getLayoutParams().width = (int)(90 * displayMetrics.density); block.getLayoutParams().height = (int)(90 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "8", "drawable", getPackageName())); currBlockId = 7; break;

        }
    }

    public void setEmpty(Field field, String playerColor, int currentBlockId){
        bigField.setEmpty(field);
        ArrayList neighbourX = new ArrayList();
        ArrayList neighbourY = new ArrayList();
        
        switch(currentBlockId){
            case 0: neighbourX.add(0); neighbourY.add(0); break;
            case 1: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); break;
            case 2: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(1); neighbourY.add(0); break;
            case 3: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(1); neighbourY.add(0); neighbourX.add(1); neighbourY.add(-1); break;
            case 4: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(1); neighbourY.add(0); neighbourX.add(1); neighbourY.add(1); break;
            case 5: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(0); neighbourY.add(2); neighbourX.add(0); neighbourY.add(3); break;
            case 6: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(2); neighbourY.add(0); neighbourX.add(1); neighbourY.add(1); neighbourX.add(2); neighbourY.add(1);  break;
            case 7: neighbourX.add(0); neighbourY.add(0); neighbourX.add(0); neighbourY.add(1); neighbourX.add(1); neighbourY.add(1); neighbourX.add(1); neighbourY.add(2); neighbourX.add(2); neighbourY.add(2); break;
        }
        for(int i=0; i<neighbourX.size(); i++) {
            bigField.setEmpty(field.getPositionX() + (int) neighbourX.get(i), field.getPositionY() + (int) neighbourY.get(i));
            String paint = "imageView" + (field.getPositionX() + (int) neighbourX.get(i)) + (field.getPositionY() + (int) neighbourY.get(i));
            int imageViewId = getResources().getIdentifier(paint, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(getResources().getIdentifier(playerColor + "1", "drawable", getPackageName()));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        ImageView activeBlockImg = findViewById(R.id.block0);


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
