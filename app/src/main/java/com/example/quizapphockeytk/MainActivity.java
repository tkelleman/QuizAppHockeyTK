package com.example.quizapphockeytk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;

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
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "org.example.android.QuizAppHockeyTK";
    private final String PREVIOUS_SCORE_KEY = "SCORE";


    private DatabaseReference mDatabase;



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
        mDatabase = FirebaseDatabase.getInstance().getReference("SEND_SCORE");
        //Initialize MediaPlayer
        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.click_sound);

        //Questions, Answers, Sounds - Objects
        q1 = new Question(getResources().getString(R.string.q1Text), true, R.raw.q1sound);
        q2 = new Question(getString(R.string.q2Text), false, R.raw.q2sound);
        q3 = new Question(getString(R.string.q3Text), true,R.raw.q3sound);
        q4 = new Question(getString(R.string.q4Text), true, R.raw.q4sound);
        q5 = new Question(getString(R.string.q5Text), true, R.raw.q5sound);
        //Sound List

        //Questions Arrays
        questions = new Question[] {q1, q2, q3, q4, q5};
        currentIndex = 0;
        currentQuestion = questions[currentIndex];
        question.setText(currentQuestion.getqText());

        // Adding Persistent Storage - Shared Preferences Method
        //initialize the shared preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Read initial value
        int prevScore = mPreferences.getInt(PREVIOUS_SCORE_KEY, 0);

        //Set TextView to Previous Score
        TextView scoreView = (TextView) findViewById(R.id.previousScoreText);
        scoreView.setText("Previous Score: " + prevScore);

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
                falseButton.setEnabled(true);
                trueButton.setEnabled(true);
                int tempSound = currentQuestion.getqSound();
                MediaPlayer questionsSound = MediaPlayer.create(MainActivity.this, tempSound);
                Log.d("tempSound ID", "tempSound" + tempSound);

                //Created SharedPreferences editor object
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                //Write the id of the selected button to our SharedPreferences file
                //this is an int Key/Value pair where the:
                //key = PREVIOUS_SCORE = "COLOR"
                //value = view.getID() = "red_button", "blue_button", etc.
                preferencesEditor.putInt(PREVIOUS_SCORE_KEY, score);
                //Commit the value and save the file.
                preferencesEditor.apply();
                //Log.d("Previous Score", score);


                //FIREBASE DATABASE
                String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(score);

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