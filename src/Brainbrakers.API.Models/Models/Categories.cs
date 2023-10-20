
using System.ComponentModel.DataAnnotations;

namespace podcast_api.Models
{
    public class Categories
    {
        [Key]
        public int CategoryId { get; set; }
        public string CategoryName { get; set; }
        public virtual ICollection<Podcast> Podcasts { get; set; } = new List<Podcast>();
        public virtual ICollection<Episode> Episodes { get; set; } = new List<Episode>();
    }
}
