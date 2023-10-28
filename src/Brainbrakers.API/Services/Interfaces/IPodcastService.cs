using podcast_api.Models;

namespace Brainbrakers.API.Services.Interfaces
{
    public interface IPodcastService
    {
        Task<Guid> CreatePodcastAsync(Podcast podcast);
        Task<Podcast> GetPodcastAsync(Guid id);
        Task<List<Episode>> GetPodcastEpisodesAsync(Guid id);
    }
}
