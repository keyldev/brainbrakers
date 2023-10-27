using Brainbrakers.API.Services.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using podcast_api.Services;

namespace podcast_api.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class SubscriptionsController : ControllerBase
    {
        private readonly ISubscriptionService _subsService;
        public SubscriptionsController(ISubscriptionService subsService)
        {
            _subsService = subsService;
        }

        //SubscriptionService subs = new SubscriptionService();

        //// authentication
        //[HttpGet("{id}/all")]
        //public IActionResult GetUserSubscriptions(Guid id) // здесь через JWT UserID
        //{
        //    var result = subs.GetSubscribedPodcasts(id);

        //    return Ok(result);
        //}

        //[HttpDelete("unsubscribe/all")]
        //public void UnsubscribeAllPodcasts()
        //{

        //}

    }
}
