namespace podcast_api.Models
{
    public class FavoriteEpisodes
    {
        public Guid UserId { get; set; }
        public Guid EpisodeId { get; set; }
        public virtual User User { get; set; }
        public virtual Episode Episode { get; set; }
    }
}
