package com.keyldev.brakerspodcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.keyldev.brakerspodcast.Navigation.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    public static String EXTRA_ACCESS_TOKEN = "extra_access_token";
    public static String EXTRA_REFRESH_TOKEN = "extra_refresh_token";

    private String accessToken = "", refreshToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey(EXTRA_ACCESS_TOKEN)) {
                    accessToken = extras.getString(EXTRA_ACCESS_TOKEN);
                }
                if (extras.containsKey(EXTRA_REFRESH_TOKEN)) {
                    refreshToken = extras.getString(EXTRA_REFRESH_TOKEN);
                }
            }
        }
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.discoverFragment,
                R.id.accountFragment,
                R.id.myLibraryFragment
        ).build();*/

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putString("accessToken", accessToken);
        navController.navigate(R.id.homeFragment, bundle);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putString("accessToken", accessToken);
                switch (item.getItemId()) {
                    case R.id.homeFragment: {

                        navController.navigate(R.id.homeFragment, bundle);
                        break;
                    }
                    case R.id.addPodcastFragment: {

                        navController.navigate(R.id.createdPodcastsFragment, bundle);
                        break;
                    }
                    case R.id.discoverFragment: {

                        navController.navigate(R.id.discoverFragment, bundle);
                        break;
                    }
                    case R.id.accountFragment: {

                        navController.navigate(R.id.accountFragment, bundle);
                        break;
                    }
                    case R.id.myLibraryFragment: {

                        navController.navigate(R.id.myLibraryFragment, bundle);
                        break;
                    }
                }
                return true;
            }
        });

//        Toolbar toolbar = findViewById(R.id.discoverToolbar);
//        setSupportActionBar(toolbar);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        navController.navigate(R.id.homeFragment, home);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}