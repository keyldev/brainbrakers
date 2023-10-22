using Microsoft.EntityFrameworkCore;
using podcast_api.Models;

namespace podcast_api.Data
{
    public class ApplicationContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<RefreshToken> RefreshToken { get; set; }
        public DbSet<Podcast> Podcasts { get; set; }
        public DbSet<Episode> Episodes { get; set; }
        public DbSet<Categories> Categories { get; set; }
        public DbSet<FavoriteEpisodes> FavoriteEpisodes { get; set; }
        public DbSet<Guests> Guests { get; set; }
        public DbSet<Hosts> Hosts { get; set; }
        public DbSet<Keywords> Keywords { get; set; }
        public DbSet<Subscription> Subscription { get; set; }
        public DbSet<PodcastAuthors> PodcastAuthors { get; set; }
        public DbSet<PodcastStat> PodcastStats { get; set; }

        public ApplicationContext(DbContextOptions<ApplicationContext> options) : base(options)
        {
            
        }

        //protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        //{
        //    optionsBuilder.UseMySql("server=localhost;user=root;password=keyldev;database=podcastsdb", new MySqlServerVersion(new Version(8, 0, 32)));
        //}
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<PodcastAuthors>().HasKey(p => new { p.PodcastId, p.UserId });
            modelBuilder.Entity<PodcastAuthors>().HasOne(pa => pa.Podcast).WithMany(p => p.Authors).HasForeignKey(p => p.PodcastId);
            modelBuilder.Entity<PodcastAuthors>().HasOne(pa => pa.User).WithMany(p => p.MyPodcasts).HasForeignKey(p => p.UserId);

            modelBuilder.Entity<User>().HasOne(u => u.RefreshToken).WithOne(r => r.User).HasForeignKey<RefreshToken>(rt => rt.UserId);

            modelBuilder.Entity<User>().HasMany(u => u.FavoriteEpisodes).WithOne(e => e.User).HasForeignKey(ue => ue.UserId);
            modelBuilder.Entity<Episode>().HasMany(e => e.FavoriteEpisodes).WithOne(ue => ue.Episode).HasForeignKey(ue => ue.EpisodeId);
            modelBuilder.Entity<FavoriteEpisodes>().HasKey(e => new { e.UserId, e.EpisodeId });

            modelBuilder.Entity<Subscription>().HasKey(s => new { s.UserId, s.PodcastId });
            modelBuilder.Entity<Subscription>().HasOne(s => s.User).WithMany(u => u.SubscribedPodcasts).HasForeignKey(s => s.UserId);
            modelBuilder.Entity<Subscription>().HasOne(s => s.Podcast).WithMany(p => p.Subscribers).HasForeignKey(s => s.PodcastId);
        }

    }
}
