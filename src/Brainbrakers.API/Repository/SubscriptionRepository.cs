using Brainbrakers.API.Repository.Interfaces;
using podcast_api.Data;

namespace Brainbrakers.API.Repository
{
    public class SubscriptionRepository : ISubscriptionRepository
    {
        private readonly ApplicationContext _dbContext;

        public SubscriptionRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

    }
}
