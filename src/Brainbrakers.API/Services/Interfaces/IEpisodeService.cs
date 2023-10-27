using podcast_api.Models;

namespace Brainbrakers.API.Services.Interfaces
{
    public interface IEpisodeService
    {

        public Task<Episode> GetEpisodeAsync(Guid id);
        public Task<bool> CreateEpisodeAsync(Episode episode, IFormFile audioFile);
    }
}
