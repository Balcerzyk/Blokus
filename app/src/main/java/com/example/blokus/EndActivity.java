package com.example.blokus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String key1 = intent.getStringExtra("key1");
        String key2 = intent.getStringExtra("key2");
        String key3 = intent.getStringExtra("key3");
        String key4 = intent.getStringExtra("key4");
        String key5 = intent.getStringExtra("key5");

        TextView champion = findViewById(R.id.champion);
        TextView table1 = findViewById(R.id.table1);
        TextView table2 = findViewById(R.id.table2);
        TextView table3 = findViewById(R.id.table3);
        TextView table4 = findViewById(R.id.table4);

        champion.setText(" The winner is: " + key5);
        table1.setText(key1);
        table2.setText(key2);
        table3.setText(key3);
        table4.setText(key4);

    }

    public void backMenu(View view) {
        Intent mainIntent = new Intent(EndActivity.this, MainActivity.class);
        finish();
        startActivity(mainIntent);
    }
}
