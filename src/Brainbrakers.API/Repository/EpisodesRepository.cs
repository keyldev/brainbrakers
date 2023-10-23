using Brainbrakers.API.Repository.Interfaces;
using podcast_api.Data;

namespace Brainbrakers.API.Repository
{
    public class EpisodesRepository : IEpisodesRepository
    {

        private readonly ApplicationContext _dbContext;

        public EpisodesRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

    }
}
