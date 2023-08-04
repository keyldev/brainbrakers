using System.ComponentModel.DataAnnotations;

namespace podcast_api.Models
{
    public class RegisterRequest
    {

        public string Username { get; set; }
        [EmailAddress]
        public string Email { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }

    }
}
