using podcast_api.Models;
using System.Security.Claims;

namespace Brainbrakers.API.Repository.Interfaces
{
    public interface IAuthRepository
    {

        public Task<User> LoginUserAsync(LoginRequest request);

    }
}
