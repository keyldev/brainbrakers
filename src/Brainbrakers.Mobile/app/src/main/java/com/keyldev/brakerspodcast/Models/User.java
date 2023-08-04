package com.keyldev.brakerspodcast.Models;

import java.util.UUID;

public class User {
    public UUID id;
    public String username = "";
    public String email = "";
    public String password = "";
    public String firstName = "";
    public String lastName = "";
    public String birthDate;
    public String biography = "";
    public String refreshToken;
    //public virtual ICollection<Podcast> MyPodcasts { get; set; } = new List<Podcast>();
//    public virtual ICollection<PodcastAuthors> MyPodcasts { get; set; } = new List<PodcastAuthors>();
//    public virtual ICollection<Subscription> SubscribedPodcasts { get; set; } = new List<Subscription>();
//    public virtual ICollection<FavoriteEpisodes> FavoriteEpisodes { get; set; } = new List<FavoriteEpisodes>();

    public String genres = ""; // жанры подкастов
    public String interests = "";// интересы
    public String language = "En"; // move into struct.
    public String avatarURL = "";// avatar url = serverurl + /acc/images/id_avatar.jpg -> loading on server
    public String creationDate;
    public boolean isConfirmed = false; // подтвержден ли (почта ?)
    public boolean isCreator = false; // ??? или вынести в роли
    public String refreshTokenId;
}
