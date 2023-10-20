using System.ComponentModel.DataAnnotations;
namespace podcast_api.Models
{
    public class Keywords
    {
        [Key]
        public int Id { get; set; }
        public string Keyword { get; set; }
        public virtual ICollection<Podcast> Podcasts { get; set; } = new List<Podcast>();
        public virtual ICollection<Episode> Episodes { get; set; } = new List<Episode>();
    }
}
