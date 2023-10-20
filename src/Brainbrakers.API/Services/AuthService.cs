using Brainbrakers.API.Services;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;
using System.Diagnostics;
using System.Security.Authentication;
using System.Security.Claims;

namespace podcast_api.Services
{
    public class AuthService : IAuthService
    {
        TokenService tokenService = new TokenService("brakersxyz122304!"); // вынести в appsettings.json
        public async Task<RefreshTokenResponse> Login(LoginRequest request)
        {
            return null;
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var user = db.Users.Include(u => u.RefreshToken).FirstOrDefault(u => request.Username == u.Username && u.Password == request.Password);

            //    //var userRefresh = db.RefreshTokens.FirstOrDefault(t => t.UserName == request.Username);
            //    if (user is not null)
            //    {
            //        var claims = new[]
            //        {
            //            new Claim(ClaimTypes.Name, request.Username),
            //        };

            //        return new RefreshTokenResponse() { RefreshToken = user.RefreshToken.TokenString, AccessToken = tokenService.GenerateAccessToken(claims) };
            //    }
            //    return null;
            //}
        }
        public async Task<string> RegUser(RegisterRequest user)
        {
            return null;
            // check if user exists, store it into db ??
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var uInDb = db.Users.FirstOrDefault(u => user.Username == u.Username);
            //    if (uInDb == null)
            //    {
            //        var claims = new List<Claim>
            //        {
            //            new Claim(ClaimTypes.Name, user.Username),
            //            new Claim(ClaimTypes.Email, user.Email)
            //        };
                    
            //        User u = new User();
            //        u.Username = user.Username;
            //        u.Password = user.Password;
            //        u.Email = user.Email;
            //        u.FirstName = user.FirstName;
            //        u.LastName = user.LastName;
            //        JwtAuthResult tokens = tokenService.GenerateTokens(u, claims, DateTime.Now);
            //        u.RefreshToken = tokens.RefreshToken;
            //        u.CreationDate = DateTime.Now;
            //        db.Users.Add(u);
            //        db.RefreshToken.Add(tokens.RefreshToken);
            //        db.SaveChanges();
            //        return tokens.AccessToken;
            //    }
            //}

            //return null;
        }
        public JwtAuthResult? RefreshToken(RefreshTokenRequest request)
        {
            return null;
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var claims = new List<Claim>
            //    {
            //        new Claim (ClaimTypes.Name, request.Username),
            //    };
            //    var user = db.Users.Include(u => u.RefreshToken).FirstOrDefault(u => u.Username == request.Username);
            //    if (user.RefreshToken.TokenString == request.RefreshToken)
            //    {
            //        var newTokens = tokenService.GenerateTokens(user, claims, DateTime.Now);
            //        user.RefreshToken = newTokens.RefreshToken;
            //        db.Users.Update(user);
            //        db.SaveChanges();
            //        return newTokens;
            //    }
            //    else
            //        return null;

            //}
        }
    }
}
