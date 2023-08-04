package com.keyldev.brakerspodcast.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.keyldev.brakerspodcast.Models.Author;
import com.keyldev.brakerspodcast.Models.Episode;
import com.keyldev.brakerspodcast.R;
import com.keyldev.brakerspodcast.Utilities.TimeConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EpisodesListAdapter extends RecyclerView.Adapter<EpisodesListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Episode> episodes;

    public interface OnStateClickListener {
        void onStateClick(Episode episode, int position);
    }

    private final EpisodesListAdapter.OnStateClickListener onClickListener;

    public EpisodesListAdapter(Context context, ArrayList<Episode> episodes, EpisodesListAdapter.OnStateClickListener onClickListener) {
        Instance = this; // костыль, за это прибьют
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.episodes = episodes;

    }
    public void addArrayToList(ArrayList<Episode> episode){
        episodes.clear();
        episodes.addAll(episode);
        notifyDataSetChanged();
    }
    public void addToList(Episode e) {
        episodes.add(e);
        notifyDataSetChanged();
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public static EpisodesListAdapter Instance;

    @Override
    public EpisodesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.episodes_list_item, parent, false);
        return new EpisodesListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EpisodesListAdapter.ViewHolder holder, int position) {
        Episode episode = episodes.get(position);

        holder.episodeTitleTV.setText(episode.title);
        holder.episodeDurationTV.setText(TimeConverter.fromMillisToHours(episode.duration));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onStateClick(episode, position);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView episodeDurationTV, episodeTitleTV;

        ViewHolder(View view) {
            super(view);
            episodeDurationTV = view.findViewById(R.id.episodeDurationTV);
            episodeTitleTV = view.findViewById(R.id.episodeTitleTV);

        }
    }
}
