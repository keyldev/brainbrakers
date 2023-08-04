package com.keyldev.brakerspodcast.Navigation;

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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.keyldev.brakerspodcast.Adapters.EpisodesListAdapter;
import com.keyldev.brakerspodcast.Models.Author;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.UserViewModel;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;
import com.keyldev.brakerspodcast.Services.UserService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutPodcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutPodcastFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PODCAST_ID = "podcastId";
    private static final String ARG_ACCESS_TOKEN = "accessToken";

    // TODO: Rename and change types of parameters
    private String mPodcastId;
    private String accessToken;

    public AboutPodcastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutPodcastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutPodcastFragment newInstance(String param1, String param2) {
        AboutPodcastFragment fragment = new AboutPodcastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PODCAST_ID, param1);
        args.putString(ARG_ACCESS_TOKEN, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPodcastId = getArguments().getString(ARG_PODCAST_ID);
            accessToken = getArguments().getString(ARG_ACCESS_TOKEN);
        }
    }

    private RecyclerView episodesRecyclerList;
    ImageView podcastLogoCoverImageView;
    TextView podcastTitle, podcastDescription, podcastDuration, podcastEpisodesCount;
    ToggleButton subscribePodcastButton;
    UserService user = new UserService();

    private boolean isSub = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_podcast, container, false);

        podcastLogoCoverImageView = view.findViewById(R.id.podcastLogoCoverImageView);
        podcastTitle = view.findViewById(R.id.podcastTitleTextView);
        podcastDuration = view.findViewById(R.id.podcastTotalDurationTextView);
        podcastDescription = view.findViewById(R.id.podcastDescriptionTextView);
        podcastEpisodesCount = view.findViewById(R.id.episodesCountTextView);
        subscribePodcastButton = view.findViewById(R.id.subscribePodcastButton);

        episodesRecyclerList = view.findViewById(R.id.episodesRecyclerList);

        ArrayList<Episode> eList = new ArrayList<Episode>();
        EpisodesListAdapter episodes = new EpisodesListAdapter(getContext(), eList, new EpisodesListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Episode episode, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("accessToken", accessToken);
                args.putString("episodeId", episode.id.toString());

                navController.navigate(R.id.action_aboutPodcastFragment_to_aboutEpisodeFragment, args);
            }
        });
        PodcastsService podcastsService = new PodcastsService();
        podcastsService.getEpisodesById(UUID.fromString(mPodcastId), accessToken, new PodcastsService.OnEpisodesResponseListener() {
            @Override
            public void onGettingEpisodesSuccessfull(ArrayList<Episode> episode, boolean isSubscribed) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        episodes.addArrayToList(episode);
                        subscribePodcastButton.setChecked(isSubscribed);
                        subscribePodcastButton.setOnCheckedChangeListener(AboutPodcastFragment.this::onCheckedChanged);
                    }
                });
            }

            @Override
            public void onGettingEpisodesFailure(String errorMessage) {
                Log.d("Error while getting episodes", "" + errorMessage);
            }
        });
        episodesRecyclerList.setAdapter(episodes);
        podcastsService.getPodcastInfoById(UUID.fromString(mPodcastId), accessToken, new PodcastsService.OnPodcastResponseListener() {
            @Override
            public void onGettingPodcastSuccess(Podcast podcast) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(podcast.coverImageURL).into(podcastLogoCoverImageView);
                        podcastTitle.setText(podcast.title);
                        podcastDuration.setText(podcast.duration + " hour");
                        podcastDescription.setText(podcast.description);
                        Log.d("Episodes count", "count: " + episodes.getItemCount());
                        podcastEpisodesCount.setText(episodes.getItemCount() + " episodes");

                    }
                });
            }

            @Override
            public void onGettingsPodcastFailure(String errorMessage) {
                Log.d("Error while getting podcast info", " " + errorMessage);
            }
        });

        user.getSubscribes(UUID.fromString(UserViewModel.USER_ID), accessToken, new UserService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                for (Podcast p : podcasts) {
                    isSub = (p.id.equals(UUID.fromString(mPodcastId)));
                }
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {

            }
        });

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if (!isChecked) {
            user.unsubscribePodcast(UUID.fromString(UserViewModel.USER_ID), UUID.fromString(mPodcastId), accessToken);
            return;
        } else {
            user.subscribePodcast(UUID.fromString(UserViewModel.USER_ID), UUID.fromString(mPodcastId), accessToken, new UserService.OnResponseListener() {
                @Override
                public void onRequestSuccess(String response) {
                    Log.d("Response is :", "" + response);
                }

                @Override
                public void onRequestFailure(String errorMessage) {

                }
            });
        }
    }
}