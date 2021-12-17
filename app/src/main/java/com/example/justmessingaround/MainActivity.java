package com.example.justmessingaround;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    private TextView welcomeTV;
    private Button startBTN;
    private ImageView mat;
    private boolean anime = false;
    private ConstraintLayout layout;
    private String password = "";
    private int totalAnswer =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, Gawr.class);
        welcomeTV = findViewById(R.id.welcome);
        startBTN = findViewById(R.id.start);
        layout = findViewById(R.id.layout);
        mat = findViewById(R.id.math);

        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Question.class);
                startActivity(intent1);
            }
        });
        welcomeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundResource(R.drawable.egg);
                anime = true;
            }
        });
        welcomeTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(intent);
                return true;
            }
        });

        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.equals("egg")){
                    Intent intent1 = new Intent(MainActivity.this, NijisanjiEN.class);
                    startActivity(intent1);
                }
                if(anime){
                    openDialog();
                }

            }
        });
    }
    private void openDialog() {
       ExampleDialog exampleDialog = new ExampleDialog();
       exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyText(String password) {
        this.password = password;
    }

}