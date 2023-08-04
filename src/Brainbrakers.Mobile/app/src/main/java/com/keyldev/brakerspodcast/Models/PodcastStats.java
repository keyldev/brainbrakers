package com.keyldev.brakerspodcast.Models;

import java.util.Date;
import java.util.UUID;

public class PodcastStats {
    public int id;
    public UUID podcastId ;
    public int subscriberCount ;
    // public int UniqueListenerCount { get; set; }
    public int listenerCount;
    public double rating ;
    public java.util.Date Date;
}
