package com.keyldev.brakerspodcast.Navigation;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.keyldev.brakerspodcast.Adapters.PodcastListAdapter;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.UserViewModel;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.UserService;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatedPodcastsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatedPodcastsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_USER_ID = "userId";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mAccessToken;
    private String mUserId;

    public CreatedPodcastsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatedPodcastsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatedPodcastsFragment newInstance(String param1, String param2) {
        CreatedPodcastsFragment fragment = new CreatedPodcastsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_USER_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            mUserId = getArguments().getString(ARG_USER_ID);
        }
    }

    Button addNewPodcast;
    RecyclerView userCreatedPodcasts;
    ArrayList<Podcast> podcastArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_created_podcasts, container, false);
//        addNewPodcast = view.findViewById(R.id.addNewPodcastButton);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menu_add){
                    goToCreatePodcast();
                    return true;
                } else {
                    return false;
                }
            }
        });
        userCreatedPodcasts = view.findViewById(R.id.podcastsCreatedRecyclerView);
        podcastArray = new ArrayList<>();


        PodcastListAdapter adapter = new PodcastListAdapter(getContext(), new PodcastListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                Bundle args = new Bundle();
                args.putString("accessToken", mAccessToken);
                args.putString("podcastId", podcast.id.toString());
                Navigation.findNavController(view).navigate(R.id.addPodcastFragment, args);

            }
        });
        adapter.onStatsClickListener = new PodcastListAdapter.OnStatsClickListener() {
            @Override
            public void onStatsClickListener(Podcast podcast, int position) {
                Bundle args = new Bundle();
                args.putString("accessToken", mAccessToken);
                args.putString("podcastId", podcast.id.toString());
                Navigation.findNavController(view).navigate(R.id.podcastStatsFragment, args);
            }
        };
        UserService service = new UserService();
        service.getMyPodcasts(UUID.fromString(UserViewModel.USER_ID), mAccessToken, new UserService.OnPodcastsResponseListener() {
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
        userCreatedPodcasts.setAdapter(adapter);


        // Inflate the layout for this fragment
        return view;
    }

    private void goToCreatePodcast(){
        Bundle args = new Bundle();
        args.putString("accessToken", mAccessToken);
        Navigation.findNavController(requireView()).navigate(R.id.addPodcastFragment, args); // debug
    }
}