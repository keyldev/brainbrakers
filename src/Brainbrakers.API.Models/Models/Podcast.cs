
namespace podcast_api.Models
{
    public class Podcast
    {
        public Guid Id { get; set; }
        public string Title { get; set; } = "";
        public string Description { get; set; } = "";
        public DateTime ReleaseDate { get; set; }
        public string CoverImageURL { get; set; } = "";
        public string RssFeedURL { get; set; } = "";// 
        public virtual ICollection<PodcastAuthors> Authors { get; set; } = new List<PodcastAuthors>();
        
        public virtual ICollection<Subscription> Subscribers { get; set; } = new List<Subscription>();
        public virtual ICollection<Categories> Categories { get; set; } = new List<Categories>();
        public virtual ICollection<Keywords> Keywords { get; set; } = new List<Keywords>();
        public virtual ICollection<Episode> Episodes { get; set; } = new List<Episode>();

        public double Rating { get; set; } = 0.0;
        public int Duration { get; set; } = 0;
        public string Language { get; set; } = "en";
        public bool IsAdultContent { get; set; } = false;

    }
}
