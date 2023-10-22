using Microsoft.AspNetCore.Http;

namespace podcast_api.Models
{
    public class UploadedEpisode
    {

        public IFormFile File { get; set; } 

    }
}
