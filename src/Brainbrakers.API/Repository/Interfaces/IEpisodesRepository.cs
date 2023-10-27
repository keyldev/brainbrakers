using podcast_api.Models;

namespace Brainbrakers.API.Repository.Interfaces
{
    public interface IEpisodesRepository
    {
        public Task<Episode> GetEpisodeAsync(Guid id);
        public Task<Episode> GetEpisodeByTitleAsync(string title);
        public Task<bool> CreateEpisodeAsync(Episode episode);
    }
}
