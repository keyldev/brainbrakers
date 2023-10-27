using Brainbrakers.API.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using podcast_api.Data;
using podcast_api.Models;

namespace Brainbrakers.API.Repository
{
    public class CategoriesRepository : ICategoriesRepository
    {
        private readonly ApplicationContext _dbContext;

        public CategoriesRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;

        }

        public async Task<List<Categories>> GetAllCategoriesAsync()
        {
            var categories = await _dbContext.Categories.ToListAsync();
            return categories;
        }

        public async Task<List<Keywords>> GetAllKeywordsAsync()
        {
            var keywords = await _dbContext.Keywords.ToListAsync();
            return keywords;
        }
    }
}
