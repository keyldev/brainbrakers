using podcast_api.Models;
using podcast_api.Services;

namespace Brainbrakers.API.Services.Interfaces
{
    public interface IAuthService
    {

        Task<RefreshTokenResponse> LoginAsync(LoginRequest request);
        Task<string> RegUser(RegisterRequest user);
        JwtAuthResult? RefreshToken(RefreshTokenRequest request);

    }
}
