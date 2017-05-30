package com.example.asus.drawerapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.drawerapp.MainActivity;
import com.example.asus.drawerapp.R;

import java.io.IOException;

import at.markushi.ui.CircleButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class fragment_main extends Fragment {

    LinearLayout llMain, llMain2, llMain3;
    RadioGroup radioGroup;
    EditText nameText, edittextWhere;
    CircleButton btnadd;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String mParam1;
    private String mParam2;
    String finalurl;
    private String mainurl = "http://dev.moyuniver.ru/api/php/v03/_places/api_set_place.php?memberid=";
    private String addurl = "&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&appcode=&os=&ver=&width=&height=&place=";

    private OnFragmentInteractionListener mListener;

    public fragment_main() {

    }

    public static fragment_main newInstance(String param1, String param2) {
        fragment_main fragment = new fragment_main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        llMain = (LinearLayout) view.findViewById(R.id.llMain);
        llMain2 = (LinearLayout) view.findViewById(R.id.llMain2);
        llMain3 = (LinearLayout) view.findViewById(R.id.llMain3);

        btnadd = (CircleButton) view.findViewById(R.id.btnadd);
        nameText = (EditText) view.findViewById(R.id.nameText);
        edittextWhere = (EditText) view.findViewById(R.id.edittextWhere);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()) {

                    case R.id.radioButton1:
                        LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lParams1.gravity = Gravity.CENTER_HORIZONTAL;

                        llMain.removeAllViews();
                        llMain3.removeAllViews();
                        edittextWhere.setText("");


                        edittextWhere.setVisibility(View.VISIBLE);
                        edittextWhere.setEnabled(true);
                        edittextWhere.setEms(10);

                        TextView textAnother1 = new TextView(getActivity());
                        textAnother1.setText("На какие пары Вы пойдете?");
                        textAnother1.setTextSize(25);

                        llMain.addView(textAnother1);
                        break;

                    case R.id.radioButton2:
                        LinearLayout.LayoutParams lParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lParams2.gravity = Gravity.CENTER_HORIZONTAL;

                        llMain.removeAllViews();
                        llMain3.removeAllViews();
                        edittextWhere.setText("");


                        edittextWhere.setVisibility(View.VISIBLE);
                        edittextWhere.setEnabled(true);
                        edittextWhere.setEms(10);

                        TextView textAnother2 = new TextView(getActivity());
                        textAnother2.setText("Куда Вы пойдете?");
                        textAnother2.setTextSize(25);

                        llMain.addView(textAnother2);
                        break;

                    case R.id.radioButton3:
                        LinearLayout.LayoutParams lParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lParams3.gravity = Gravity.CENTER_HORIZONTAL;

                        llMain.removeAllViews();
                        llMain3.removeAllViews();
                        edittextWhere.setText("");

                        edittextWhere.setVisibility(View.INVISIBLE);
                        edittextWhere.setEnabled(false);
                        edittextWhere.setText("Home");

                        break;
                }
            }
        });
        btnadd.setOnClickListener(btnclick);
        return view;
    }

     View.OnClickListener btnclick = new View.OnClickListener() {
         private OkHttpClient OkHttpClient;
         private Request request;

        public  void onClick(View v) {
            final String stringName = nameText.getText().toString();
            String stringWhere = edittextWhere.getText().toString();
            finalurl = mainurl + stringName + addurl + stringWhere;
            edittextWhere.setText("");
            nameText.setText("");
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Записано!", Toast.LENGTH_SHORT);
            toast.show();
            OkHttpClient = new OkHttpClient();
            request = new Request.Builder().url(finalurl).build();
            OkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {}
            });

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
