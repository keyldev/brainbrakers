package com.keyldev.brakerspodcast.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PodcastListAdapter extends RecyclerView.Adapter<PodcastListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Podcast> podcasts;


    public interface OnStateClickListener {
        void onStateClick(Podcast serverModel, int position);
    }

    public interface OnStatsClickListener {
        void onStatsClickListener(Podcast podcast, int position);
    }

    public PodcastListAdapter.OnStatsClickListener onStatsClickListener;

    private final PodcastListAdapter.OnStateClickListener onClickListener;

    public PodcastListAdapter(Context context, PodcastListAdapter.OnStateClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.podcasts = new ArrayList<>();
    }

    @Override
    public PodcastListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.podcasts_list_item, parent, false);
        //R.layout.
        return new PodcastListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PodcastListAdapter.ViewHolder holder, int position) {
        Podcast podcastModel = podcasts.get(position);
        holder.setData(podcastModel, position);
    }

    public void addToList(ArrayList<Podcast> podcasts) {
        this.podcasts.clear();
        this.podcasts.addAll(podcasts);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return podcasts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView podcastTitle, podcastAuthors, podcastEpisodesCount;
        final ImageView podcastLogo;
        final ImageView podcastStats;

        ViewHolder(View view) {
            super(view);
            podcastTitle = view.findViewById(R.id.podcastTitle);
//            podcastDescription = view.findViewById(R.id.podcastDescription);
            podcastAuthors = view.findViewById(R.id.podcastAuthors);
            podcastEpisodesCount = view.findViewById(R.id.podcastEpisodesCount);
            podcastLogo = view.findViewById(R.id.podcastLogo);
            podcastStats = view.findViewById(R.id.podcastStatsButton);
        }

        public void setData(Podcast podcastModel, int position){

            podcastTitle.setText(podcastModel.title);
//        holder.podcastDescription.setText(podcastModel.description);
            podcastAuthors.setText(podcastModel.description);
            podcastEpisodesCount.setText("эпизодов "+podcastModel.episodes.size());
            String url = podcastModel.coverImageURL.length() > 0 ? podcastModel.coverImageURL :
                    "https://sun2.43222.userapi.com/s/v1/ig2/nEeF53noJF2bt41gPeyy2v-7nUSPd_dkT4aY-BV7LdbKmvAWr0BznTQOONvbrUjnWUHhkJS8TkRe1oFAlHHVNGJn.jpg?size=100x100&quality=95&crop=85,84,348,348&ava=1";

            Picasso.get().load(url).into(podcastLogo);

            // костыль??
            if (onStatsClickListener == null)
                podcastStats.setVisibility(View.GONE);
            podcastStats.setOnClickListener(view -> onStatsClickListener.onStatsClickListener(podcastModel, position));

            itemView.setOnClickListener(view -> onClickListener.onStateClick(podcastModel, position));
            if(podcastModel.isUserMaker){
                podcastStats.setVisibility(View.VISIBLE);
            } else  {
                podcastStats.setVisibility(View.GONE);
            }
        }
    }
}
