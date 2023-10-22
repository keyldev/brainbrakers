using Brainbrakers.API.Services;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;
using System.Diagnostics;

namespace podcast_api.Services
{
    public class PodcastService : IPodcastService
    {
        public string CreatePodcast(Podcast podcast)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var podcastEx = db.Podcasts.FirstOrDefault(p => p.Id == podcast.Id); // ?? или по id

            //    if (podcastEx != null)
            //    {
            //        return null;
            //    }
            //    else
            //    {
            //        podcast.ReleaseDate = DateTime.Now;
            //        db.Podcasts.Add(podcast);
            //        //db.Episodes.AddRange(podcast.Episodes);

            //        db.SaveChanges();
            //        return podcast.Id.ToString();
            //    }
            //}
            return null;
        }

        public List<Podcast> GetAllPodcasts()
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var podcasts = db.Podcasts.ToList();
            //    return podcasts;
            //}
            return null;
        }

        public List<User> GetSubscribers(Guid podcastId)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var subscribers = db.Subscription
            //        .Where(s => s.PodcastId == podcastId)
            //        .Include(s => s.User)
            //        .Select(s => s.User)
            //        .ToList();
            //    return subscribers;
            //}
            return null;
        }

        public List<User> GetPodcastAuthors(Guid podcastId)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var authors = db.PodcastAuthors.Where(pa => pa.PodcastId == podcastId).Select(u => u.UserId).ToList();
            //    var users = db.Users.Where(u => authors.Contains(u.Id)).ToList();
            //    return users;
            //}
            return null;
        }

        public Podcast? GetPodcastInfo(Guid id)
        {
            //using ApplicationContext db = new ApplicationContext();
            //var podcast = db.Podcasts.FirstOrDefault(p => p.Id == id);
            //return podcast;
            return null;
        }

        internal List<Episode> GetEpisodesByPodcastId(Guid id)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var episodes = db.Episodes.Where(e => e.PodcastId == id);
            //    return episodes.ToList();
            //}
            return null;
        }

        public List<Podcast> GetPodcastByName(string name)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var podcasts = db.Podcasts.Where(p => p.Title.Contains(name)).ToList();
            //    return podcasts;
            //}
            return null;
        }

        public bool Test(Guid id, string genres)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var user = db.Users.FirstOrDefault(u => u.Id == id);
            //    if (user != null)
            //    {
            //        user.Genres = genres;
            //        db.Users.Update(user);
            //        db.SaveChanges();
            //        return true;
            //    }

            //    return false;
            //}
            return false;
        }

        public List<Podcast> GetNewPodcasts()
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    return db.Podcasts.OrderBy(p => p.ReleaseDate.Date.Day).ToList();
            //}
            return null;
        }

        public bool UpdatePodcast(Podcast podcast)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var cast = db.Podcasts.Select(p => p.Id == podcast.Id);
            //    if (cast != null)
            //    {
            //        db.Podcasts.Update(podcast);
            //        db.SaveChanges();
            //        return true;
            //    }
            //    else return false;
            //}
            return false;
        }

        public List<PodcastStat>? GetPodcastStats(Guid id)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var podcast = db.Podcasts.FirstOrDefault(p => p.Id == id);
            //    if (podcast != null)
            //    {
            //        var stat = db.PodcastStats.Where(p => p.PodcastId == id).ToList();
            //        return stat;
            //    }
            //    else return null;
            //}
            return null;
        }

        public List<Podcast> GetTrendingAuthors()
        {
            //using ApplicationContext db = new ApplicationContext();
            //var authorsIds = db.PodcastStats.Where(ps => ps.ListenerCount > 5).Select(ps => ps.PodcastId).ToList();
            //var podcasts = db.Podcasts.Where(p => authorsIds.Contains(p.Id)).ToList();
            //return podcasts;
            return null;
        }

        public List<Podcast>? FindPodcast(string text)
        {
            //using ApplicationContext db = new ApplicationContext();
            //var podcasts = db.Podcasts.Where(p => p.Title.Contains(text) || p.Description.Contains(text)).ToList();
            //return podcasts;
            return null;
        }
    }
}