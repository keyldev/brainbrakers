using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace podcast_api.Models
{
    public class User
    {
        [Key]
        public Guid Id { get; set; }
        public string Username { get; set; } = "";
        [EmailAddress]
        public string Email { get; set; } = "";
        public string Password { get; set; } = "";
        public string FirstName { get; set; } = "";
        public string LastName { get; set; } = "";
        public DateTime BirthDate { get; set; }
        public string Biography { get; set; } = "";
        
        public virtual RefreshToken? RefreshToken { get; set; }
        //public virtual ICollection<Podcast> MyPodcasts { get; set; } = new List<Podcast>();
        public virtual ICollection<PodcastAuthors> MyPodcasts { get; set; } = new List<PodcastAuthors>();
        public virtual ICollection<Subscription> SubscribedPodcasts { get; set; } = new List<Subscription>();
        public virtual ICollection<FavoriteEpisodes> FavoriteEpisodes { get; set; } = new List<FavoriteEpisodes>();

        public string Genres { get; set; } = ""; // жанры подкастов 
        public string Interests { get; set; } = "";// интересы
        public string Language { get; set; } = "En"; // переделать в структуру.
        public string AvatarURL { get; set; } = "";
        public DateTime CreationDate { get; set; }
        public bool IsConfirmed { get; set; } = false; // подтвержден ли (почта ?)
        public bool IsCreator { get; set; } = false; // ??? или вынести в роли
        public Guid? RefreshTokenId { get; set; }


    }

}
