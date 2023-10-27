using Brainbrakers.API.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;

namespace Brainbrakers.API.Repository
{
    public class EpisodesRepository : IEpisodesRepository
    {

        private readonly ApplicationContext _dbContext;

        public EpisodesRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

        public  async Task<bool> CreateEpisodeAsync(Episode episode)
        {
            await _dbContext.Episodes.AddAsync(episode);
            await _dbContext.SaveChangesAsync();
            return true;
        }

        public async Task<Episode> GetEpisodeAsync(Guid id)
        {
            var episode = await _dbContext.Episodes.FirstOrDefaultAsync(e=> e.Id == id);
            return episode;
        }

        public async Task<Episode> GetEpisodeByTitleAsync(string title)
        {
            var episode = await _dbContext.Episodes.FirstOrDefaultAsync(e => e.Title == title);
            return episode;
        }
    }
}
