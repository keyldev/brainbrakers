using Microsoft.IdentityModel.Tokens;
using podcast_api.Models;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using System.Text.Unicode;

namespace podcast_api.Services
{
    public class SecurityTokenService
    {
        /// <summary>
        /// Генерирует AccessToken на основе LoginRequest'a
        /// </summary>
        /// <param name="user"></param>
        /// <returns>JWT-токен действительный следующие 20 минут.</returns>
        public string GenerateAccessToken(LoginRequest user)
        {
            var signCredentials = new SigningCredentials(new SymmetricSecurityKey(Encoding.UTF8.GetBytes("")), SecurityAlgorithms.HmacSha256);
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, user.Username)
            };

            var jwt = new JwtSecurityToken(
                issuer: "brainbrakers_xyz_server",
                audience: "brainbrakers_xyz_client",
                claims: claims,
                expires: DateTime.UtcNow.AddMinutes(20),
                signingCredentials: signCredentials
                );
            var token = new JwtSecurityTokenHandler().WriteToken(jwt);
            return token;
        }
        public JwtAuthResult GenerateTokenWithRefresh(LoginRequest user, DateTime now)
        {
            var claims = new List<Claim>()
            {
                new Claim(ClaimTypes.Name, user.Username)
            };
            var signCredentials = new SigningCredentials(new SymmetricSecurityKey(Encoding.UTF8.GetBytes("")), SecurityAlgorithms.HmacSha256);

            var jwt = new JwtSecurityToken(
                issuer: "brainbrakers_xyz_server",
                audience: "brainbrakers_xyz_client",
                claims: claims,
                expires: DateTime.UtcNow.AddMinutes(5),
                signingCredentials: signCredentials
                );
            var token = new JwtSecurityTokenHandler().WriteToken(jwt);
            var response = new JwtAuthResult
            {
                AccessToken = token,
                ExpiresIn = (int)TimeSpan.FromMinutes(5).TotalSeconds,
                //RefreshToken = GenerateRefreshToken(user, now)
            };
            return response;

        }
        private RefreshToken GenerateRefreshToken(User user, DateTime now)
        {
            var randomNumber = new byte[32];
            using var rng = RandomNumberGenerator.Create();
            rng.GetBytes(randomNumber);
            return new RefreshToken
            {
                User = user,
                ExpiryTime = now.AddDays(30),
                TokenString = Convert.ToBase64String(randomNumber)
            };
        }
    }
}
