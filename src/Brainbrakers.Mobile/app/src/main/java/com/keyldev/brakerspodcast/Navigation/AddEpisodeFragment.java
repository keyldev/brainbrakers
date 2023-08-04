package com.keyldev.brakerspodcast.Navigation;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.keyldev.brakerspodcast.Adapters.EpisodesListAdapter;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Services.EpisodesService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEpisodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEpisodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_PODCAST_ID = "podcastId";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_CODE_PICK_SOUNDFILE = 1;

    // TODO: Rename and change types of parameters
    private String accessToken;
    private String podcastId;
    private String mParam2;

    public AddEpisodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEpisodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEpisodeFragment newInstance(String param1, String param2) {
        AddEpisodeFragment fragment = new AddEpisodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher;
    File f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            podcastId = getArguments().getString(ARG_PODCAST_ID);
            Log.d("ACCESS TOKEN FROM EP", accessToken + " ");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // There are no request codes
                            Uri uri = result.getData().getData();
                            f = new File(getDataColumn(getContext(), uri, null, null));
                            Toast.makeText(getContext(), "" + f.getName(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), data.getData().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    private Button chooseFileButton, addEpisodeToPodcast;
    private EditText episodeTitleEditText, episodeDescriptionEditText, episodeAuthorsEditText;
    private CheckBox isAdultContent;

    // imagebutton для аватарки но позже
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_episode, container, false);
        episodeAuthorsEditText = view.findViewById(R.id.episodeAuthorsEditText);
        episodeDescriptionEditText = view.findViewById(R.id.episodeDescriptionEditText);
        episodeTitleEditText = view.findViewById(R.id.episodeTitleEditText);
        isAdultContent = view.findViewById(R.id.isAdultCheckBox);


        addEpisodeToPodcast = view.findViewById(R.id.addNewEpisodeButton);
        chooseFileButton = view.findViewById(R.id.chooseEpisodeFileButton);
        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*"); // specify "audio/mp3" to filter only mp3 files

                startActivityForResult(intent, 1);

//                someActivityResultLauncher.launch(intent);
            }
        });
        addEpisodeToPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (f == null) {
                    Toast.makeText(getContext(), "Нельзя отправлять эпизод без файла", Toast.LENGTH_LONG).show();
                    return;
                }
                Episode e = new Episode();
                e.id = UUID.randomUUID();
                e.imageURL = "https://sun2.43222.userapi.com/s/v1/ig2/nEeF53noJF2bt41gPeyy2v-7nUSPd_dkT4aY-BV7LdbKmvAWr0BznTQOONvbrUjnWUHhkJS8TkRe1oFAlHHVNGJn.jpg?size=100x100&quality=95&crop=85,84,348,348&ava=1";
                e.title = episodeTitleEditText.getText().toString();
                e.description = episodeDescriptionEditText.getText().toString();
                e.isAdultContent = isAdultContent.isChecked();
                e.episodeNumber = 1;
                Log.d("GUID FROM STRING - ", " " + e.id);
                e.podcastId = UUID.fromString(podcastId);

                EpisodesService eService = new EpisodesService();
                eService.create(e, f, accessToken);
                EpisodesListAdapter.Instance.addToList(e);

                Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment).popBackStack();

            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String path = getPathFromUri(uri);
            f = new File(path);
            // Используйте абсолютный путь к файлу
        }
    }
    private String getPathFromUri(Uri uri) {
        String filePath = "";
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            File file = new File(getActivity().getCacheDir().getAbsolutePath(), "temp.mp3");
            writeFile(inputStream, file);
            filePath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
    private void writeFile(InputStream input, File file) throws IOException {
        OutputStream output = new FileOutputStream(file);
        try {
            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            output.flush();
        } finally {
            output.close();
            input.close();
        }
    }

}