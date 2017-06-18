package com.example.asus.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    TextView tv_name, tv_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_place = (TextView) findViewById(R.id.tv_place);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String place = intent.getStringExtra("place");

        tv_name.setText(name);
        tv_place.setText(place);
    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String name = data.getStringExtra("name");
        String place = data.getStringExtra("place");

        tv_name.setText(name);
        tv_place.setText(place);
    } */
}
