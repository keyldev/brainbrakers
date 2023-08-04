package com.keyldev.brakerspodcast.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.keyldev.brakerspodcast.Constants;
import com.keyldev.brakerspodcast.MusicPlayerActivity;
import com.keyldev.brakerspodcast.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    private static final String CHANNEL_ID = "MusicServiceChannel";

    private MediaPlayer mediaPlayer;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private Notification notification;
    private boolean isPaused = false;
    private int playIcon;
    private int pauseIcon;
    private int currentNotificationId = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if (mediaPlayer == null) {
//            mediaPlayer = MediaPlayer.create(this, Uri.parse(Constants.HOST_URL+"/api/v1/podcasts/b393a8e9-f1c2-4746-91a8-014be5b77636/audio"));
//            mediaPlayer.setOnCompletionListener(this);
//        }
//
//        // Создаем уведомление
//        createNotification(intent.getStringExtra("title"),
//                intent.getStringExtra("artist"));
//
//        // Управляем проигрыванием музыки
//        handleIntentAction(intent);
//
//        return START_NOT_STICKY;
//    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tstFileUrl = Constants.HOST_URL + "/api/v1/podcasts/b393a8e9-f1c2-4746-91a8-014be5b77636/audio";
        if (mediaPlayer == null) {
            File musicFile = new File(getApplicationContext().getCacheDir(), "b393a8e9-f1c2-4746-91a8-014be5b77636.mp3");
            Log.d("APP_TAG", "onStartCommand: musicFile "+musicFile.length());
            if (!musicFile.exists() || musicFile.length() < 10) {
                PodcastsService podcastsService = new PodcastsService();
                podcastsService.downloadFile(
                        "",
                        tstFileUrl,
                        musicFile,
                        new PodcastsService.OnEpisodesFileDownloadedListener(){

                            @Override
                            public void onGettingEpisodesSuccessfull(File downloadedFile) {
                                mediaPlayer = MediaPlayer.create(MusicService.this, Uri.fromFile(musicFile));
                                mediaPlayer.setOnCompletionListener(MusicService.this);
                            }

                            @Override
                            public void onGettingEpisodesFailure(String errorMessage) {

                            }
                        }
                );
            } else {
                mediaPlayer = MediaPlayer.create(this, Uri.fromFile(musicFile));
                mediaPlayer.setOnCompletionListener(this);
            }
        }

        // Создаем уведомление
        createNotification(intent.getStringExtra("title"),
                intent.getStringExtra("artist"));

        // Управляем проигрыванием музыки
        handleIntentAction(intent);

        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotification(String title, String artist) {
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_layout);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_layout_big);
        notificationLayout.setTextViewText(R.id.notification_title, title);
        notificationLayout.setTextViewText(R.id.notification_artist, artist);

        // Иконки для управления проигрыванием
        playIcon = R.drawable.ic_play_button_svgrepo_com;
        pauseIcon = R.drawable.ic_pause_button;

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction("PLAY");
        PendingIntent playPendingIntent =
                PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.notification_play, playPendingIntent);

        Intent pauseIntent = new Intent(this, MusicService.class);
        pauseIntent.setAction("PAUSE");
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.notification_pause, pausePendingIntent);

        Intent stopIntent = new Intent(this, MusicService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.notification_stop, stopPendingIntent);

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_notification)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setColor(getResources().getColor(R.color.light_cyan, null))
                .setColorized(true)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded);

        notification = notificationBuilder.build();
        startForeground(currentNotificationId, notification);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Music Service Channel", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
    }

    private void updateNotification(int playPauseBtnIcon) {
        RemoteViews notificationLayout = notification.contentView;
        notificationLayout.setImageViewResource(R.id.notification_play, playPauseBtnIcon);

        notificationBuilder.setContent(notificationLayout);
        notification = notificationBuilder.build();
        notificationManager.notify(currentNotificationId, notification);
    }

    private void handleIntentAction(Intent intent) {
        if (intent.getAction() == null) return;
        if (mediaPlayer == null) return;

        switch (intent.getAction()) {
            case "PLAY":
                if (isPaused) {
                    mediaPlayer.start();
                    isPaused = false;
                } else {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
                updateNotification(pauseIcon);
                break;
            case "PAUSE":
                mediaPlayer.pause();
                updateNotification(playIcon);
                isPaused = true;
                break;
            case "STOP":
                stopSelf();
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        updateNotification(playIcon);
        mediaPlayer.seekTo(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        stopForeground(true);
    }
}