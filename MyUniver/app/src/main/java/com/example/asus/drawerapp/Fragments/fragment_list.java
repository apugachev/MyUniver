package com.example.asus.drawerapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.asus.drawerapp.R;

import java.io.IOException;

public class fragment_list extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private OkHttpClient OkHttpClient;

    private Request request;

    TextView name;
    TextView place;
    //String FirstPart;
    //public static String SecondPart;
    String mainstring = "";
    String part = "";

    private String url = "http://dev.moyuniver.ru/api/php/v03/_places/api_get_places.php?memberid=28665485147fa7133b44cb&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&appcode=&os=&ver=&width=&height=";

    public fragment_list() {
    }



    public static fragment_list newInstance(String param1, String param2) {
        fragment_list fragment = new fragment_list();
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

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        OkHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url).build();

        OkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mainstring = response.body().string();
                int i = 0;
                while (i < mainstring.length()) {
                    if (mainstring.charAt(i) != '#' && mainstring.charAt(i) != '\n') {
                        part = part + mainstring.charAt(i);
                        ++i;
                    } else if (mainstring.charAt(i) == '#') {
                        part = part.replace("null", "");
                        final String FirstPart = part;
                        Handler handler = new Handler(getActivity().getMainLooper());
                        handler.post( new Runnable() {
                            @Override
                            public void run() {
                                name = (TextView) getActivity().findViewById(R.id.name);
                                String text = name.getText().toString();
                                String newtext = text + "\n" + FirstPart;
                                name.setText(newtext);
                            }
                        } );
                        part = "";
                        ++i;
                    } else if (mainstring.charAt(i) == '\n') {
                        ++i;
                        final String SecondPart = part;
                        Handler handler = new Handler(getActivity().getMainLooper());
                        handler.post( new Runnable() {
                            @Override
                            public void run() {
                                place = (TextView) getActivity().findViewById(R.id.place);
                                String text = place.getText().toString();
                                String newtext = text + "\n" + SecondPart;
                                place.setText(newtext);
                            }
                        } );
                        part = "";
                    }
                }
            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
