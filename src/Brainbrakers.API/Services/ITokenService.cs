using podcast_api.Models;
using podcast_api.Services;
using System.Security.Claims;

namespace Brainbrakers.API.Services
{
    public interface ITokenService
    {
        public string GenerateAccessToken(IEnumerable<Claim> claims);
        public JwtAuthResult GenerateTokens(User user, IEnumerable<Claim> claims, DateTime now);
        public ClaimsPrincipal GetPrincipalFromExpiredToken(string token);


    }
}
