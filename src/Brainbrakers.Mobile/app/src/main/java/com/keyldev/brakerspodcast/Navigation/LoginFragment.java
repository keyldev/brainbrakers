package com.keyldev.brakerspodcast.Navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keyldev.brakerspodcast.MainActivity;
import com.keyldev.brakerspodcast.Models.LoginResponseModel;
import com.keyldev.brakerspodcast.Models.User;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.AuthService;
import com.keyldev.brakerspodcast.Utilities.Encoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button login;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    private EditText usernameEditText, passwordEditText;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txt = view.findViewById(R.id.signUpTextView);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        view.findViewById(R.id.googleLoginButton).setOnClickListener(v -> {

        });
        view.findViewById(R.id.vkLoginButton).setOnClickListener(v -> {

        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        login = view.findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getContext(), MainActivity.class);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getContext(), "Поля должны быть заполнены", Toast.LENGTH_LONG).show();
                    return;
                }
                String encodedPass = "";
                try {
                    encodedPass = Encoder.sha256(password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                AuthService authService = new AuthService();
                authService.authenticate(username, encodedPass, new AuthService.OnLoginResponseListener() {
                    @Override
                    public void onLoginSuccess(LoginResponseModel loginResponseModel) {
                        mainActivity.putExtra(MainActivity.EXTRA_ACCESS_TOKEN, loginResponseModel.accessToken);
                        mainActivity.putExtra(MainActivity.EXTRA_REFRESH_TOKEN, loginResponseModel.refreshToken);
                        startActivity(mainActivity);
                        getActivity().finish();
                    }

                    @Override
                    public void onLoginFailure(String errorMessage) {
                        Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}