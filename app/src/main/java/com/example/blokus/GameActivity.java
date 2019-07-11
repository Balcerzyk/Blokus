package com.example.blokus;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.view.Window;
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
    int rotation = 0;

    Player red = new Player("red");
    Player blue = new Player("blue");
    Player yellow = new Player("yellow");
    Player green = new Player("green");


    DisplayMetrics displayMetrics = new DisplayMetrics();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View game = findViewById(R.id.game_layout);
        game.setOnTouchListener(this);

    }

    public void setActiveBlock(View view) {
        switch(view.getId()) {
            case R.id.leftButton:
                if(currBlockId == 0) return;
                else{
                    switch (currPlayer){
                        case "red": changeLeft(red); break;
                        case "blue": changeLeft(blue); break;
                        case "yellow": changeLeft(yellow); break;
                        case "green": changeLeft(green); break;
                    }
                }
                break;
            case R.id.rightButton:
                if(currBlockId == 7) return;
                else{
                    switch (currPlayer){
                        case "red": changeRight(red); break;
                        case "blue": changeRight(blue); break;
                        case "yellow": changeRight(yellow); break;
                        case "green": changeRight(green); break;
                    }
                }
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

        setSize(currBlockId);
        block.setImageResource(getResources().getIdentifier(currPlayer + (currBlockId + 1), "drawable", getPackageName()));
    }

    public void changeLeft(Player player){
        if(player.blocks[currBlockId-1]) {
            currBlockId--;
            return;
        }
        else{
            int copy = currBlockId;
            while(currBlockId != 0 && !player.blocks[currBlockId - 1]) currBlockId--;
            if(currBlockId != 0)currBlockId--;
            else currBlockId = copy;
        }

    }

    public void changeRight(Player player){
        if(player.blocks[currBlockId+1]) {
            currBlockId++;
            return;
        }
        else{
            int copy = currBlockId;
            while(currBlockId != 7 && !player.blocks[currBlockId + 1]) currBlockId++;
            if(currBlockId != 7)currBlockId++;
            else currBlockId = copy;
        }
    }

    public void setSize(int id){
        ImageView block = findViewById(R.id.block0);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch(id){
            case 0: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(30 * displayMetrics.density) ; break;
            case 1: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; break;
            case 2: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; break;
            case 3: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(90 * displayMetrics.density) ; break;
            case 4: block.getLayoutParams().width = (int)(60 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; break;
            case 5: block.getLayoutParams().width = (int)(30 * displayMetrics.density); block.getLayoutParams().height = (int)(120 * displayMetrics.density); break;
            case 6: block.getLayoutParams().width = (int)(90 * displayMetrics.density); block.getLayoutParams().height = (int)(60 * displayMetrics.density) ; break;
            case 7: block.getLayoutParams().width = (int)(90 * displayMetrics.density); block.getLayoutParams().height = (int)(90 * displayMetrics.density) ; break;

        }
    }

    public boolean collision(int currentBlockId, Field field){
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
            if(bigField.checkEmpty(field.getPositionX() + (int) neighbourX.get(i), field.getPositionY() + (int) neighbourY.get(i))) continue;
            else return false;
        }
        return true;
    }

    public void setEmpty(Field field, String playerColor, int currentBlockId){
        bigField.setEmpty(field);
        ArrayList neighbourX = new ArrayList();
        ArrayList neighbourY = new ArrayList();

        int x = 0;
        int y = 0;
        switch(rotation){
            case 0: x = 0; y = 0; break;
            case 90: x = -1; y = -1; break;
            case 180: x = 0; y = -2; break;
            case 270: x = 1; y = -1; break;
        }
        
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

    public void changePlayer(Player player, Player nextPlayer, Player nnPlayer, Player nnnPlayer){
        ImageView block = findViewById(R.id.block0);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        block.getLayoutParams().width = (int) (30 * displayMetrics.density);
        block.getLayoutParams().height = (int) (30 * displayMetrics.density);

        Player [] players = {player, nextPlayer, nnPlayer, nnnPlayer};
        for(int i=0; i<8; i++){
            if(player.blocks[i]) break;
            else if(i == 7) player.finish = true;
        }
        if(player.finish && nextPlayer.finish && nnPlayer.finish && nnnPlayer.finish) end();
        else {
            int i = 1;
            while (true) {
                playerId++;
                if (playerId == 4) playerId = 0;
                currPlayer = playerColors[playerId];
                if (!players[i].finish) break;
                i++;
            }

            block.setRotation(block.getRotation()- rotation);
            rotation = 0;
            currBlockId = 0;
            if (!nextPlayer.blocks[currBlockId]) {
                while (currBlockId != 7 && !nextPlayer.blocks[currBlockId + 1]) currBlockId++;
                if (currBlockId != 7) currBlockId++;
            }

            setSize(currBlockId);
            block.setImageResource(getResources().getIdentifier(currPlayer + (currBlockId + 1), "drawable", getPackageName()));


        }
    }

    public void end(){
        int max = red.points;
        String champion = "Red";
        if (blue.points > max) {max = blue.points; champion = "Blue";}
        if (yellow.points > max) {max = yellow.points; champion = "Yellow";}
        if (green.points > max) {champion = "Green";}

        Intent endIntent = new Intent(GameActivity.this, EndActivity.class);
        endIntent.putExtra("key1","Red " + Integer.toString(red.points));
        endIntent.putExtra("key2","Blue " + Integer.toString(blue.points));
        endIntent.putExtra("key3","Yellow " + Integer.toString(yellow.points));
        endIntent.putExtra("key4","Green " + Integer.toString(green.points));
        endIntent.putExtra("key5", champion);
        finish();
        startActivity(endIntent);
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
                        .x(clickX - 15 * displayMetrics.density)
                        .y(clickY - 15 * displayMetrics.density)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                currentField = bigField.searchField(clickX, clickY, currBlockId);
                if (currentField != null) {
                    activeBlockImg.setX(currentField.getTopLeftX());
                    activeBlockImg.setY(currentField.getTopLeftY());
                    activeBlockImg.animate()
                            .x(currentField.getTopLeftX())
                            .y(currentField.getTopLeftY())
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

    public void nextPlayer(View view) {
        if (collision(currBlockId, currentField)) {

            Toolbar toolbar = findViewById(R.id.toolbar);
            Window window = getWindow();
            setEmpty(currentField, currPlayer, currBlockId);

            switch(currPlayer){
                case "red":
                    red.deleteBlock(currBlockId);
                    changePlayer(red, blue, yellow, green);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                    window.setStatusBarColor(getResources().getColor(R.color.blue));
                    break;
                case "blue":
                    blue.deleteBlock(currBlockId);
                    changePlayer(blue, yellow, green, red);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.yellow));
                    window.setStatusBarColor(getResources().getColor(R.color.yellow));
                    break;
                case "yellow":
                    yellow.deleteBlock(currBlockId);
                    changePlayer(yellow, green, red, blue);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                    window.setStatusBarColor(getResources().getColor(R.color.green));
                    break;
                case "green":
                    green.deleteBlock(currBlockId);
                    changePlayer(green, red, blue, yellow);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                    window.setStatusBarColor(getResources().getColor(R.color.red));
                    break;
            }
            TextView pointsRed = findViewById(R.id.pointsRed);
            TextView pointsBlue = findViewById(R.id.pointsBlue);
            TextView pointsYellow = findViewById(R.id.pointsYellow);
            TextView pointsGreen = findViewById(R.id.pointsGreen);

            pointsRed.setText(Integer.toString(red.points));
            pointsBlue.setText(Integer.toString(blue.points));
            pointsYellow.setText(Integer.toString(yellow.points));
            pointsGreen.setText(Integer.toString(green.points));

        }
    }

    public void pass(View view) {
        switch(currPlayer){
            case "red": red.finish = true; changePlayer(red, blue, yellow, green); break;
            case "blue": blue.finish = true; changePlayer(blue, yellow, green, red); break;
            case "yellow": yellow.finish = true;  changePlayer(yellow, green, red, blue); break;
            case "green": green.finish = true;  changePlayer(yellow, green, blue, yellow); break;
        }
    }

    public void rotate(View view) {
        ImageView block = findViewById(R.id.block0);
                switch(view.getId()){
                    case R.id.rotateRight:
                        block.setRotation(block.getRotation()+ 90);
                        rotation += 90;
                        if(rotation == 360) rotation = 0;
                        break;
                    case R.id.rotateLeft:
                        block.setRotation(block.getRotation()- 90);
                        rotation -= 90;
                        if(rotation == -90) rotation = 270;
                        break;
                }
    }
}
