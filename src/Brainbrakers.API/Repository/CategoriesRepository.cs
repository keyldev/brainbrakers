using Brainbrakers.API.Repository.Interfaces;
using podcast_api.Data;

namespace Brainbrakers.API.Repository
{
    public class CategoriesRepository : ICategoriesRepository
    {
        private readonly ApplicationContext _dbContext;

        public CategoriesRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;

        }


    }
}
