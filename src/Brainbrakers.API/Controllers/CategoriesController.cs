using Brainbrakers.API.Services.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using podcast_api.Services;

namespace podcast_api.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly ICategoriesService _categoriesService;
        public CategoriesController(ICategoriesService categoriesService)
        {
            _categoriesService = categoriesService;
        }
        [HttpGet("all")]
        public async Task<IActionResult> GetAllCategoriesAsync()
        {
            var categoriesList = await _categoriesService.GetAllCategoriesAsync();
            if (categoriesList == null)
            {
                return NotFound();
            }
            else return Ok(categoriesList);
        }
        [HttpGet("keywords")]
        public async Task<IActionResult> GetAllKeywordsAsync()
        {
            var keywordsList = await _categoriesService.GetAllKeywordsAsync();
            if (keywordsList == null)
            {
                return NotFound();
            }
            else return Ok(keywordsList);
        }

    }
}
