package com.keyldev.brakerspodcast.Services;

import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keyldev.brakerspodcast.Constants;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.PodcastStats;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PodcastsService {
    private final String default_url = Constants.HOST_URL + "";
    private final String API_URL = default_url + "/api/v1/podcast/";
    private final String podcasts_url = default_url + "/api/v1/podcasts/";

    public void getTrends(String accessToken, OnPodcastsResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(API_URL + "trending").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingsPodcastsFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<Podcast> podcastModels = gson.fromJson(responseString, new TypeToken<ArrayList<Podcast>>() {
                    }.getType());

                    listener.onGettingPodcastsSuccess(podcastModels);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastsFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void create(Podcast podcast, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();

        Gson gson = new Gson();
        Log.d("Json of podcast:", "--" + gson.toJson(podcast).toString());
        RequestBody body = RequestBody.create(gson.toJson(podcast), mediaType);
        Request request = new Request.Builder().post(body).
                url(API_URL + "create")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();


                    // Обработка ответа
                    Log.d("Response string:", responseString);
                } else {
                    String errorString = response.body().string();
                    Log.d("Response string:", errorString);
                    // Обработка ошибок
                }
            }
        });
    }

    public void getPodcastInfoById(UUID id, String accessToken, OnPodcastResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(API_URL + id + "/info").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingsPodcastFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    Podcast podcast = gson.fromJson(responseString, Podcast.class);

                    listener.onGettingPodcastSuccess(podcast);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void getPodcasts(OnPodcastsResponseListener listener) {
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor()
                .build();

        Request request = new Request.Builder()
                .get()
                .url(podcasts_url + "all")
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingsPodcastsFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<Podcast> podcastModels = gson.fromJson(responseString, new TypeToken<ArrayList<Podcast>>() {
                    }.getType());

                    listener.onGettingPodcastsSuccess(podcastModels);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastsFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void getEpisodesById(UUID id, String accessToken, OnEpisodesResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(API_URL + id + "/episodes").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingEpisodesFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<Episode> episodes = gson.fromJson(responseString, new TypeToken<ArrayList<Episode>>() {
                    }.getType());

                    //TODO: как-нибудь получить подписку
                    listener.onGettingEpisodesSuccessfull(episodes, false);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingEpisodesFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });

    }

    public void getStats(UUID id, String accessToken, OnStatsResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(API_URL + id + "/stats").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingStatsFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<PodcastStats> stats = gson.fromJson(responseString, new TypeToken<ArrayList<PodcastStats>>() {
                    }.getType());

                    listener.onGettingStatsSuccess(stats);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingStatsFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void find(String text, String accessToken, OnPodcastsResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(API_URL + "find/" + text).addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingsPodcastsFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    ArrayList<Podcast> podcasts = gson.fromJson(responseString, new TypeToken<ArrayList<Podcast>>() {
                    }.getType());

                    listener.onGettingPodcastsSuccess(podcasts);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastsFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void update(Podcast podcast, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();

        Gson gson = new Gson();
        Log.d("Json of podcast:", "--" + gson.toJson(podcast).toString());
        RequestBody body = RequestBody.create(gson.toJson(podcast), mediaType);
        Request request = new Request.Builder().put(body).
                url(API_URL + "update")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();


                    // Обработка ответа
                    Log.d("Response string:", responseString);
                } else {
                    String errorString = response.body().string();
                    Log.d("Response string:", errorString);
                    // Обработка ошибок
                }
            }
        });
    }

    public void downloadFile(String accessToken, String downloadUrl, File fileToDownload, OnEpisodesFileDownloadedListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(downloadUrl)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                listener.onGettingEpisodesFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    BufferedInputStream in = new BufferedInputStream(response.body().byteStream());
                    fileToDownload.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                    byte dataBuffer[] = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    listener.onGettingEpisodesSuccessfull(fileToDownload);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onGettingEpisodesFailure(e.getMessage());
                }

            }
        });

    }

    public interface OnStatsResponseListener {
        void onGettingStatsSuccess(ArrayList<PodcastStats> podcasts);

        void onGettingStatsFailure(String errorMessage);
    }

    public interface OnPodcastsResponseListener {
        void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts);

        void onGettingsPodcastsFailure(String errorMessage);
    }

    public interface OnPodcastResponseListener {
        void onGettingPodcastSuccess(Podcast podcast);

        void onGettingsPodcastFailure(String errorMessage);
    }

    public interface OnEpisodesResponseListener {
        void onGettingEpisodesSuccessfull(ArrayList<Episode> episodes, boolean isSubscribed);

        void onGettingEpisodesFailure(String errorMessage);
    }

    public interface OnEpisodesFileDownloadedListener {
        void onGettingEpisodesSuccessfull(File downloadedFile);

        void onGettingEpisodesFailure(String errorMessage);
    }

    public void createToMultipart(Podcast podcast, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Gson g = new Gson();
        Log.d("Episode json is : ", g.toJson(podcast));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("podcast", g.toJson(podcast))
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(API_URL + "create")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    // Обработка ответа
                    Log.d("Response string: 1", responseString);
                } else {
                    String errorString = response.body().string();

                    Log.d("Response string: 2", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }
}
