package com.keyldev.brakerspodcast;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.Services.EpisodesService;
import com.keyldev.brakerspodcast.Services.MusicService;
import com.keyldev.brakerspodcast.Utilities.TimeConverter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MusicPlayerActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "my_channel_3";
    String ACCESS_TOKEN = "";
    String EPISODE_ID = "";
    String AUDIO_SAMPLE_URL = "http://10.0.2.2:5000/api/v1/podcasts/";

    public static String EXTRA_ACCESS_TOKEN = "accessToken";
    public static String EXTRA_EPISODE_ID = "episodeId";
    MediaPlayer mMediaPlayer;
    // widgets
    TextView episodeTitle, podcastTitle, playedTime, totalTime;
    ImageView episodeCover;
    ImageView playEpisodeMusicButton, skipLeft, skipRight;
    SeekBar playerProgress;
    //

    Handler mHandler;
    private MediaSessionCompat mMediaSessionCompat;
    private PlaybackStateCompat.Builder mStateBuilder;


    private Runnable mUpdateProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                int currentDuration = mMediaPlayer.getCurrentPosition();
                int totalDuration = mMediaPlayer.getDuration();

                playerProgress.setMax(totalDuration);
                playerProgress.setProgress(currentDuration);

                playedTime.setText(TimeConverter.fromMillisToHours(currentDuration));
                totalTime.setText(TimeConverter.fromMillisToHours(totalDuration));


                mHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey(EXTRA_ACCESS_TOKEN)) {
                    ACCESS_TOKEN = extras.getString(EXTRA_ACCESS_TOKEN);
                }
                if (extras.containsKey(EXTRA_EPISODE_ID)) {
                    EPISODE_ID = extras.getString(EXTRA_EPISODE_ID);
                }
            }
        }
        setContentView(R.layout.activity_music_player);


        playerProgress = findViewById(R.id.playerProgressBar);
        playedTime = findViewById(R.id.playedTimeTextView);
        totalTime = findViewById(R.id.totalTimeTextView);
        episodeCover = findViewById(R.id.playerImageLogo);
        episodeTitle = findViewById(R.id.playerTitleTextView);

        playEpisodeMusicButton = findViewById(R.id.playEpisodeMusicButton);

        mHandler = new Handler();
        mMediaSessionCompat = new MediaSessionCompat(getApplicationContext(), "tag");

//        mMediaSessionCompat.setCallback(new MySessionCallbackk());
        mMediaPlayer = new MediaPlayer();

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        mMediaSessionCompat.setPlaybackState(mStateBuilder.build());

        EpisodesService service = new EpisodesService();
        service.getInfoById(UUID.fromString(EPISODE_ID), ACCESS_TOKEN, new EpisodesService.OnEpisodeResponseListener() {
            @Override
            public void onGettingEpisodeSuccess(Episode podcast) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(podcast.imageURL).into(episodeCover); // if not null
                        episodeTitle.setText(podcast.title);
                    }
                });
            }

            @Override
            public void onGettingEpisodeFailure(String errorMessage) {

            }
        });


        String url = Constants.HOST_URL+"/api/v1/episode/" + EPISODE_ID + "/audio";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ACCESS_TOKEN);
        playEpisodeMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Intent playIntent = new Intent(getApplicationContext(), MusicService.class);
//                    playIntent.putExtra("title", episodeTitle.getText().toString());
//                    playIntent.putExtra("artist","Подкаст 1");
//
//                    playIntent.setAction("PLAY");
//                    startService(playIntent);
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        playEpisodeMusicButton.setImageResource(R.drawable.ic_play_button_svgrepo_com);
                    } else {
                        playEpisodeMusicButton.setImageResource(R.drawable.ic_pause_button);
                        mMediaPlayer.start();
                    }
                    mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(url), headers);
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();

                            mHandler.postDelayed(mUpdateProgressRunnable, 100);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        playerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    this.progress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mHandler.removeCallbacks(mUpdateProgressRunnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(progress);
                mHandler.postDelayed(mUpdateProgressRunnable, 100);
            }
        });
    }
}