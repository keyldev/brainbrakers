using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using podcast_api.Models;
using podcast_api.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace podcast_api.Controllers
{
    /*
        - Получения списка подкастов
        - Получения информации об отдельном подкасте
        - Создания нового подкаста
        - Редактирования существующего подкаста
        - Удаления подкаста
        - Получения списка эпизодов для конкретного подкаста
     */
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/v1/[controller]")]
    [ApiController]
    public class PodcastController : ControllerBase
    {
        private PodcastService podcastService = new PodcastService();

        [HttpGet("{id}/info")]
        public IActionResult GetPodcastInfoByID(Guid id)
        {
            var result = podcastService.GetPodcastInfo(id);
            if (result == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(result);
            }
        }

        [HttpGet("trending")]
        public IActionResult GetTrendingAuthors()
        {
            var result = podcastService.GetTrendingAuthors();
            if (result != null)
                return Ok(result);
            else return NotFound();
        }

        [HttpGet("{id}/episodes")]
        public IActionResult GetEpisodes(Guid id) // guid podcast'a
        {
            var result = podcastService.GetEpisodesByPodcastId(id);
            if (result != null) return Ok(result);
            else return NotFound();
        }

        [HttpGet("{id}/authors")]
        public IActionResult GetAuthors(Guid id)
        {
            var result = podcastService.GetPodcastAuthors(id);
            if (result != null) return Ok(result);
            else return BadRequest();
        }

        [HttpGet("find/{text}")]
        public IActionResult FindPodcast(string text)
        {
            var result = podcastService.FindPodcast(text);
            if (result != null) return Ok(result);
            else return BadRequest();
        }

        [HttpGet("{id}/stats")]
        public IActionResult GetPodcastStats(Guid id)
        {
            var result = podcastService.GetPodcastStats(id);
            if (result is not null) return Ok(result);
            else return Conflict();
        }


        [HttpPost("create")]
        public IActionResult CreatePodcast([FromBody] Podcast podcast)
        {
            var result = podcastService.CreatePodcast(podcast);
            if (result != null) return Ok(result);
            else return Forbid();
        }

        [HttpPut("update")]
        public IActionResult Put([FromBody] Podcast podcast)
        {
            var result = podcastService.UpdatePodcast(podcast);
            if (result) return Ok();
            else return Conflict();
        }

        [HttpDelete("{id}/delete")]
        public string Delete(int id)
        {
            return "Podcast with id: " + id + " was successfully deleted";
        }
    }
}