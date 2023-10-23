using Brainbrakers.API.Repository.Interfaces;
using podcast_api.Data;

namespace Brainbrakers.API.Repository
{
    public class UserRepository : IUserRepository
    {
        private readonly ApplicationContext _dbContext;
        public UserRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;


        }



    }
}
