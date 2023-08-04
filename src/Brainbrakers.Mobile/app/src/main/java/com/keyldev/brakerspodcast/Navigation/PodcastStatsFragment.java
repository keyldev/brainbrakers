package com.keyldev.brakerspodcast.Navigation;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.keyldev.brakerspodcast.Models.PodcastStats;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;
import com.keyldev.brakerspodcast.Utilities.DateAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PodcastStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PodcastStatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PODCAST_ID = "podcastId";

    // TODO: Rename and change types of parameters
    private String mAccessToken;
    private String mPodcastId;

    public PodcastStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PodcastStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PodcastStatsFragment newInstance(String param1, String param2) {
        PodcastStatsFragment fragment = new PodcastStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_PODCAST_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            mPodcastId = getArguments().getString(ARG_PODCAST_ID);
        }
    }
    private TextView subsCountText, listenerCountText, ratingCountText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_podcast_stats, container, false);
        BarChart lineChart = view.findViewById(R.id.line_chart);
        subsCountText = view.findViewById(R.id.subsCountText);
        listenerCountText = view.findViewById(R.id.listenerCountText);
        ratingCountText = view.findViewById(R.id.ratingText);

        List<BarEntry> entries = new ArrayList<>();
        PodcastsService ps = new PodcastsService();
        ps.getStats(UUID.fromString(mPodcastId), mAccessToken, new PodcastsService.OnStatsResponseListener() {
            @Override
            public void onGettingStatsSuccess(ArrayList<PodcastStats> podcasts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int subs = 0, listener = 0;
                        double rating = 0;

                        for(int i = 0; i < podcasts.size(); i++){
                            entries.add(new BarEntry(i, podcasts.get(i).listenerCount));
                            subs = podcasts.get(i).subscriberCount;
                            listener = podcasts.get(i).listenerCount;
                            rating  = podcasts.get(i).rating;
                        }
                        subsCountText.setText("Общее число подписчиков: " + subs);
                        listenerCountText.setText("Общее число прослушиваний: " + listener);
                        ratingCountText.setText("Общий рейтинг: " + rating);


                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setValueFormatter(new DateAxisValueFormatter()); // Установка форматтера меток на оси X
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1); // Шаг между метками оси X
                        xAxis.setLabelRotationAngle(-45);

                        BarDataSet dataSet = new BarDataSet(entries, "Количество подписчиков");
                        dataSet.setColor(Color.BLUE);
                        dataSet.setValueTextColor(Color.BLACK);

                        BarData lineData = new BarData(dataSet);

                        lineChart.setData(lineData);
                        lineChart.animateY(1000);
                        // Inflate the layout for this fragment
                    }
                });
            }

            @Override
            public void onGettingStatsFailure(String errorMessage) {

            }
        });


        return view;
    }
}