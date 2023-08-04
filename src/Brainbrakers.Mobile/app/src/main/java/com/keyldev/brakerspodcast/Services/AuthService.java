package com.keyldev.brakerspodcast.Services;

import android.util.Log;

import com.google.gson.Gson;
import com.keyldev.brakerspodcast.Constants;
import com.keyldev.brakerspodcast.Models.LoginResponseModel;
import com.keyldev.brakerspodcast.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthService {
    String url = Constants.HOST_URL+"/api/v1/auth/";

    public void authenticate(String username, String password, OnLoginResponseListener listener) {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("Username", username);
            json.put("Password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(json.toString(), mediaType);
        Request request = new Request.Builder()
                .post(body)
                .url(url + "login")
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

                    listener.onLoginSuccess(json.fromJson(responseString, LoginResponseModel.class));
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

    public interface OnLoginResponseListener {
        void onLoginSuccess(LoginResponseModel loginResponseModel);

        void onLoginFailure(String errorMessage);
    }

    public interface OnRegisterResponseListener {
        void onRegisterSuccess(String response);

        void onRegisterFailure(String errorMessage);
    }
}
