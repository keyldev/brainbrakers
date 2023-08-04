namespace podcast_api.Models
{
    public class PodcastAuthors
    {
        public int Id { get; set; }
        public Guid UserId { get; set; }
        public User? User { get; set; }
        public Guid PodcastId { get; set; }
        public Podcast? Podcast { get; set; }
    }
}
