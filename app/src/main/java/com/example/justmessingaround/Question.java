package com.example.justmessingaround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Question extends AppCompatActivity {
    private RadioGroup q1, q2, q3,q4,q5;
    private Button results;

    int a1 = 0;
    int n1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        q1 = findViewById(R.id.question1);
        q2= findViewById(R.id.question2);
        q3 = findViewById(R.id.question3);
        q4 = findViewById(R.id.question4);
        q5 = findViewById(R.id.question5);
        results = findViewById(R.id.get_result);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(n1 == 5){
                    Intent intent = new Intent(Question.this, NeverGonnaGiveYouUp.class);
                    startActivity(intent);
                }
                else if(a1 == 5){
                    Intent intent = new Intent(Question.this, Best.class );
                    startActivity(intent);
                }
                else if(a1 == 3 || a1 == 4){
                    Intent intent = new Intent(Question.this, Fine.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(Question.this, Bad.class);
                    startActivity(intent);
                }
            }
        });

        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q1_1:
                    case R.id.q1_3:
                        break;
                    case R.id.q1_2:
                        a1++;
                        break;
                    case R.id.q1_4:
                        n1++;
                        break;
                }
            }
        });
        q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q2_1:
                    case R.id.q2_2:
                        break;
                    case R.id.q2_3:
                        a1++;
                        break;
                    case R.id.q2_4:
                        n1++;
                        break;
                }
            }
        });
        q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q3_1:
                        a1++;
                        break;
                    case R.id.q3_3:
                    case R.id.q3_2:
                        break;
                    case R.id.q3_4:
                        n1++;
                        break;
                }
            }
        });
        q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q4_1:
                    case R.id.q4_3:
                        break;
                    case R.id.q4_2:
                        a1++;
                        break;
                    case R.id.q4_4:
                        n1++;
                        break;
                }
            }
        });
        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q5_1:
                        a1++;
                        break;
                    case R.id.q5_3:
                    case R.id.q5_2:
                        break;
                    case R.id.q5_4:
                        n1++;
                        break;
                }
            }
        });
    }
}