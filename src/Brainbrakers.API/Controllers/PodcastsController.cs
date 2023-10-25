using Microsoft.AspNetCore.Mvc;
using podcast_api.Models;
using podcast_api.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace podcast_api.Controllers
{
    /*
     -подкасты все
    - подкасты от одного артиста
    - ещё че нибудь
     
     */

    [Route("api/v1/[controller]")]
    [ApiController]
    public class PodcastsController : ControllerBase
    {
        //PodcastService podcasts = new PodcastService(); // change to middleware?

        //[HttpGet("all")]
        //public List<Podcast> GetAllPodcasts()
        //{
        //    return podcasts.GetAllPodcasts();
        //}

        //[HttpGet("{id}/subscribers")]
        //public Podcast GetSubs(Guid id)
        //{
        //    return podcasts.GetPodcastInfo(id);
        //}

        //[HttpGet("{id}/authors")]
        //public IActionResult GetAuthors(Guid id)
        //{
        //    var result = podcasts.GetPodcastAuthors(id);
        //    if (result != null) return Ok(result);
        //    else return BadRequest();
        //}

        //[HttpGet("find/{name}")]
        //public IActionResult FindPodcastByName(string name)
        //{
        //    var result = podcasts.GetPodcastByName(name);
        //    if (result != null) return Ok(result);
        //    else return BadRequest();
        //}

        //[HttpPost("genres")]
        //public IActionResult UpdateGenres([FromBody] GenresModel genres)
        //{
        //    var result = podcasts.Test(genres.id, genres.genres);
        //    if (result) return Ok(result);
        //    else return BadRequest();
        //}

        //[HttpGet("sort")]
        //public IActionResult GetNewPodcasts()
        //{
        //    var result = podcasts.GetNewPodcasts();
        //    if (result != null && result.Any()) return Ok(result);
        //    else return NotFound();
        //}
        //public class GenresModel
        //{
        //    public Guid id { get; set; }
        //    public String genres { get; set; }
            
        //}

        //// DELETE api/<PodcastsController>/5
        //[HttpDelete("{id}")]
        //public void Delete(int id)
        //{
        //}

        //[HttpGet("{id}/audio")]
        //public IActionResult GetEpisodeAudio(Guid id)
        //{
        //    var filePath = $"{AppDomain.CurrentDomain.BaseDirectory}/episodes/{id}/1.mp3";
        //    // NOTE: because you're serving media files, you should specify the MIME type
        //    return PhysicalFile(filePath, "audio/mpeg");
        //}
    }
}