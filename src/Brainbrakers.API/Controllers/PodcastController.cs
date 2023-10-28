using Brainbrakers.API.Services.Interfaces;
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
        //    private PodcastService podcastService = new PodcastService();
        private readonly IPodcastService _podcastService;
        public PodcastController(IPodcastService podcastService)
        {
            _podcastService = podcastService;
        }

        [HttpGet("{id}/info")]
        public async Task<IActionResult> GetPodcastAsync(Guid id)
        {
            var podcastDto = await _podcastService.GetPodcastAsync(id);
            if (podcastDto == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(podcastDto);
            }
        }
        [HttpGet("{id}/episodes")]
        public async Task<IActionResult> GetPodcastEpisodesAsync(Guid id)
        {
            var episodes = await _podcastService.GetPodcastEpisodesAsync(id);
            if (episodes == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(episodes);
            }

        }
        [HttpPost("create")]
        public async Task<IActionResult> CreatePodcastAsync([FromBody] Podcast podcast)
        {
            var creationResult = await _podcastService.CreatePodcastAsync(podcast);
            if (!creationResult.Equals(Guid.Empty)) return Ok(creationResult);
            else return BadRequest();
        }
        //    [HttpGet("trending")]
        //    public IActionResult GetTrendingAuthors()
        //    {
        //        var result = podcastService.GetTrendingAuthors();
        //        if (result != null)
        //            return Ok(result);
        //        else return NotFound();
        //    }


        //    [HttpGet("{id}/authors")]
        //    public IActionResult GetAuthors(Guid id)
        //    {
        //        var result = podcastService.GetPodcastAuthors(id);
        //        if (result != null) return Ok(result);
        //        else return BadRequest();
        //    }

        //    [HttpGet("find/{text}")]
        //    public IActionResult FindPodcast(string text)
        //    {
        //        var result = podcastService.FindPodcast(text);
        //        if (result != null) return Ok(result);
        //        else return BadRequest();
        //    }

        //    [HttpGet("{id}/stats")]
        //    public IActionResult GetPodcastStats(Guid id)
        //    {
        //        var result = podcastService.GetPodcastStats(id);
        //        if (result is not null) return Ok(result);
        //        else return Conflict();
        //    }



        //    [HttpPut("update")]
        //    public IActionResult Put([FromBody] Podcast podcast)
        //    {
        //        var result = podcastService.UpdatePodcast(podcast);
        //        if (result) return Ok();
        //        else return Conflict();
        //    }
    }
}