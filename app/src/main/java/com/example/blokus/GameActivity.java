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

        int widthBlock = block.getMeasuredWidth();
        int heightBlock = block.getMeasuredHeight();

        block.setX(width/2);
        block.setY(findViewById(R.id.rightButton).getY());
        block.animate()
                .x(block.getX() - widthBlock)
                .y(block.getY() - heightBlock)
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
        ArrayList blocks = setPos(currentBlockId, rotation);

        for(int i=0; i<blocks.size(); i++){
            switch((int) blocks.get(i)){
                case 0: neighbourX.add(0); neighbourY.add(0); break;
                case 1: neighbourX.add(1); neighbourY.add(0); break;
                case 2: neighbourX.add(2); neighbourY.add(0); break;
                case 3: neighbourX.add(0); neighbourY.add(1); break;
                case 4: neighbourX.add(1); neighbourY.add(1); break;
                case 5: neighbourX.add(2); neighbourY.add(1); break;
                case 6: neighbourX.add(0); neighbourY.add(2); break;
                case 7: neighbourX.add(1); neighbourY.add(2); break;
                case 8: neighbourX.add(2); neighbourY.add(2); break;
                case 9: neighbourX.add(0); neighbourY.add(3); break;
                case 10: neighbourX.add(3); neighbourY.add(0); break;

            }
        }

        int x;
        int y;
        boolean isOk = false;

        for(int i=0; i<neighbourX.size(); i++) {
            x = field.getPositionX() + (int) neighbourX.get(i);
            y = field.getPositionY() + (int) neighbourY.get(i);
            if((x == 0 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 0) || (x == 10 && y == 10)) isOk = true;

            if(x-1 >= 0 && bigField.getFields(x-1, y).color == currPlayer) return false;
            if(x+1 < 11 && bigField.getFields(x+1, y).color == currPlayer) return false;
            if(y-1 >= 0 && bigField.getFields(x, y-1).color == currPlayer) return false;
            if(y+1 < 11 && bigField.getFields(x, y+1).color == currPlayer) return false;

            if(x-1 >= 0 && y-1 >= 0 && bigField.getFields(x-1, y-1).color == currPlayer) isOk = true;
            if(x-1 >= 0 && y+1 < 11 && bigField.getFields(x-1, y+1).color == currPlayer) isOk = true;
            if(x+1 < 11 && y-1 >= 0 && bigField.getFields(x+1, y-1).color == currPlayer) isOk = true;
            if(x+1 < 11 && y+1 < 11 && bigField.getFields(x+1, y+1).color == currPlayer) {Log.v("PPPPPPPPPPPP", "LEEEL"); isOk = true;}

        }

        if(!isOk) return false;
        for(int i=0; i<neighbourX.size(); i++) {
            if((field.getPositionX() + (int) neighbourX.get(i)) < 0 || (field.getPositionX() + (int) neighbourX.get(i)) > 10
                    || (field.getPositionY() + (int) neighbourY.get(i)) < 0 || (field.getPositionY() + (int) neighbourY.get(i)) > 10) return false;
            if(bigField.checkEmpty(field.getPositionX() + (int) neighbourX.get(i), field.getPositionY() + (int) neighbourY.get(i))) continue;
            else return false;
        }
        return true;
    }

    public void setEmpty(Field field, String playerColor, int currentBlockId){

        ArrayList neighbourX = new ArrayList();
        ArrayList neighbourY = new ArrayList();
        ArrayList blocks = setPos(currentBlockId, rotation);


        for(int i=0; i<blocks.size(); i++){
            switch((int) blocks.get(i)){
                case 0: neighbourX.add(0); neighbourY.add(0); break;
                case 1: neighbourX.add(1); neighbourY.add(0); break;
                case 2: neighbourX.add(2); neighbourY.add(0); break;
                case 3: neighbourX.add(0); neighbourY.add(1); break;
                case 4: neighbourX.add(1); neighbourY.add(1); break;
                case 5: neighbourX.add(2); neighbourY.add(1); break;
                case 6: neighbourX.add(0); neighbourY.add(2); break;
                case 7: neighbourX.add(1); neighbourY.add(2); break;
                case 8: neighbourX.add(2); neighbourY.add(2); break;
                case 9: neighbourX.add(0); neighbourY.add(3); break;
                case 10: neighbourX.add(3); neighbourY.add(0); break;
            }
        }

        for(int i=0; i<blocks.size(); i++) {
            Log.v("ustawiamX: ", Integer.toString(field.getPositionX() + (int) neighbourX.get(i)));
            Log.v("ustawiamY: ", Integer.toString(field.getPositionY() + (int) neighbourY.get(i)));

            bigField.setEmpty(field.getPositionX() + (int) neighbourX.get(i), field.getPositionY() + (int) neighbourY.get(i), currPlayer);
            String paint = "imageView" + (field.getPositionX() + (int) neighbourX.get(i)) + (field.getPositionY() + (int) neighbourY.get(i));
            int imageViewId = getResources().getIdentifier(paint, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(getResources().getIdentifier(playerColor + "1", "drawable", getPackageName()));
        }
    }

    public ArrayList setPos(int blockId, int rot){
        ArrayList blocks = new ArrayList();
        switch(blockId){
            case 0: blocks.add(0); break;
            case 1:
                switch(rot){
                    case 0 : blocks.add(0); blocks.add(3); break;
                    case 90 : blocks.add(0); blocks.add(1); break;
                    case 180 : blocks.add(0); blocks.add(3); break;
                    case 270 : blocks.add(0); blocks.add(1); break;
                }
                break;
            case 2:
                switch(rot){
                    case 0 : blocks.add(0); blocks.add(1); blocks.add(3);break;
                    case 90 : blocks.add(0); blocks.add(1); blocks.add(4);break;
                    case 180 : blocks.add(1); blocks.add(3); blocks.add(4);break;
                    case 270 : blocks.add(0); blocks.add(3); blocks.add(4);break;
                }
                break;
            case 3:
                switch(rot){
                    case 0 : blocks.add(1); blocks.add(3); blocks.add(4); blocks.add(6); break;
                    case 90 : blocks.add(0); blocks.add(1); blocks.add(4); blocks.add(5); break;
                    case 180 : blocks.add(1); blocks.add(3); blocks.add(4); blocks.add(6); break;
                    case 270 : blocks.add(0); blocks.add(1); blocks.add(4); blocks.add(5); break;
                }
                break;
            case 4: blocks.add(0); blocks.add(1); blocks.add(3); blocks.add(4); break;
            case 5:
                switch(rot){
                    case 0 : blocks.add(0); blocks.add(3); blocks.add(6); blocks.add(9); break;
                    case 90 : blocks.add(0); blocks.add(1); blocks.add(2); blocks.add(10); break;
                    case 180 : blocks.add(0); blocks.add(3); blocks.add(6); blocks.add(9); break;
                    case 270 : blocks.add(0); blocks.add(1); blocks.add(2); blocks.add(10); break;
                }
                break;
            case 6:
                switch(rot){
                    case 0 : blocks.add(0); blocks.add(2); blocks.add(3); blocks.add(4); blocks.add(5); break;
                    case 90 : blocks.add(0); blocks.add(1); blocks.add(3); blocks.add(6); blocks.add(7); break;
                    case 180 : blocks.add(0); blocks.add(1); blocks.add(2); blocks.add(3); blocks.add(5); break;
                    case 270 : blocks.add(0); blocks.add(1); blocks.add(4); blocks.add(6); blocks.add(7); break;
                }
                break;
            case 7:
                switch(rot){
                    case 0 : blocks.add(0); blocks.add(3); blocks.add(4); blocks.add(7); blocks.add(8); break;
                    case 90 : blocks.add(1); blocks.add(2); blocks.add(3); blocks.add(4); blocks.add(6); break;
                    case 180 : blocks.add(0); blocks.add(1); blocks.add(4); blocks.add(5); blocks.add(8); break;
                    case 270 : blocks.add(2); blocks.add(4); blocks.add(5); blocks.add(6); blocks.add(7); break;
                }
                break;
        }
        return blocks;
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
                Log.v("5555555", currentField. color);
                float []correction = {0, 0};
                if(rotation == 90 || rotation == 270) correction = setCorrection(currBlockId);
                if (currentField != null) {
                    activeBlockImg.setX(currentField.getTopLeftX() + correction[0]);
                    activeBlockImg.setY(currentField.getTopLeftY() + correction[1]);
                    activeBlockImg.animate()
                            .x(currentField.getTopLeftX() + correction[0])
                            .y(currentField.getTopLeftY() + correction[1])
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
        float []correction = {0, 0};
        if(rotation == 90 || rotation == 270) correction = setCorrection(currBlockId);
        block.setX(currentField.getTopLeftX() + correction[0]);
        block.setY(currentField.getTopLeftY() + correction[1]);
        block.animate()
                .x(currentField.getTopLeftX() + correction[0])
                .y(currentField.getTopLeftY() + correction[1])
                .setDuration(0)
                .start();
    }

    public float []setCorrection(int blockId){
        float []xy = {0, 0};
        switch (blockId){
            case 0: break;
            case 1:
                xy[0] = currentField.getWidth()/2 * displayMetrics.density;
                xy[1] = -currentField.getHeight()/2 * displayMetrics.density;
                return xy;
            case 2: break;
            case 3:
                xy[0] = currentField.getWidth()/2 * displayMetrics.density;
                xy[1] = -currentField.getHeight()/2 * displayMetrics.density;
                return xy;
            case 4: break;
            case 5:
                xy[0] = 3*currentField.getWidth()/2 * displayMetrics.density;
                xy[1] = -3*currentField.getHeight()/2 * displayMetrics.density;
                break;
            case 6:
                xy[0] = -currentField.getWidth()/2 * displayMetrics.density;
                xy[1] = currentField.getHeight()/2 * displayMetrics.density;
                break;
            case 7:
        }
        return xy;
    }

}
