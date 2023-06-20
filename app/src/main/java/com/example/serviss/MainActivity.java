package com.example.serviss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        steps = findViewById(R.id.steps);
        steps.setText(String.valueOf(MyService.stepsCheck));
        Toast.makeText(MainActivity.this, String.valueOf(MyService.stepsCheck), Toast.LENGTH_SHORT).show();
    }
}