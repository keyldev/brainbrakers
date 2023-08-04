package com.keyldev.brakerspodcast.Models;

import java.util.UUID;

public class Episode {
    public UUID id ;
    public String title  = "";
    public String releaseDate ;
    public String description  = "";
    public int duration  = 0;
    public String audioURL  = "";
    public String imageURL  = "";
    public int episodeNumber  = 0;//
    public UUID podcastId ;
    public Podcast Podcast ;

    public double rating  = 0.0;
    public boolean isAdultContent  = false;
}
