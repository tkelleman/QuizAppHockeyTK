package com.example.quizapphockeytk;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    Button falseButton, trueButton, nextButton;
    TextView question;
    Toast myToast;
    int score = 0;
    String message;
    Question q1, q2, q3, q4, q5, currentQuestion;
    Question[] questions;
    int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. Initializing Variables and Inflating our UI Elements (Connecting ID to Layout)
        trueButton = (Button) findViewById(R.id.trueButton);
        falseButton = (Button) findViewById(R.id.falseButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        question = (TextView) findViewById(R.id.question);
        score = 0;
        message = "";
        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.click_sound);

        //Question List
        q1 = new Question(getResources().getString(R.string.q1Text), true, R.raw.q2sound);
        q2 = new Question(getString(R.string.q2Text), false, R.raw.q3sound);
        q3 = new Question(getString(R.string.q3Text), true,R.raw.q4sound);
        q4 = new Question(getString(R.string.q4Text), true, R.raw.q2sound);
        q5 = new Question(getString(R.string.q5Text), true, R.raw.q3sound);
        //Sound List

        //Questions Arrays
        questions = new Question[] {q1, q2, q3, q4, q5};
        currentIndex = 0;
        currentQuestion = questions[currentIndex];
        question.setText(currentQuestion.getqText());

        //3. Do whatever you want to do with its UI Elements
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSound.start();
                if (currentQuestion.getCorrectAns() == true) {
                    message = getString(R.string.correctMessage);
                    score++;
                }
                else {
                    message = getString(R.string.incorrectMessage);
                }
                myToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                myToast.show();
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSound.start();
                if (currentQuestion.getCorrectAns() == false) {
                    message = getString(R.string.correctMessage);
                    score++;
                }
                else {
                    message = getString(R.string.incorrectMessage);
                }
                myToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                myToast.show();
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSound.start();
                currentIndex++;
                clickSound.start();
                falseButton.setEnabled(true);
                trueButton.setEnabled(true);
                int tempSound = currentQuestion.getqSound();
                MediaPlayer questionsSound = MediaPlayer.create(MainActivity.this, tempSound);
                Log.d("tempSound ID", "tempSound" + tempSound);
                if (currentIndex < questions.length){
                    //advance and show next question
                    currentQuestion = questions[currentIndex];
                    question.setText(currentQuestion.getqText());

                    questionsSound.start();
                }
                else {
                    //move to score activity
                    //Declare and initialize intent
                    Intent myIntent = new Intent(MainActivity.this, ScoreActivity.class);
                    //Put in Extra Information
                    myIntent.putExtra("sentScore", score);
                    //Start New Activity
                    startActivity(myIntent);
                }
            }
        });
    }
}