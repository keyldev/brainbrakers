namespace podcast_api.Models;

public class PodcastStat
{
    public int Id { get; set; }
    public Guid PodcastId { get; set; }
    public int SubscriberCount { get; set; }
    // public int UniqueListenerCount { get; set; }
    public int ListenerCount { get; set; }
    public double Rating { get; set; }
    public DateTime Date { get; set; }
}