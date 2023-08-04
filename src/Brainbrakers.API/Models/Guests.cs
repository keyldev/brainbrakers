using System.ComponentModel.DataAnnotations;

namespace podcast_api.Models
{
    public class Guests
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public virtual ICollection<Episode> Episodes { get; set; } = new List<Episode>();
    }
}
