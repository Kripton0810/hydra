package com.kripton.hydra;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeFramgemnt_2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public homeFramgemnt_2() {
    }

    public static homeFramgemnt_2 newInstance(String param1, String param2) {
        homeFramgemnt_2 fragment = new homeFramgemnt_2();
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
    ArrayList<MainDataModel> models = new ArrayList<>();
    RecyclerView rv;
    ShimmerFrameLayout simer;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_framgemnt_2, container, false);
        rv = view.findViewById(R.id.rec_view);
        simer = view.findViewById(R.id.news_shimmer);
        simer.startShimmer();





        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get().url("https://newsapi.org/v2/top-headlines?country=in&category=HEALTH&apiKey=3752112d8e3d414fb9bbe02c36554502")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                JSONArray array = object.optJSONArray("articles");
                                Log.d("news1", array.getString(0));

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject news = array.getJSONObject(i);
                                    String img = news.getString("urlToImage");
                                    String titel = news.getString("title");
                                    String desc = news.getString("description");
                                    String time = news.getString("publishedAt");
                                    MainDataModel model = new MainDataModel();
                                    model.setDesc(desc);
                                    model.setImage(img);
                                    model.setTitel(titel);
                                    model.setTime(time);
                                    models.add(model);
                                }
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
                                simer.stopShimmer();
                                simer.setVisibility(View.GONE);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                rv.setLayoutManager(layoutManager);
                                MainDataAdapter adapter = new MainDataAdapter(getActivity(), models);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {

                            }


                        }
                    });
                }catch (Exception e)
                {

                }
            }
        });
        return view;
    }
}