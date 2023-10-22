using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;
using System.Security.Claims;

namespace Brainbrakers.API.Repository
{
    public class AuthRepository : IAuthRepository
    {
        private readonly ApplicationContext _dbContext;
        public AuthRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<User> LoginUserAsync(LoginRequest request)
        {
            using (var db = _dbContext)
            {
                var user = await db.Users.Include(u => u.RefreshToken).FirstOrDefaultAsync(u => request.Username == u.Username && u.Password == request.Password);
                return user;
            }
        }

    }
}
