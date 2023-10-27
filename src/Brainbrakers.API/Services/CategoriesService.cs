using Brainbrakers.API.Repository.Interfaces;
using Brainbrakers.API.Services.Interfaces;
using podcast_api.Data;

namespace podcast_api.Services
{
    public class CategoriesService : ICategoriesService
    {
        private readonly ICategoriesRepository _categoriesRepository;

        public CategoriesService(ICategoriesRepository categoriesRepository)
        {
            _categoriesRepository = categoriesRepository;
        }

        public async Task<List<string>> GetAllCategoriesAsync()
        {
            var categoriesList = await _categoriesRepository.GetAllCategoriesAsync();
            return categoriesList.Select(c => c.CategoryName).ToList();
        }

        public async Task<List<string>> GetAllKeywordsAsync()
        {
            var keywordsList = await _categoriesRepository.GetAllKeywordsAsync();
            return keywordsList.Select(c => c.Keyword).ToList();
        }
    }
}
