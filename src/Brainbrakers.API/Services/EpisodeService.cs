using Brainbrakers.API.Repository.Interfaces;
using Brainbrakers.API.Services.Interfaces;
using podcast_api.Data;
using podcast_api.Models;

namespace podcast_api.Services
{
    public class EpisodeService : IEpisodeService
    {
        private readonly IEpisodesRepository _episodesRepository;
        public EpisodeService(IEpisodesRepository episodesRepository)
        {
            _episodesRepository = episodesRepository;
        }

        public Episode? GetEpisodeInfo(Guid id)
        {
            //using (var db = new ApplicationContext())
            //{
            //    var podcast = db.Episodes.FirstOrDefault(e => e.Id == id);
            //    return podcast;
            //}
            return null;
        }
        public async Task<Episode> GetEpisodeAsync(Guid id)
        {
            var episodeDto = await _episodesRepository.GetEpisodeAsync(id);
            return episodeDto;
        }

        public async Task<bool> CreateEpisodeAsync(Episode episode, IFormFile audioFile)
        {
            var isEpisodeExists = await _episodesRepository.GetEpisodeByTitleAsync(episode.Title);
            if (isEpisodeExists != null)
                return false; // if episode with the same id exists - we need to say error cause.
            else
            {
                // refactor this sh**
                
                if (audioFile == null) return false;
                var uploadPath = AppDomain.CurrentDomain.BaseDirectory + "/episodes/" + episode.Id;
                Directory.CreateDirectory(uploadPath);
                string fullPath = $"{uploadPath}/{episode.EpisodeNumber}.mp3";
                episode.AudioURL = fullPath;
                
                using(var fileStream = new FileStream(fullPath, FileMode.Create))
                {
                    await audioFile.CopyToAsync(fileStream);
                }
                var episodeCreationResult = await _episodesRepository.CreateEpisodeAsync(episode);
                return episodeCreationResult;
            }
        }
        public void UpdateStats(Guid id)
        {
            //using ApplicationContext db = new ApplicationContext();
            //var podcastId = db.Episodes.FirstOrDefault(e => e.Id == id).PodcastId;
            //var stats = db.PodcastStats.OrderByDescending(s => s.Date).FirstOrDefault();
            //if (stats != null)
            //{
            //    db.PodcastStats.Add(new PodcastStat()
            //    {
            //        Date = DateTime.Now,
            //        ListenerCount = stats.ListenerCount + 1,
            //        PodcastId = stats.PodcastId,
            //        Rating = stats.Rating,
            //        SubscriberCount = stats.SubscriberCount
            //    });
                
            //}
            //else
            //{
            //    db.PodcastStats.Add(new PodcastStat()
            //    {
            //        Date = DateTime.Now,
            //        ListenerCount = 0 + 1,
            //        PodcastId = podcastId,
            //        Rating = 7.7,
            //        SubscriberCount = 0
            //    });
            //}
            //db.SaveChanges();
        }

    }
}

