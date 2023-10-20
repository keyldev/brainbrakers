using Brainbrakers.API.Services;
using podcast_api.Data;

namespace podcast_api.Services
{
    public class CategoriesService : ICategoriesService
    {

        public List<string> GetAllCategories()
        {
            using (ApplicationContext db = new ApplicationContext())
            {
                var categories = db.Categories.ToList();
                if (categories.Any())
                    return categories.Select(c => c.CategoryName).ToList();
                else return null;
            }

        }

        public List<string> GetAllKeywords()
        {
            using (ApplicationContext db = new ApplicationContext())
            {
                var keywords = db.Keywords.ToList();
                if (keywords.Any())
                    return keywords.Select(k => k.Keyword).ToList();
                else return null;
            }
        }


    }
}
