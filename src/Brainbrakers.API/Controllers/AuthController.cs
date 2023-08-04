using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using podcast_api.Models;
using podcast_api.Services;
using System.Diagnostics;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace podcast_api.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private AuthService _authService = new AuthService();

        [HttpPost("login")]
        public async Task<IActionResult> LoginUser([FromBody] LoginRequest user)
        {
            var result = await _authService.Login(user);

            Debug.WriteLine(user.Password);
            if (result == null) return BadRequest();
            return Ok(result);
        }
        [HttpPost("logout")]
        public void LogoutUser([FromBody] User user) // <- 
        {

        }

        [HttpPost("register")]
        public async Task<IActionResult> RegisterUser([FromBody] RegisterRequest user) // <- Register request here
        {
            var result = await _authService.RegUser(user);
            if (result != null)
                return Ok(result);
            else
                return Conflict(new
                {
                    message = "Account already exists"
                });
        }

        [HttpPost("token/refresh")]
        public IActionResult GetRefreshToken([FromBody] RefreshTokenRequest token)
        {
            var result = _authService.RefreshToken(token);
            if (result != null) return Ok(new
            {
                refreshToken = result.RefreshToken.TokenString,
                accessToken = result.AccessToken
            });
            return Unauthorized(); // attempt to fuck my system :D
        }
        // вынести в userConroller ??
        [HttpPost("forgotpassword")]
        public void ForgotUserPassword()
        {

        }
        [HttpPost("resetpassword")]
        public void ResetUserPassword()
        {

        }
    }
}
