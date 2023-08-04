package com.keyldev.brakerspodcast.Navigation;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.keyldev.brakerspodcast.Adapters.EpisodesListAdapter;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPodcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPodcastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PODCAST_ID = "podcastId";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String mPodcastId;

    public AddPodcastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPodcastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPodcastFragment newInstance(String param1, String param2) {
        AddPodcastFragment fragment = new AddPodcastFragment();
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
            Log.d("AT IN PC:", "" + accessToken);

            mPodcastId = getArguments().getString(ARG_PODCAST_ID);
        }
    }

    private Button addNewEpisodeButton, savePodcastButton;
    private ImageView podcastCoverImageButton;
    private EditText podcastTitleEditText, podcastDescriptionEditText, authorsEditText;

    private RecyclerView episodesRecyclerList;
    private ArrayList<Episode> eList = new ArrayList<>();
    Podcast podcast = new Podcast();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_podcast, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> goBack());
        addNewEpisodeButton = view.findViewById(R.id.addNewEpisodeButton);
        savePodcastButton = view.findViewById(R.id.savePodcastButton);
        podcastCoverImageButton = view.findViewById(R.id.podcastCoverImageButton);

        podcastTitleEditText = view.findViewById(R.id.podcastTitleEditText);
        podcastDescriptionEditText = view.findViewById(R.id.podcastDescriptionEditText);
        authorsEditText = view.findViewById(R.id.authorsEditText);
        episodesRecyclerList = view.findViewById(R.id.addedEpisodesRecyclerList);
        EpisodesListAdapter episodesListAdapter = new EpisodesListAdapter(getContext(), eList, new EpisodesListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Episode episode, int position) {

            }
        });
        if (mPodcastId != null && mPodcastId.length() > 0) {
            PodcastsService p = new PodcastsService();
            p.getPodcastInfoById(UUID.fromString(mPodcastId), accessToken, new PodcastsService.OnPodcastResponseListener() {
                @Override
                public void onGettingPodcastSuccess(Podcast p) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            podcast.id = p.id;
                            podcastTitleEditText.setText(p.title);
                            podcastDescriptionEditText.setText(p.description);
                            Picasso.get().load(p.coverImageURL).into(podcastCoverImageButton);
                        }
                    });


                }

                @Override
                public void onGettingsPodcastFailure(String errorMessage) {

                }
            });
            p.getEpisodesById(UUID.fromString(mPodcastId), accessToken, new PodcastsService.OnEpisodesResponseListener() {
                @Override
                public void onGettingEpisodesSuccessfull(ArrayList<Episode> episodes, boolean isSubscribed) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (episodes != null)
                                episodesListAdapter.addArrayToList(episodes);
                        }
                    });
                }

                @Override
                public void onGettingEpisodesFailure(String errorMessage) {

                }
            });
        } else {
            podcast.id = UUID.randomUUID();
        }


        addNewEpisodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle b = new Bundle();
                b.putString("accessToken", accessToken);
                b.putString("podcastId", podcast.id.toString().length() > 1?  podcast.id.toString() : mPodcastId);
                navController.navigate(R.id.action_addPodcastFragment_to_addEpisodeFragment, b);
            }
        });
        episodesRecyclerList.setAdapter(episodesListAdapter);

        savePodcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PodcastsService p = new PodcastsService();

                podcast.title = podcastTitleEditText.getText().toString();
                podcast.description = podcastDescriptionEditText.getText().toString();
                podcast.coverImageURL = "https://sun2.43222.userapi.com/s/v1/ig2/nEeF53noJF2bt41gPeyy2v-7nUSPd_dkT4aY-BV7LdbKmvAWr0BznTQOONvbrUjnWUHhkJS8TkRe1oFAlHHVNGJn.jpg?size=100x100&quality=95&crop=85,84,348,348&ava=1";
                podcast.rating = 7.8;

//                podcast.episodes.addAll(episodesListAdapter.getEpisodes());

                podcast.duration = 55;
                podcast.language = "RU";
                podcast.isAdultContent = true;
                if (mPodcastId != null && mPodcastId.length() > 0)
                    p.update(podcast, accessToken);
                else
                    p.create(podcast, accessToken);
//                Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment).popBackStack();
            }
        });

        return view;
        // Inflate the layout for this fragment
    }

    private void goBack(){
        Navigation.findNavController(requireView()).popBackStack(); // debug
    }
}