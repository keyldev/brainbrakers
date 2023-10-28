using podcast_api.Models;

namespace Brainbrakers.API.Repository.Interfaces
{
    public interface IPodcastRepository
    {
        Task<Podcast> CreatePodcastAsync(Podcast podcast);
        Task<List<Episode>> GetEpisodesAsync(Guid id);
        Task<Podcast> GetPodcastAsync(Guid id);
    }
}
