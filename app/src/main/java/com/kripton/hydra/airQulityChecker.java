package com.kripton.hydra;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class airQulityChecker extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public airQulityChecker() {
    }
    public static airQulityChecker newInstance(String param1, String param2) {
        airQulityChecker fragment = new airQulityChecker();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private EditText pm2,pm10,co,o3,no,no2,nox,nh3,c6h6,c8h10,aqi,so2,c7h8;
    private TextView tv;
    private ProgressDialog progressDialog;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_air_qulity_checker, container, false);
        pm2 = view.findViewById(R.id.pm2);
        pm10 = view.findViewById(R.id.pm10);
        co = view.findViewById(R.id.co);
        o3 = view.findViewById(R.id.o3);
        no = view.findViewById(R.id.no);
        no2 = view.findViewById(R.id.no2);
        nox = view.findViewById(R.id.nox);
        nh3 = view.findViewById(R.id.nh3);
        c6h6 = view.findViewById(R.id.c6h6);
        c8h10 = view.findViewById(R.id.c8x10);
        aqi = view.findViewById(R.id.aqi);
        so2 = view.findViewById(R.id.so2);
        c7h8 = view.findViewById(R.id.c7h8);
        Button submit = view.findViewById(R.id.submit);
        tv = view.findViewById(R.id.output);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Pleas wait let out AI predict your data");
        //
        submit.setOnClickListener(v -> {
            String url = "https://hydra-kripton.herokuapp.com/model/?pm2="+pm2.getText()+"&pm5="+pm10.getText()+"&no="+no.getText()+"&no2="+no2.getText()+"&nox="+nox.getText()+"&nh3="+nh3.getText()+
                    "&co="+co.getText()+"&so2="+so2.getText()+"&o3="+o3.getText()+"&c6="+c6h6.getText()+"&tol="+c7h8.getText()+"&xy="+c8h10.getText()+"&aqi="+aqi.getText();
            //progressDialog.show();
            sendRequest(url);

        });

        return view;
    }

    private void sendRequest(String url) {
        progressDialog.show();
        OkHttpClient client = new OkHttpClient();

        Log.d("url",url);
        Request request = new Request.Builder().url(url)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(response.body().string());
                            Log.d("output",obj.getString("result"));
                            progressDialog.cancel();
                            progressDialog.hide();
                            tv.setText(obj.getString("result"));
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}