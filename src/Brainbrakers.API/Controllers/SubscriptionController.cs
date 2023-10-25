using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using podcast_api.Services;
using System.Diagnostics;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace podcast_api.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    public class SubscriptionController : ControllerBase
    {

        //SubscriptionService subs = new SubscriptionService();

        //// Auth methods.
        ///// <summary>
        ///// Подписывает пользователя на подкаст
        ///// </summary>
        ///// <param name="id">Идентификатор подкаста</param>
        //[HttpPost("subscribe")]
        //public IActionResult SubscribePodcastByID([FromBody] UserAndPodcast model)
        //{

        //    Debug.WriteLine($"User id: {model.UserId}\nPodcast id: {model.PodcastId}");

        //    var result = subs.Subscribe(model);
        //    if (result) return Ok();
        //    return BadRequest();
        //}/// <summary>
        // /// Включение/выключение уведомлений в подкасте
        // /// </summary>
        // /// <param name="id">Идентификатор подкаста</param>
        //[HttpPost("{id}/notification")]
        //public void UpdateNotificationByID(int id)
        //{

        //}

        ///// <summary>
        ///// Отписка от подкаста
        ///// </summary>
        ///// <param name="id">Идентификатор подкаста</param>
        //[HttpPost("unsubscribe")]
        //public IActionResult UnsubscribePodcastByID([FromBody] UserAndPodcast model)
        //{
        //    var result = subs.Unsubscribe(model);
        //    if (result)
        //    {
        //        return Ok();
        //    }
        //    else return BadRequest();
        //}

    }
    public class UserAndPodcast
    {
        public Guid UserId { get; set; }
        public Guid PodcastId { get; set; }
    }
}
