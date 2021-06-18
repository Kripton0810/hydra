package com.kripton.hydra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class weather_fragement extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public weather_fragement() {
        // Required empty public constructor
    }

    public static weather_fragement newInstance(String param1, String param2) {
        weather_fragement fragment = new weather_fragement();
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

    VideoView videoView;
    String path;
    ConstraintLayout constraintLayout;
    ProgressDialog progressDialog;
    TextView temp,city,weather;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_fragement, container, false);
        videoView = view.findViewById(R.id.videoback);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Finding your location and your weather repoet please wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        temp = view.findViewById(R.id.temp);
        city = view.findViewById(R.id.cityname);
        weather = view.findViewById(R.id.weathercond);
        constraintLayout = view.findViewById(R.id.constlay);
        progressDialog.show();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }else {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location!=null)
                    {
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                            Double lat = addresses.get(0).getLatitude();
                            Double lon =addresses.get(0).getLongitude() ;
                            String ur = "https://api.weatherbit.io/v2.0/forecast/hourly?lat="+lat+"&lon="+lon+"&key=d7d882996d684ad8988161f31f5ecf6a&hours=48";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(ur)
                                    .get().header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36")
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    try {
                                        JSONObject object = new JSONObject(response.body().string());
                                        JSONArray array = object.optJSONArray("data");
                                        JSONObject set = array.getJSONObject(0);
                                        JSONObject sky = set.optJSONObject("weather");
                                        Log.d("weather", String.valueOf(sky.getInt("code")));
                                        if (sky.getInt("code") == 804||sky.getInt("code") == 520||sky.getInt("code") == 511||sky.getInt("code") == 502||
                                                sky.getInt("code") == 501||sky.getInt("code") == 200||
                                                sky.getInt("code") == 201||sky.getInt("code") == 202||
                                                sky.getInt("code") == 230)
                                        {
                                            path = "android.resource://com.kripton.hydra/"+R.raw.rain;
                                        }
                                        else if (sky.getInt("code") == 300||sky.getInt("code") == 301||sky.getInt("code") == 500||sky.getInt("code") == 302
                                                ||sky.getInt("code") == 700||sky.getInt("code") == 711||sky.getInt("code") == 731||sky.getInt("code") == 741)
                                        {
                                            path = "android.resource://com.kripton.hydra/"+R.raw.drizzel;
                                        }
                                        else if(sky.getInt("code") == 623||sky.getInt("code") == 622||sky.getInt("code") == 621||sky.getInt("code") == 610
                                                ||sky.getInt("code") == 602||sky.getInt("code") == 601||sky.getInt("code") == 600)
                                        {
                                            path = "android.resource://com.kripton.hydra/"+R.raw.snowfall;
                                        }
                                        else
                                        {
                                            path = "android.resource://com.kripton.hydra/"+R.raw.sunny;
                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                try {
                                                    Log.d("location","lat"+addresses.get(0).getLatitude()+" long "+addresses.get(0).getLongitude());
                                                    progressDialog.cancel();
                                                    progressDialog.hide();
                                                    Uri u = Uri.parse(path);
                                                    Log.d("path",u.getPath());
                                                    Log.d("path",path);
                                                    city.setText(object.getString("city_name"));
                                                    temp.setText(set.getString("temp")+ Html.fromHtml("<sup>°C</sup>"));
                                                    weather.setText(sky.getString("description"));

                                                    videoView.setVideoURI(u);
                                                    videoView.setZOrderOnTop(false);
                                                    videoView.setZOrderMediaOverlay(false);
                                                    videoView.setSoundEffectsEnabled(false);
                                                    videoView.start();
                                                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                        @Override
                                                        public void onPrepared(MediaPlayer mp) {
                                                            mp.setLooping(true);
                                                        }
                                                    });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });


                                    }catch (Exception e)
                                    {

                                    }
                                }
                            });


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
         
        return view;
    }
}