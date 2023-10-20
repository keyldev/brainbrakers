using Brainbrakers.API.Services;
using podcast_api.Data;
using podcast_api.Models;

namespace podcast_api.Services
{
    public class EpisodeService : IEpisodeService
    {
        public async Task<bool> UploadEpisodeOnServer(Episode episode, IFormFile request)
        {

            using (ApplicationContext db = new ApplicationContext())
            {

                var ep = db.Episodes.Any(e => episode.Title == e.Title);
                if (ep)
                    return false;
                else
                {
                    // проверка на вес файла, ограничить макс вес, к примеру, 256 мегабайтами.
                    var file = request;
                    if (file == null) return false;
                    var uploadPath = AppDomain.CurrentDomain.BaseDirectory + "/episodes/" + episode.Id;
                    Directory.CreateDirectory(uploadPath);
                    string fullPath = $"{uploadPath}/{episode.EpisodeNumber}.mp3";
                    episode.AudioURL = fullPath ?? "";


                    using (var fileStream = new FileStream(fullPath, FileMode.Create))
                    {
                        await file.CopyToAsync(fileStream);
                    }
                    db.Episodes.Add(episode);
                    db.SaveChanges();
                    return true;
                }
            }
            
        }
  
        public Episode? GetEpisodeInfo(Guid id)
        {
            using (var db = new ApplicationContext())
            {
                var podcast = db.Episodes.FirstOrDefault(e => e.Id == id);
                return podcast;
            }
        }

        public void UpdateStats(Guid id)
        {
            using ApplicationContext db = new ApplicationContext();
            var podcastId = db.Episodes.FirstOrDefault(e => e.Id == id).PodcastId;
            var stats = db.PodcastStats.OrderByDescending(s => s.Date).FirstOrDefault();
            if (stats != null)
            {
                db.PodcastStats.Add(new PodcastStat()
                {
                    Date = DateTime.Now,
                    ListenerCount = stats.ListenerCount + 1,
                    PodcastId = stats.PodcastId,
                    Rating = stats.Rating,
                    SubscriberCount = stats.SubscriberCount
                });
                
            }
            else
            {
                db.PodcastStats.Add(new PodcastStat()
                {
                    Date = DateTime.Now,
                    ListenerCount = 0 + 1,
                    PodcastId = podcastId,
                    Rating = 7.7,
                    SubscriberCount = 0
                });
            }
            db.SaveChanges();
        }
    }
}

