package com.example.asus.mainproject;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    LinearLayout llMain, llMain2, llMain3;
    RadioGroup radioGroup;
    EditText nameText, edittextWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        llMain2 = (LinearLayout) findViewById(R.id.llMain2);
        llMain3 = (LinearLayout) findViewById(R.id.llMain3);

        nameText = (EditText) findViewById(R.id.nameText);
        edittextWhere = (EditText) findViewById(R.id.edittextWhere);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }


    public void onRadioButtonClicked(View view) {

        switch (radioGroup.getCheckedRadioButtonId()) {

            case R.id.radioButton1:
                LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lParams1.gravity = Gravity.CENTER_HORIZONTAL;

                llMain.removeAllViews();
                llMain3.removeAllViews();
                edittextWhere.setText("");

                Button buttonWhere1 = new Button(this);
                buttonWhere1.setId(1);
                buttonWhere1.setOnClickListener(btnclick);
                buttonWhere1.setText("Сохранить");
                buttonWhere1.setEnabled(true);

                edittextWhere.setVisibility(View.VISIBLE);
                edittextWhere.setEnabled(true);
                edittextWhere.setEms(10);

                TextView textAnother1 = new TextView(this);
                textAnother1.setText("На какие пары Вы пойдете?");
                textAnother1.setTextSize(25);

                llMain.addView(textAnother1);
                llMain3.addView(buttonWhere1);
                break;

            case R.id.radioButton2:
                LinearLayout.LayoutParams lParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lParams2.gravity = Gravity.CENTER_HORIZONTAL;

                llMain.removeAllViews();
                llMain3.removeAllViews();
                edittextWhere.setText("");

                Button buttonWhere2 = new Button(this);
                buttonWhere2.setId(2);
                buttonWhere2.setOnClickListener(btnclick);
                buttonWhere2.setText("Сохранить");
                buttonWhere2.setEnabled(true);

                edittextWhere.setVisibility(View.VISIBLE);
                edittextWhere.setEnabled(true);
                edittextWhere.setEms(10);

                TextView textAnother2 = new TextView(this);
                textAnother2.setText("Куда Вы пойдете?");
                textAnother2.setTextSize(25);

                llMain.addView(textAnother2);
                llMain3.addView(buttonWhere2);
                break;

            case R.id.radioButton3:
                LinearLayout.LayoutParams lParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lParams3.gravity = Gravity.CENTER_HORIZONTAL;

                llMain.removeAllViews();
                llMain3.removeAllViews();
                edittextWhere.setText("");

                edittextWhere.setVisibility(View.INVISIBLE);
                edittextWhere.setEnabled(false);

                Button buttonWhere3 = new Button(this);
                buttonWhere3.setId(3);
                buttonWhere3.setOnClickListener(btnclick);
                buttonWhere3.setText("Сохранить");
                buttonWhere3.setEnabled(true);

                llMain3.addView(buttonWhere3);
                break;
        }

    }

    View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String stringName = nameText.getText().toString();
            String stringWhere = edittextWhere.getText().toString();
            String stringHome = "Дома";

            Intent intent = new Intent(MainActivity.this, ViewActivity.class);

            switch (v.getId()) {
                case 1:
                    intent.putExtra("name", stringName);
                    intent.putExtra("place", stringWhere);
                    startActivity(intent);
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Добавлено!",
                            Toast.LENGTH_SHORT);
                    toast1.show();
                    break;
                case 2:
                    intent.putExtra("name", stringName);
                    intent.putExtra("place", stringWhere);
                    startActivity(intent);
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Добавлено!",
                            Toast.LENGTH_SHORT);
                    toast2.show();
                    break;

                case 3:
                    intent.putExtra("name", stringName);
                    intent.putExtra("place", stringHome);
                    startActivity(intent);
                    Toast toast3 = Toast.makeText(getApplicationContext(),
                            "Добавлено!",
                            Toast.LENGTH_SHORT);
                    toast3.show();
                    break;
            }
        }
    };

}


