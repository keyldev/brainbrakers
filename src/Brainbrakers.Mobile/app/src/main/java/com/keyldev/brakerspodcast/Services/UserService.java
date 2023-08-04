package com.keyldev.brakerspodcast.Services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keyldev.brakerspodcast.Constants;
import com.keyldev.brakerspodcast.Models.LoginResponseModel;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {
    private final String urlToSubs = Constants.HOST_URL+"/api/v1/subscription/";
    private final String urlToSub = Constants.HOST_URL+"/api/v1/subscriptions/";

    private final String userService = Constants.HOST_URL+"/api/v1/user/";

    public void registerUser(String username, String email, String password) {

    }

    public void getUserInfo(UUID userId, String accessToken, OnUserResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(userService + "info/get").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                listener.onGettingUserInfoFailure(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Gson gson = new Gson();
                    User user = gson.fromJson(responseString, User.class);

                    listener.onGettingUserInfoSuccess(user);

                    // Обработка ответа
                    Log.d("User инфо success ", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingUserInfoFailure(errorString);
                    Log.d("User инфо failure ", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void getMyPodcasts(UUID userId, String accessToken, OnPodcastsResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(userService + userId + "/mypodcasts").addHeader("Authorization", "Bearer " + accessToken)
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
                    Log.d("User authored success ", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastsFailure(errorString);
                    Log.d("User authored failure ", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void getSubscribes(UUID userId, String accessToken, OnPodcastsResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(urlToSub + userId + "/all")
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
                    Log.d("User subscribes success ", responseString);
                } else {
                    String errorString = response.body().string();
                    listener.onGettingsPodcastsFailure(errorString);
                    Log.d("User subscribes failure ", response.code() + "");
                    // Обработка ошибок
                }
            }
        });
    }

    public void unsubscribePodcast(UUID userId, UUID podcastId, String accessToken) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("podcastId", podcastId);
            json.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(json.toString(), mediaType);
        Request request = new Request.Builder()
                .post(body)
                .url(urlToSubs + "unsubscribe").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    Gson json = new Gson();
                    // Обработка ответа
                    Log.d("Unsubscribe success ", responseString);
                } else {
                    String errorString = response.body().string();

                    Log.d("Unsubscribe failure ", response.code() + " code");
                    // Обработка ошибок
                }
            }
        });
    }

    public void subscribePodcast(UUID userId, UUID podcastId, String accessToken, OnResponseListener listener) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("podcastId", podcastId);
            json.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(json.toString(), mediaType);
        Request request = new Request.Builder()
                .post(body)
                .url(urlToSubs + "subscribe").addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Отправка запроса и обработка ответа
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    Gson json = new Gson();

                    listener.onRequestSuccess(responseString);
                    // Обработка ответа
                    Log.d("Subscribe success ", responseString);
                } else {
                    String errorString = response.body().string();

                    Log.d("Subscribe failure ", response.code() + " code");
                    // Обработка ошибок
                }
            }
        });
    }

    public interface OnPodcastsResponseListener {
        void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts);

        void onGettingsPodcastsFailure(String errorMessage);
    }

    public interface OnUserResponseListener {
        void onGettingUserInfoSuccess(User user);

        void onGettingUserInfoFailure(String errorMessage);
    }


    public interface OnResponseListener {
        void onRequestSuccess(String response);

        void onRequestFailure(String errorMessage);
    }

}
