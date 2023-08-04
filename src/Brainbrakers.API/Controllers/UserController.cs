using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.JsonWebTokens;
using podcast_api.Models;
using podcast_api.Services;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Security.Claims;

namespace podcast_api.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private UserService userService = new UserService();

        [HttpGet("users/all")]
        public void GetUsers()
        {

        }
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet("{id}")]
        public IActionResult GetUserByID(Guid id)
        {
            var result = userService.GetUserById(id);
            if (result != null) return Ok(result);
            else return NotFound(new
            {
                message = "An error occurred while found info"
            });
        }

        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpPut("info/update")]
        public IActionResult UpdateUserInfo([FromBody] User user) // хз работает или не
        {
            var result = userService.UpdateUserInfo(user);
            if (result) return Ok();
            else return NotFound();
        }
        [HttpDelete("info/delete")] // инфо делет и юзерделет сделать.
        public IActionResult DeleteUserByID([FromBody] User user)
        {
            return Ok();
        }
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet("info/get")]
        public IActionResult GetUserInfo() // инт заменить на тот GUID
        {
            var identity = HttpContext.User.Identity as ClaimsIdentity;
            var userName = identity.Name;
            var result = userService.GetUserByName(userName);
            Debug.WriteLine(result.Username);
            if (result != null) return Ok(result);
            else return BadRequest();
        }
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        [HttpGet("{id}/get")]
        public IActionResult GetUser(Guid id)
        {
            var result = userService.GetUserById(id);
            if (result != null) return Ok(result);
            return NotFound();
        }
        [HttpGet("{userid}/mypodcasts")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
        public IActionResult GetMyPodcasts(Guid userid)
        {

            Debug.WriteLine($"user id is {userid}");

            var result = userService.GetMyPodcasts(userid);
            if (result != null) return Ok(result);
            else return BadRequest();
        }

    }
}
