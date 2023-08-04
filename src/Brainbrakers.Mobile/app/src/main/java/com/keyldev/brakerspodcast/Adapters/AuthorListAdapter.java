package com.keyldev.brakerspodcast.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.keyldev.brakerspodcast.Models.Author;
import com.keyldev.brakerspodcast.Models.Podcast;
import com.keyldev.brakerspodcast.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Podcast> authors;

    public interface OnStateClickListener {
        void onStateClick(Podcast authorsModel, int position);
    }

    private final AuthorListAdapter.OnStateClickListener onClickListener;

    public AuthorListAdapter(Context context, ArrayList<Podcast> authorsList, AuthorListAdapter.OnStateClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.authors = authorsList;

    }

    @Override
    public AuthorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.authors_list_item, parent, false);
        return new AuthorListAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(AuthorListAdapter.ViewHolder holder, int position) {
        Podcast author = authors.get(position);
        // изменить на автора
        if(author.coverImageURL.length() == 0)
        {
            author.coverImageURL = "https://sun9-10.userapi.com/impg/tDLrlJBmr5lcWXzlAk4z88TgI4Ouj8kXdOg_dA/71IcTwqkeHg.jpg?size=2160x2160&quality=96&sign=8d75b59cb256e217fa902938a45e1898&type=album";
        }
        Picasso.get().load(author.coverImageURL).into(holder.podcastCoverImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onStateClick(author, position);
            }
        });
    }
    public void addToList(ArrayList<Podcast> podcasts) {
        this.authors.clear();
        this.authors.addAll(podcasts);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView podcastCoverImageView;

        ViewHolder(View view) {
            super(view);
            podcastCoverImageView = view.findViewById(R.id.podcastCoverImageView);
        }
    }
}
