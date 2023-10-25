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

        //[HttpGet("{id}/info")]
        //public IActionResult GetEpisodeInfoByID(Guid id)
        //{
        //    var result = episodeService.GetEpisodeInfo(id);
        //    if (result == null)
        //    {
        //        return NotFound();
        //    }
        //    else
        //    {
        //        return Ok(result);
        //    }
        //}

        //[HttpPost("create")]
        //public async Task<IActionResult> CreateEpisode([FromForm] string episode, [FromForm(Name = "audioFile")] IFormFile audioFile)
        //{
        //    Debug.WriteLine(episode);
        //    if (episode == null) Debug.WriteLine("Эпизода нема");
        //    var episodes = JsonConvert.DeserializeObject<Episode>(episode);

        //    Debug.WriteLine("File file file: " + audioFile.FileName + " \n  " + episodes.Title + " " + episodes.Id + "\n" + episodes.PodcastId);
        //    var result = await episodeService.UploadEpisodeOnServer(episodes, audioFile);
        //    if (result) return Ok();
        //    else return BadRequest();
        //}
        //[HttpGet("{id}/audio")]
        //public IActionResult GetEpisodeAudio(Guid id)
        //{
        //    var filePath = $"{AppDomain.CurrentDomain.BaseDirectory}/episodes/{id}/1.mp3";
        //    episodeService.UpdateStats(id);
        //    // NOTE: because you're serving media files, you should specify the MIME type
        //    return PhysicalFile(filePath, "audio/mpeg");
        //}


        //[HttpPost("create2")]
        //public async Task<IActionResult> CreateEpisode2([FromForm] Episode episode, [FromForm(Name = "audioFile")] IFormFile audioFile)
        //{
        //    if (audioFile == null || audioFile.Length == 0)
        //        return BadRequest("Audio file is not provided");

        //    // save the file to disk
        //    var filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "AudioFiles", audioFile.FileName);
        //    Directory.CreateDirectory(AppDomain.CurrentDomain.BaseDirectory + "/AudioFiles");
        //    using (var stream = new FileStream(filePath, FileMode.Create))
        //    {
        //        await audioFile.CopyToAsync(stream);
        //    }

        //    // save the episode to the database
        //    // use the episode object that is received in the form data
        //    // and the file path where the audio file is stored
        //    // to create a new episode record in the database

        //    return Ok();

        //}
        //// ????
        //[HttpPatch("{id}/update")]
        //public void UpdateEpisodeInfoByID(int id)
        //{

        //}

        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody] string value)
        //{

        //}

        //[HttpDelete("{id}/delete")]
        //public void DeleteEpisodeByID(int id)
        //{

        //}
    }
}
