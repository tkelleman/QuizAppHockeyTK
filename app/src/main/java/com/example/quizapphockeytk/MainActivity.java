package com.example.quizapphockeytk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //1.  Declare Instance Variables (UI Elements and Score)
    Button falseButton, trueButton, doneButton;
    TextView question;
    Toast myToast;
    int score;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. Initializing Variables and Inflating our UI Elements (Connecting ID to Layout)
        trueButton = (Button) findViewById(R.id.trueButton);
        falseButton = (Button) findViewById(R.id.falseButton);
        doneButton = (Button) findViewById(R.id.doneButton);
        question = (TextView) findViewById(R.id.question);
        score = 0;
        message = "";

        //3. Do whatever you want to do with its UI Elements
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "Correct!";
                myToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                myToast.show();
                score++;
            }
        });

        falseButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "Incorrect!";
                myToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                myToast.show();
            }
        }));
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declare and initialize intent
                Intent myIntent = new Intent(MainActivity.this, ScoreActivity.class);

                //Put in Extra Information
                myIntent.putExtra("sentScore", score);
                //Start New Activity
                startActivity(myIntent);

            }
        });
    }
}