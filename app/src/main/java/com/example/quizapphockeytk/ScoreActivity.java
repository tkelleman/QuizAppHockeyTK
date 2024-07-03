package com.example.quizapphockeytk;

import static android.content.ContentValues.TAG;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    //XML Declarations
    TextView receivedScore, highScoresReceived;
    Button emailBTN, highScoresButton;
    //Variable Declarations
    int score;
    String name;
    Intent myIntent;
    //DataBase Declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabaseScore, mDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        receivedScore = findViewById(R.id.recievedScore);
        highScoresReceived = findViewById(R.id.highScoresReceived);
        myIntent = getIntent();
        emailBTN = (Button) findViewById(R.id.emailButton);
        highScoresButton = (Button) findViewById(R.id.highScoresButton);
        //Make it get the extra
        score = myIntent.getIntExtra("sentScore", 0);

        receivedScore.setText(getString(R.string.printReceivedScore) + score);
        //Firebase Inflation
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseScore = firebaseDatabase.getReference("SEND_SCORE");
        //mDatabaseName = firebaseDatabase.getReference("SEND_NAME");

        //Email Button
        emailBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] address = new String[]{getString(R.string.emailAddress)};
                String subject = getString(R.string.subjectText);
                String body = getString(R.string.bodyText1) + score + getString(R.string.bodyText2);
                composeEmail(address, subject, body);
            }
        });
        //High Scores Button
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ((TextView) findViewById(R.id.nameText)).getText().toString();
                PlayerScore newPlayerScore = new PlayerScore(name, score);
                String scoreKey = mDatabaseScore.push().getKey();
                mDatabaseScore.child(scoreKey).setValue(newPlayerScore);
                ArrayList<PlayerScore> scoreArrayList = new ArrayList<PlayerScore>();

                mDatabaseScore.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String result = "";
                        for (DataSnapshot child : snapshot.getChildren()) {
                            result += child.getValue().toString() + "\n";
                            PlayerScore newPlayer = child.getValue(PlayerScore.class);
                            scoreArrayList.add(newPlayer);
                        }
                        highScoresReceived.setText("HighScores:\n \n" + scoreArrayList.toString());
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });
    }

    private void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("message/rfc822");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}