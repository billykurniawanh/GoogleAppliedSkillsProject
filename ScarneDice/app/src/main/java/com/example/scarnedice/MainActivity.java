package com.example.scarnedice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static int userovrscore;
    private static int userturnscore;
    private static int compovrscore;
    private static int compturnscore;
    private static TextView text1;
    private static TextView text2;
    private static TextView text3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userovrscore = 0;
        userturnscore = 0;
        compovrscore = 0;
        compturnscore = 0;
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        text1.setText("Your score: " + userovrscore);
        text2.setText("Computer score: " + compovrscore);
        text3.setText("Your turn score: " + userturnscore);

    }

    public void helper() {
        Random rand = new Random();
        int n = rand.nextInt(6) + 1;

        ImageView img = (ImageView) findViewById(R.id.gameImageView);

        if (n == 1)
            img.setImageResource(R.drawable.dice1);
        else if (n == 2)
            img.setImageResource(R.drawable.dice2);
        else if (n == 3)
            img.setImageResource(R.drawable.dice3);
        else if (n == 4)
            img.setImageResource(R.drawable.dice4);
        else if (n == 5)
            img.setImageResource(R.drawable.dice5);
        else if (n == 6)
            img.setImageResource(R.drawable.dice6);

        if (n != 1) {
            userturnscore = userturnscore + n;
            text3.setText("Your turn score: " + userturnscore);
        } else {
            userovrscore = userovrscore + userturnscore;
            userturnscore = 0;
            text1.setText("Your score: " + userovrscore);
            text3.setText("Your turn score: " + userturnscore);
            computerTurn();
        }
    }

    public void computerTurn() {
        Random rand = new Random();
        int n = 0;

        Button but2 = (Button) findViewById(R.id.button2);
        Button but3 = (Button) findViewById(R.id.button2);

        but2.setEnabled(false);
        but3.setEnabled(false);
        ImageView img = (ImageView) findViewById(R.id.gameImageView);
        while(n!=1) {
            n = rand.nextInt(6) + 1;

            if (n != 1) {
                compturnscore = compturnscore + n;
            } else {
                compovrscore = compovrscore + compturnscore;
                compturnscore = 0;
                text2.setText("Computer score: " + compovrscore);
            }
        }
        but2.setEnabled(true);
        but3.setEnabled(true);
    }

    public void roll(View view) {
        helper();
    }

    public void hold(View view) {
        userovrscore = userovrscore + userturnscore;
        userturnscore = 0;
        text1.setText("Your score: " + userovrscore);
        text3.setText("Your turn score: " + userturnscore);
    }

    public void reset(View view) {
        userturnscore = 0;
        userovrscore = 0;
        compovrscore = 0;
        compturnscore = 0;
        text1.setText("Your score: " + userovrscore);
        text2.setText("Computer score: " + compovrscore);
        text3.setText("Your turn score: " + userturnscore);
    }
}
