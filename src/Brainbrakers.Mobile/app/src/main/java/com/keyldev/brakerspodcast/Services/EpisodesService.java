package com.keyldev.brakerspodcast.Services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keyldev.brakerspodcast.Constants;
import com.keyldev.brakerspodcast.MainActivity;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.Models.LoginResponseModel;
import com.keyldev.brakerspodcast.Models.Podcast;

import java.io.File;
import java.io.IOException;
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

public class EpisodesService {
    String url = Constants.HOST_URL+"/api/v1/episode/";

    public void create(Episode e, File file, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Gson g = new Gson();
        Log.d("Episode json is : ", g.toJson(e));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("episode", g.toJson(e))
                .addFormDataPart("audioFile", file.getName(), RequestBody.create(file, MediaType.parse("audio/mpeg")))
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "multipart/form-data;boundary=MyBoundary")
                .url(url + "create")
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

    public void getInfoById(UUID id, String accessToken, OnEpisodeResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(url + id + "/info").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingEpisodeFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    Episode episode = gson.fromJson(responseString, Episode.class);

                    listener.onGettingEpisodeSuccess(episode);

                    // Обработка ответа
                    Log.d("Response string success:", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingEpisodeFailure(errorString);
                    Log.d("Response string error:", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public interface OnEpisodeResponseListener {
        void onGettingEpisodeSuccess(Episode podcast);

        void onGettingEpisodeFailure(String errorMessage);
    }
}
