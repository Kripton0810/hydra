package com.kripton.hydra;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home_Fragement extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_Fragement() {
    }


    public static Home_Fragement newInstance(String param1, String param2) {
        Home_Fragement fragment = new Home_Fragement();
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

    private RecyclerView recyclerview;
    private ProgressBar bar;
    private TextView textView;
    private ShimmerFrameLayout shimmerFrameLayout;
    ArrayList<MainDataModel> dataList = new ArrayList<>();
    MainDataAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home__fragement, container, false);
        NestedScrollView scrollView = view.findViewById(R.id.scrollview);
        bar = view.findViewById(R.id.progressbar);
        recyclerview = view.findViewById(R.id.recycle_view);
        shimmerFrameLayout = view.findViewById(R.id.news_shimmer);
        textView = view.findViewById(R.id.text);
        //init adapter
        adapter = new MainDataAdapter(getActivity(),dataList);
        //Set Layout manager
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);

        getData();
        shimmerFrameLayout.startShimmer();


        return view;
    }

    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get().url("https://newsapi.org/v2/top-headlines?country=in&category=HEALTH&apiKey=3752112d8e3d414fb9bbe02c36554502")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    call.cancel();
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray array = object.optJSONArray("articles");
                    String n;
                    n = Objects.requireNonNull(response.body()).string();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject news = array.getJSONObject(i);
                        String img = news.getString("urlToImage");
                        String titel = news.getString("title");
                        String desc = news.getString("description");
                        MainDataModel model = new MainDataModel();
                        model.setDesc(desc);
                        model.setImage(img);
                        model.setTitel(titel);
                        dataList.add(model);
                    }
                    Handler handler = new Handler();
                    handler.hasCallbacks(() -> {
                        Log.d("news",n);
                        textView.setText(n);
                        bar.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);

                        adapter = new MainDataAdapter(getActivity(),dataList);
                        recyclerview.setAdapter(adapter);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}