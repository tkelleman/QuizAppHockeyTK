package com.example.quizapphockeytk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {
    TextView recievedScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        recievedScore = findViewById(R.id.recievedScore);
        Intent myIntent = getIntent();

        //Make it get the extra
        int score = myIntent.getIntExtra("sentScore", 0);
        //String str = Integer.toString(score);
        recievedScore.setText("Score: " + score);
    }
}