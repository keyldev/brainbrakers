package com.keyldev.brakerspodcast.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Podcast {
    public UUID id;
    public String title = "";
    public String description = "";
    public String releaseDate;
    public String coverImageURL = "";
    public String rssFeedURL = "";// ?? не факт что понадобится
    public List<Object> authors = new ArrayList<>();
    //public virtual ICollection<User> Authors  = new List<User>();
    public List<Object> subscribers = new ArrayList<Object>();
    public List<Object> categories = new ArrayList<Object>();
    public List<Object> keywords = new ArrayList<Object>();
    public List<Episode> episodes = new ArrayList<Episode>();

    public double rating = 0.0;
    public int duration = 0;
    public String language = "en";
    public boolean isAdultContent = false;
    public boolean isUserMaker = true;
}
