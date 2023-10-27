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
        //CategoriesService categories = new CategoriesService();
        //[HttpGet("all")]
        //public IActionResult GetAllCategories()
        //{
        //    var result = categories.GetAllCategories();
        //    if (result != null) return Ok(result);
        //    else return NotFound();
        //}

        //[HttpGet("keywords")]
        //public IActionResult GetAllKeywords()
        //{
        //    var result = categories.GetAllKeywords();
        //    if (result != null) return Ok(result);
        //    else return NotFound();
        //}

    }
}
