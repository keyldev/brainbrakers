using Brainbrakers.API.Repository.Interfaces;
using podcast_api.Data;

namespace Brainbrakers.API.Repository
{
    public class PodcastRepository : IPodcastRepository
    {
        private readonly ApplicationContext _dbContext;

        public PodcastRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

    }
}
