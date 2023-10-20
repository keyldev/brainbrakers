
namespace podcast_api.Models
{
    // 18.03 1:45 - Заполнил модель эпизода (надо добавить гитлаб)

    /*
        - Уникальный идентификатор эпизода
        - Название эпизода
        - Описание эпизода
        - Длительность эпизода
        - Дата публикации эпизода
        - Ссылки на аудиофайлы для эпизода
        - Добавленные метки
     */
    public class Episode
    {
        public Guid Id { get; set; }
        public string Title { get; set; } = "";
        public DateTime ReleaseDate { get; set; } = DateTime.Today;
        public string Description { get; set; } = "";
        public int Duration { get; set; } = 0;
        public string AudioURL { get; set; } = "";
        public string ImageURL { get; set; } = "";
        public int EpisodeNumber { get; set; } = 0;//
        public virtual ICollection<Categories> Categories { get; set; } = new List<Categories>();
        public virtual ICollection<Keywords> Keywords { get; set; } = new List<Keywords>();
        public virtual ICollection<Guests> Guests { get; set; } = new List<Guests>();
        public virtual ICollection<Hosts> Hosts { get; set; } = new List<Hosts>();
        public virtual ICollection<FavoriteEpisodes> FavoriteEpisodes { get; set; } = new List<FavoriteEpisodes>();

        public Guid PodcastId { get; set; }
        public Podcast? Podcast { get; set; }

        public double Rating { get; set; } = 0.0;
        public bool IsAdultContent { get; set; } = false;
       // public Podcast Podcast { get; set; } = null;
    }
}
