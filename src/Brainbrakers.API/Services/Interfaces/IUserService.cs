using podcast_api.Models;

namespace Brainbrakers.API.Services.Interfaces
{
    public interface IUserService
    {
        Task<User> GetUserAsync(Guid id);
    }
}
