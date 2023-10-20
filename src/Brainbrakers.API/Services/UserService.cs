using Brainbrakers.API.Services;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;
using System.Diagnostics;

namespace podcast_api.Services
{
    public class UserService : IUserService
    {
        public User GetUserByName(string username)
        {
            using (ApplicationContext db = new ApplicationContext())
            {
                if (username == null) return null;

                var user = db.Users.Where(u => username == u.Username).FirstOrDefault();
                return user;

            }
        }

        public User GetUserById(Guid id)
        {
            using ApplicationContext db = new ApplicationContext();
            var user = db.Users.FirstOrDefault(u => u.Id == id);
            return user;
        }

        public bool UpdateUserGenres(Guid id, string genres)
        {
            using (ApplicationContext db = new ApplicationContext())
            {
                var user = db.Users.FirstOrDefault(u=> u.Id == id);
                if (user != null)
                {
                    user.Genres = genres;
                    db.Users.Update(user);
                    db.SaveChanges();
                    return true;
                }

                return false;
            }
        }
        public bool UpdateUserInfo(User user)
        {

            using (ApplicationContext db = new ApplicationContext())
            {
                if (user == null) return false;
                bool isUserExists = db.Users.Any(u => u.Id == user.Id);
                if (isUserExists)
                {
                    db.Users.Update(user);
                    db.SaveChanges();
                    return true;
                }
                else return false;
            }
        }
        

        internal List<Podcast> GetMyPodcasts(Guid userid)
        {
            using (ApplicationContext db = new ApplicationContext())
            {
                var userPodcasts = db.Users.FirstOrDefault(u => u.Id == userid);
                if (userPodcasts != null)
                {
                    var podcastIds = db.PodcastAuthors.Where(pa => pa.UserId == userid)
                        .Select(pa => pa.PodcastId).ToList();
                    var podcasts = db.Podcasts.Where(p => podcastIds.Contains(p.Id)).ToList();

                    Debug.WriteLine($"Podcasts is {podcastIds.Count} {podcasts.Count}");

                    return podcasts;
                }



                return null;

            }
        }
    }
}
