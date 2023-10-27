using podcast_api.Models;

namespace Brainbrakers.API.Repository.Interfaces
{
    public interface ICategoriesRepository
    {
        public Task<List<Categories>> GetAllCategoriesAsync();
        public Task<List<Keywords>> GetAllKeywordsAsync();
    }
}
