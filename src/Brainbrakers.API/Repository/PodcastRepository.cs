using Brainbrakers.API.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;

namespace Brainbrakers.API.Repository
{
    public class PodcastRepository : IPodcastRepository
    {
        private readonly ApplicationContext _dbContext;

        public PodcastRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<Podcast> CreatePodcastAsync(Podcast podcast)
        {
            var isExists = await _dbContext.Podcasts.FirstOrDefaultAsync(p => p.Id == podcast.Id);
            if (isExists == null)
            {
                await _dbContext.Podcasts.AddAsync(podcast);
                await _dbContext.SaveChangesAsync();
                return podcast;
            }
            else return null;
        }

        public async Task<List<Episode>> GetEpisodesAsync(Guid id)
        {
            var episodes = await _dbContext.Episodes.Where(e => e.PodcastId == id).ToListAsync();

            return episodes;
        }

        public async Task<Podcast> GetPodcastAsync(Guid id)
        {
            var podcast = await _dbContext.Podcasts.FirstOrDefaultAsync(p => p.Id == id);
            return podcast;
        }
    }
}
