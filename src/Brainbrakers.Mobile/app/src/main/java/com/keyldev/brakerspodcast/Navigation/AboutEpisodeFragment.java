package com.keyldev.brakerspodcast.Navigation;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyldev.brakerspodcast.MainActivity;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.MusicPlayerActivity;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.EpisodesService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutEpisodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutEpisodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_EPISODE_ID = "episodeId";

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String episodeId;

    public AboutEpisodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutEpisodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutEpisodeFragment newInstance(String param1, String param2) {
        AboutEpisodeFragment fragment = new AboutEpisodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_EPISODE_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            episodeId = getArguments().getString(ARG_EPISODE_ID);
        }
    }

    TextView episodeTitleTextView, episodeAuthorTextView, episodeDateTextView, episodeNameTextView, episodeDescriptionTextView;
    ImageView episodeLogoCoverImage;
    Button playEpisodeButton;
    String musicUrl = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_episode, container, false);
        episodeTitleTextView = view.findViewById(R.id.episodeTitleTextView);
        episodeAuthorTextView = view.findViewById(R.id.episodeAuthorTextView);
        episodeNameTextView = view.findViewById(R.id.episodeNameTextView);
        episodeDescriptionTextView = view.findViewById(R.id.episodeDescriptionTextView);
        episodeDateTextView = view.findViewById(R.id.episodeDateTextView);
        episodeLogoCoverImage = view.findViewById(R.id.episodeLogoCoverImage);
        playEpisodeButton = view.findViewById(R.id.playEpisodeButton);


        EpisodesService service = new EpisodesService();
        service.getInfoById(UUID.fromString(episodeId), accessToken, new EpisodesService.OnEpisodeResponseListener() {
            @Override
            public void onGettingEpisodeSuccess(Episode episode) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(episode.imageURL).into(episodeLogoCoverImage);
                        episodeTitleTextView.setText(episode.title);
                        episodeDescriptionTextView.setText(episode.description);
                        musicUrl = episode.audioURL;
                    }
                });
            }

            @Override
            public void onGettingEpisodeFailure(String errorMessage) {
                Log.d("Error while getting episode info", " " + errorMessage);
            }
        });
        playEpisodeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {

//                    mediaPlayer.setDataSource("http://10.0.2.2:5000/api/v1/podcasts/" + episodeId + "/audio");
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            mediaPlayer.start();
//                        }
//                    });



                    Intent i = new Intent(getContext(), MusicPlayerActivity.class);
                    i.putExtra(MusicPlayerActivity.EXTRA_EPISODE_ID, episodeId);
                    i.putExtra(MusicPlayerActivity.EXTRA_ACCESS_TOKEN, accessToken);
                    startActivity(i);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

}