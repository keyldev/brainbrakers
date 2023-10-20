namespace podcast_api.Models
{
    public class RefreshToken
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public string TokenString { get; set; }
        public DateTime ExpiryTime { get; set; }
        public virtual User User { get; set; }

    }
}
