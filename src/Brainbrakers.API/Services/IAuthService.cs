using podcast_api.Models;
using podcast_api.Services;

namespace Brainbrakers.API.Services
{
    public interface IAuthService
    {

        Task<RefreshTokenResponse> Login(LoginRequest request);
        Task<string> RegUser(RegisterRequest user);
        JwtAuthResult? RefreshToken(RefreshTokenRequest request);

    }
}
