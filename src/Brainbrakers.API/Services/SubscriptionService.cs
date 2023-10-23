using Brainbrakers.API.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using podcast_api.Controllers;
using podcast_api.Data;
using podcast_api.Models;
using System.Diagnostics;

namespace podcast_api.Services
{
    public class SubscriptionService : ISubscriptionService
    {
        internal bool Subscribe(UserAndPodcast model)
        {
            /*using (ApplicationContext db = new ApplicationContext())
            {
                var userId = model.UserId;
                var podcastId = model.PodcastId;

                var user = db.Users.FirstOrDefault(u => u.Id == userId);
                var podcast = db.Podcasts.FirstOrDefault(p => p.Id == podcastId);
                var stats = db.PodcastStats.OrderByDescending(ps => ps.Date).FirstOrDefault();
                if (user != null)
                {
                    user.SubscribedPodcasts.Add(new Models.Subscription
                    {
                        UserId = userId,
                        User = user,
                        Podcast = podcast,
                        PodcastId = podcastId
                    });

                    if (stats != null)
                    {
                        db.PodcastStats.Add(new PodcastStat()
                        {
                            PodcastId = stats.PodcastId,
                            Date = DateTime.Now,
                            ListenerCount = stats.ListenerCount, // change to
                            SubscriberCount = stats.SubscriberCount + 1,
                            Rating = stats.Rating // change to
                        });
                    }
                    else
                    {
                        db.PodcastStats.Add(new PodcastStat()
                        {
                            PodcastId = podcastId,
                            Date = DateTime.Now,
                            ListenerCount = 1, // change to
                            SubscriberCount = 0 + 1,
                            Rating = 0 + 1 // change to
                        });
                    }

                    db.Users.Update(user);
                    db.SaveChanges();
                    return true;
                }

                return false;
            }*/
            return false;
        }

        public List<Podcast> GetSubscribedPodcasts(Guid userId)
        {
            //using (ApplicationContext db = new ApplicationContext())
            //{
            //    var subscribedPodcasts = db.Subscription
            //        .Where(s => s.UserId == userId)
            //        .Include(s => s.Podcast)
            //        .Select(s => s.Podcast)
            //        .ToList();
            //    return subscribedPodcasts;
            //}
            return null;
        }

        internal bool Unsubscribe(UserAndPodcast model)
        {
            /*using (ApplicationContext db = new ApplicationContext())
            {
                var userId = model.UserId;
                var podcastId = model.PodcastId;

                var user = db.Users.FirstOrDefault(u => u.Id == userId);
                var podcast = db.Podcasts.FirstOrDefault(p => p.Id == podcastId);
                var stats = db.PodcastStats.OrderByDescending(ps => ps.Date).FirstOrDefault();
                if (user != null && podcast != null)
                {
                    var subs = db.Subscription.FirstOrDefault(s => s.PodcastId == podcastId && s.UserId == userId);
                    if (stats != null)
                    {
                        
                        db.PodcastStats.Add(new PodcastStat()
                        {
                            PodcastId = stats.PodcastId,
                            Date = DateTime.Now,
                            ListenerCount = stats.ListenerCount, // change to
                            SubscriberCount = stats.SubscriberCount - 1,
                            Rating = stats.Rating // change to
                        });
                    }

                    if (subs != null)
                    {
                        Debug.WriteLine($"Sub id: {subs.Id}: user id {subs.UserId} : podcast id {subs.PodcastId}");

                        db.Subscription.Remove(subs);
                    }


                    db.SaveChanges();
                    return true;
                }

                return false;
            }*/
            return false;
        }
        /*internal List<Podcast> GetPodcastsByUserId(Guid userId)
{
   using (ApplicationContext db = new ApplicationContext())
   {
       Debug.WriteLine($"Getting podcasts for userId: {userId}");

       var userSubscriptions = db.Subscription.Where(s => s.UserId == userId).ToList();


       if (userSubscriptions != null)
       {

           Debug.WriteLine($"asd {userSubscriptions[0].UserId} {userSubscriptions[0].Podcast is null}");

           return userSubscriptions.Select(sub => sub.Podcast).ToList();
       }
       return null;
   }
}*/
    }
}