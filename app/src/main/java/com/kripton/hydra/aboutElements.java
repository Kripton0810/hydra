package com.kripton.hydra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link aboutElements#newInstance} factory method to
 * create an instance of this fragment.
 */
public class aboutElements extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public aboutElements() {
        // Required empty public constructor
    }

    public static aboutElements newInstance(String param1, String param2) {
        aboutElements fragment = new aboutElements();
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

    List<elementModel> list = new ArrayList<>();
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_elements, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        list.add(new elementModel("Nitrogen Dioxide","Breathing air with a high concentration of NO2 can irritate airways in the human respiratory system. ..." +
                " NO2 along with other NOx reacts with other chemicals in the air to form both particulate matter and ozone. Both of these are also harmful when inhaled due " +
                "to effects on the respiratory system.",R.drawable.no2));//done
        list.add(new elementModel("Nitric oxide","NOx has direct and indirect effects on human health. It can cause breathing problems, headaches, chronically " +
                "\reduced lung function, eye irritation, loss of appetite and corroded teeth. Indirectly, it can affect humans by damaging the ecosystems they rely on in water " +
                "and on land—harming animals and plants",R.drawable.nox));//done
        list.add(new elementModel("Ammonia","Exposure to high concentrations of ammonia in air causes immediate burning of the eyes, nose, " +
                "throat and respiratory tract and can result in blindness, " +
                "lung damage or death. Inhalation of lower concentrations can cause coughing, and nose and throat irritation.",R.drawable.amonia));//done
        list.add(new elementModel("Carbon monoxide","The most common symptoms of CO poisoning are headache, dizziness, weakness, upset stomach, vomiting, chest pain, and " +
                "confusion. CO symptoms are often described as “flu-like.” If you breathe in a lot of CO it can make you pass out or kill you.",R.drawable.co));//done

        list.add(new elementModel("Xylene","Exposure to xylene can irritate the eyes, nose, skin, and throat. Xylene can also cause headaches, dizziness, confusion," +
                " loss of muscle coordination, and in high doses, death. Workers may be harmed from exposure to xylene.",R.drawable.xeylen));//

        list.add(new elementModel("Toluene","Breathing toluene vapors in small amounts may cause a mild headache, dizziness, drowsiness, or nausea. With more serious exposure, " +
                "toluene may cause sleepiness, stumbling, irregular heartbeat, fainting, or even death. Toluene vapor is mildly irritating to the skin, eyes, and lungs.",R.drawable.toluene));

        list.add(new elementModel("PM 2.5","Breathing in particle pollution can be harmful to your health. Coarse (bigger) particles, called PM10, can irritate your eyes, nose, and" +
                " throat. ... Fine (smaller) particles, called PM2.5, are more dangerous because they can get into the deep parts of your lungs — or even into " +
                "your blood.",R.drawable.pm25));

        list.add(new elementModel("Benzene","People can be exposed to benzene in the environment from gasoline fumes, automobile exhaust, emissions from some factories, " +
                "and waste water from certain industries. Benzene is commonly found in air in both urban and rural areas, but the levels are usually very low.",R.drawable.benx));

        list.add(new elementModel("AQI","AQI values at or below 100 are generally thought of as satisfactory. When AQI values are above 100, air quality is unhealthy: at first for certain sensitive groups of people, then for everyone as AQI values get higher.     151-200 AQI is " +
                "considered unsafe and anyone could experience negative health effects from pollution in the air. Very Unhealthy (Purple): 201-300 AQI is a serious health risk level" +
                " for everyone",R.drawable.aqi));

        elementAdapter adapter = new elementAdapter(list);

        viewPager2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        new TabLayoutMediator(tabLayout, viewPager2,((tab, position) -> {})).attach();


        return view;
    }
}