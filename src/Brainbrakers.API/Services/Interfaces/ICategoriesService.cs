using podcast_api.Models;

namespace Brainbrakers.API.Services.Interfaces
{
    public interface ICategoriesService
    {

        public Task<List<string>> GetAllCategoriesAsync();
        public Task<List<string>> GetAllKeywordsAsync();

    }
}
