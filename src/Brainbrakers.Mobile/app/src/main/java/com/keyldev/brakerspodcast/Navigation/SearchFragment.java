package com.keyldev.brakerspodcast.Navigation;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keyldev.brakerspodcast.Adapters.PodcastListAdapter;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView fondedPodcastRecyclerView;
    PodcastListAdapter adapter;
    EditText text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        fondedPodcastRecyclerView = view.findViewById(R.id.fondedPodcastRecyclerView);
        text = view.findViewById(R.id.searchEditText);
        adapter = new PodcastListAdapter(getContext(), new PodcastListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {

            }
        });

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    search(text.getText().toString());
                    return true;
                }
                return false;
            }
        });


        fondedPodcastRecyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void search(String query) {
        PodcastsService service = new PodcastsService();
        service.find(query, accessToken, new PodcastsService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addToList(podcasts);
                    }
                });
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {
                //Toast.makeText(getContext(), "Ошибка, подкасты не найдены", Toast.LENGTH_LONG).show();
            }
        });
    }
}