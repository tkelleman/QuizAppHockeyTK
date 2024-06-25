package com.example.quizapphockeytk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    TextView receivedScore;
    Intent myIntent;
    int score;
    Button emailBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        receivedScore = findViewById(R.id.recievedScore);
        myIntent = getIntent();
        emailBTN = (Button) findViewById(R.id.emailButton);
        //Make it get the extra
        score = myIntent.getIntExtra("sentScore", 0);
        //String str = Integer.toString(score);
        receivedScore.setText(getString(R.string.printReceivedScore) + score);

        emailBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String[] address = new String[] {getString(R.string.emailAddress)};
                String subject = getString(R.string.subjectText);
                String body = getString(R.string.bodyText1) + score + getString(R.string.bodyText2);
                composeEmail(address, subject, body);
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