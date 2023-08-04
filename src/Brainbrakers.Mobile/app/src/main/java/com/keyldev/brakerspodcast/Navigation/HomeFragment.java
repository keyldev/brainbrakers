package com.keyldev.brakerspodcast.Navigation;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyldev.brakerspodcast.Adapters.AuthorListAdapter;
import com.keyldev.brakerspodcast.Adapters.PodcastListAdapter;
import com.keyldev.brakerspodcast.Models.Author;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.User;
import com.keyldev.brakerspodcast.Models.UserViewModel;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.PodcastsService;
import com.keyldev.brakerspodcast.Services.UserService;
import com.keyldev.brakerspodcast.Utilities.TimeConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ACCESS_TOKEN, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessToken = getArguments().getString(ACCESS_TOKEN);
            Log.d("ACEESS TOKEN", accessToken);

            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView subsRecycler, updatesRecycler;
    private FrameLayout loadingContainer;
    private ArrayList<Podcast> aList = new ArrayList<>();

    private TextView helloTextView, usernameTextView;
    private ImageView userAvatarImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        subsRecycler = view.findViewById(R.id.subsListRecycler);
        loadingContainer = view.findViewById(R.id.loadingContainer);
        subsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        helloTextView = view.findViewById(R.id.helloTextView);
        usernameTextView = view.findViewById(R.id.userFullNameTextView);
        userAvatarImageView = view.findViewById(R.id.userAvatarImageView);
        helloTextView.setText(TimeConverter.getTimeOfDay());


        UserService userInfo = new UserService();
        userInfo.getUserInfo(null, accessToken, new UserService.OnUserResponseListener() {
            @Override
            public void onGettingUserInfoSuccess(User user) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserViewModel.USER_ID = user.id.toString();
                        String userName = user.firstName + " " + user.lastName;
                        if (user.isConfirmed)
                            userName += "\uD83C\uDFB5";
                        usernameTextView.setText(userName);

                        String avatarURL = user.avatarURL;
                        if (avatarURL != null || avatarURL.length() != 0)
                            Picasso.get().load(avatarURL).into(userAvatarImageView);
                    }
                });
            }

            @Override
            public void onGettingUserInfoFailure(String errorMessage) {
                Log.d("Error while getting user info", " asd" + errorMessage);
            }
        });

        AuthorListAdapter authorListAdapter = new AuthorListAdapter(getContext(), aList, new AuthorListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("podcastId", podcast.id.toString());
                args.putString("accessToken", accessToken);
                navController.navigate(R.id.aboutPodcastFragment, args);
            }
        });
        UserService user = new UserService();
        user.getSubscribes(UUID.fromString(UserViewModel.USER_ID != null ? UserViewModel.USER_ID : "08db60e7-db21-4e63-8775-254333c7776e"), accessToken, new UserService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        authorListAdapter.addToList(podcasts);
                    }
                });
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {

            }
        });
        subsRecycler.setAdapter(authorListAdapter);

        updatesRecycler = view.findViewById(R.id.updatesListRecycler);

        PodcastListAdapter podcastListAdapter = new PodcastListAdapter(getContext(), new PodcastListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("podcastId", podcast.id.toString());
                args.putString("accessToken", accessToken);
                navController.navigate(R.id.aboutPodcastFragment, args);
            }
        });

        PodcastsService podcastsService = new PodcastsService();

        podcastsService.getPodcasts(new PodcastsService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (podcasts != null && podcasts.size() > 0)
                            podcastListAdapter.addToList(podcasts);

                        setLoading(false);
                    }
                });
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {
                Log.d("Error while getting podcasts", errorMessage + "");
                setLoading(false);
            }
        });
        updatesRecycler.setAdapter(podcastListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void setLoading(boolean isLoading){
        if(isLoading){
            loadingContainer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingContainer.setVisibility(View.VISIBLE);
                }
            }, 300);
        } else {
            loadingContainer.setVisibility(View.GONE);
        }
    }
}