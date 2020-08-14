package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lbo_Main", "Main completed successfully");
    }

    public void launchNewActivity(View v)
    {
        //launch new activity
        Intent i = new Intent(this, NewActivity.class);
        startActivity(i);
    }

    public void unusedExample()
    {
//        ((Button)findViewById(R.id.someViewName)).setText("This is how to cast a view to a" +
//        "button and immediately set its text");
        Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show();
    }
}