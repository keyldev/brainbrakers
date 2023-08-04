namespace podcast_api.Models;

public class EpisodeStat
{
    public int Id { get; set; }
    public Guid EpisodeId { get; set; }
    public int SubscriberCount { get; set; }
    public int ListenerCount { get; set; }
    public double Rating { get; set; }
    public DateTime Date { get; set; }
}