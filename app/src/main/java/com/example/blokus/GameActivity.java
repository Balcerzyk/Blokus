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

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    BigField bigField = new BigField();
    int activeBlockInt = 0;
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
                if (currentField != null) {
                    ImageView block = findViewById(R.id.block0);
                    setEmpty(currentField, currPlayer, currBlockId);

                    playerId++;
                    if (playerId == 4) playerId = 0;
                    currPlayer = playerColors[playerId];

                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    block.getLayoutParams().width = (int) (30 * displayMetrics.density);
                    block.getLayoutParams().height = (int) (30 * displayMetrics.density);
                    block.setImageResource(getResources().getIdentifier(currPlayer + "1", "drawable", getPackageName()));
                }
            }
        });

        View game = findViewById(R.id.game_layout);
        game.setOnTouchListener(this);

    }

    public void setActiveBlock(View view) {
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
        switch(activeBlockInt){
            case 0: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "1", "drawable", getPackageName())); currBlockId = 0; break;
            case 1: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "2", "drawable", getPackageName())); currBlockId = 1; break;
            case 2: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "3", "drawable", getPackageName())); currBlockId = 2; break;
            case 3: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "1", "drawable", getPackageName())); currBlockId = 3; break;
            case 4: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "2", "drawable", getPackageName())); currBlockId = 4; break;
            case 5: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "3", "drawable", getPackageName())); currBlockId = 5; break;
            case 6: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "1", "drawable", getPackageName())); currBlockId = 6; break;
            case 7: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; block.setImageResource(getResources().getIdentifier(player + "2", "drawable", getPackageName())); currBlockId = 7; break;

        }
    }

    public void setEmpty(Field field, String playerColor, int currentBlockId){
        bigField.setEmpty(field);
        String paint = "imageView" + field.getPositionX() + field.getPositionY();
        int imageViewId = getResources().getIdentifier(paint, "id", getPackageName());
        ImageView imageView = findViewById(imageViewId);
        imageView.setImageResource(getResources().getIdentifier(playerColor + "1", "drawable", getPackageName()));
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
