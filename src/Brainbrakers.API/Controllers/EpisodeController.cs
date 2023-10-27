using Brainbrakers.API.Services.Interfaces;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using podcast_api.Models;
using podcast_api.Services;
using System.Diagnostics;
using System.Net;
using System.Net.Http;
using System.Text.Json;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace podcast_api.Controllers
{

    /*
        - Получения списка эпизодов -> into podcastS
        - Получения информации об отдельном эпизоде
        - Создания нового эпизода
        - Редактирования существующего эпизода
        - Удаления эпизода
        - Получения списка эпизодов конкретного подкаста -> into podcastS
     */
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/v1/[controller]")]
    [ApiController]
    public class EpisodeController : ControllerBase
    {
        //private EpisodeService episodeService = new EpisodeService();
        private readonly IEpisodeService _episodesService;
        public EpisodeController(IEpisodeService episodeService)
        {
            _episodesService = episodeService;
        }
        [HttpGet("{id}/info")]
        public async Task<IActionResult> GetEpisodeInfoAsync(Guid id)
        {
            var episodeDto = await _episodesService.GetEpisodeAsync(id);
            if (episodeDto != null)
                return Ok(episodeDto);
            else return NotFound();
        }
        [HttpPost("create")]
        public async Task<IActionResult> CreateEpisodeAsync([FromForm] string episode, [FromForm(Name = "audioFile")] IFormFile audioFile)
        {
            var episodeDto = JsonConvert.DeserializeObject<Episode>(episode);
            var creationResult = await _episodesService.CreateEpisodeAsync(episodeDto, audioFile);
            if (creationResult) return Ok();
            else return BadRequest();
        }

        //[HttpGet("{id}/audio")]
        //public IActionResult GetEpisodeAudio(Guid id)
        //{
        //    var filePath = $"{AppDomain.CurrentDomain.BaseDirectory}/episodes/{id}/1.mp3";
        //    episodeService.UpdateStats(id);
        //    // NOTE: because you're serving media files, you should specify the MIME type
        //    return PhysicalFile(filePath, "audio/mpeg");
        //}
    }
}
