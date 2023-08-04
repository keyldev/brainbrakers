package com.keyldev.brakerspodcast.Navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toolbar;

import com.keyldev.brakerspodcast.Adapters.AuthorListAdapter;
import com.keyldev.brakerspodcast.Adapters.PodcastListAdapter;
import com.keyldev.brakerspodcast.Models.Author;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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


    private RecyclerView trendingAuthors, trendingPodcast;
    EditText findEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ArrayList<Podcast> alist = new ArrayList<Podcast>();

        findEditText = view.findViewById(R.id.searchEditText);
        findEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("accessToken", accessToken);
                Navigation.findNavController(view).navigate(R.id.searchFragment, args);
            }
        });

        trendingAuthors = view.findViewById(R.id.popularAuthorsList);
        AuthorListAdapter adapter = new AuthorListAdapter(getContext(), alist, new AuthorListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("podcastId", podcast.id.toString());
                args.putString("accessToken", accessToken);
                navController.navigate(R.id.action_discoverFragment_to_aboutPodcastFragment, args);
            }
        });

        trendingAuthors.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingAuthors.setAdapter(adapter);

        trendingPodcast = view.findViewById(R.id.mostListenedPodcastList);
        PodcastListAdapter padapter = new PodcastListAdapter(getContext(), new PodcastListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("podcastId", podcast.id.toString());
                args.putString("accessToken", accessToken);
                navController.navigate(R.id.action_discoverFragment_to_aboutPodcastFragment, args);
            }
        });
        PodcastsService podcastsService = new PodcastsService();
        podcastsService.getPodcasts(new PodcastsService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        padapter.addToList(podcasts);
                    }
                });
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {
                Log.d("Error while getting podcasts", errorMessage + "");
            }
        });
       podcastsService.getTrends(accessToken, new PodcastsService.OnPodcastsResponseListener() {
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

           }
       });

        trendingPodcast.setAdapter(padapter);
        return view;
        // Inflate the layout for this fragment
    }
}