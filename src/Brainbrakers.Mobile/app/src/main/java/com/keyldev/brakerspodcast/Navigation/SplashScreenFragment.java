package com.keyldev.brakerspodcast.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.keyldev.brakerspodcast.MainActivity;
import com.keyldev.brakerspodcast.Models.LoginResponseModel;
import com.keyldev.brakerspodcast.Models.UserPreferences;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.AuthService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreenFragment extends Fragment {

    private SharedPreferences mSettings;
    private String[] permissions = {"android.permission.INTERNET",
            "android.permission.WRITE_EXTERNAL_STORAGE",
    };
    private static final int REQUEST_PERMISSIONS = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SplashScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashScreenFragment newInstance(String param1, String param2) {
        SplashScreenFragment fragment = new SplashScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            for (int res : grantResults) {
                if (res == PackageManager.PERMISSION_GRANTED) {
                    continue;
                } else {
                    Toast.makeText(getContext(), "Для корректной работы программы, необходимо предоставить все запрашиваемые разрешения", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.auth_nav_host_fragment);

//        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);

//        mSettings = getActivity().getSharedPreferences(UserPreferences.APP_PREFERENCES, Context.MODE_PRIVATE);
//        for (String perm : permissions) {
//            if (ContextCompat.checkSelfPermission(getContext(), perm) == PackageManager.PERMISSION_GRANTED) {
//                continue;
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(permissions, REQUEST_PERMISSIONS);
//                }
//            }
//        }
//
//        if (mSettings.getString(UserPreferences.LOGIN_USERNAME, null) != null && mSettings.getString(UserPreferences.LOGIN_PASSWORD, null) != null) {
//            if (mSettings.getBoolean(UserPreferences.LOGIN_AUTO_ENABLED, false)) {
//                // try to login
//                AuthService authService = new AuthService();
//                authService.authenticate(mSettings.getString(UserPreferences.LOGIN_USERNAME, null), mSettings.getString(UserPreferences.LOGIN_PASSWORD, null), new AuthService.OnLoginResponseListener() {
//                    @Override
//                    public void onLoginSuccess(LoginResponseModel loginResponseModel) {
//                        Intent mainActivity = new Intent(getContext(), MainActivity.class);
//                        // put extra access or refresh token's
//                        mainActivity.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, loginResponseModel.accessToken);
//                        mainActivity.putExtra(MainActivity.EXTRA_REFRESH_TOKEN, loginResponseModel.refreshToken);
//                        startActivity(mainActivity);
//
//                    }
//
//                    @Override
//                    public void onLoginFailure(String errorMessage) {
//                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
//                    }
//                });
//            } else {
//                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);
//            }
//        } else {
//            // show login or register activities
//            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);
//        }

        return view;
        // Inflate the layout for this fragment
    }
}