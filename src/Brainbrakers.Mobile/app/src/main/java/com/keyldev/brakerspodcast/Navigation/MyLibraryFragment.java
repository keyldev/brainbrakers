package com.keyldev.brakerspodcast.Navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyldev.brakerspodcast.Adapters.PodcastListAdapter;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.Models.UserViewModel;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.UserService;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyLibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLibraryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mAccessToken;
    private String mParam2;

    public MyLibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyLibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyLibraryFragment newInstance(String param1, String param2) {
        MyLibraryFragment fragment = new MyLibraryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private RecyclerView mySubsRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);

        mySubsRecycler = view.findViewById(R.id.mySubscriptionsRecyclerView);
        PodcastListAdapter podcastListAdapter = new PodcastListAdapter(getContext(), new PodcastListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Podcast podcast, int position) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("podcastId", podcast.id.toString());
                args.putString("accessToken", mAccessToken);
                navController.navigate(R.id.aboutPodcastFragment, args);
            }
        });
        UserService user = new UserService();
        user.getSubscribes(UUID.fromString(UserViewModel.USER_ID), mAccessToken, new UserService.OnPodcastsResponseListener() {
            @Override
            public void onGettingPodcastsSuccess(ArrayList<Podcast> podcasts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        podcastListAdapter.addToList(podcasts);
                    }
                });
            }

            @Override
            public void onGettingsPodcastsFailure(String errorMessage) {

            }
        });
        mySubsRecycler.setAdapter(podcastListAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}