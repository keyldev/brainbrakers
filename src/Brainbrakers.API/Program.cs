using Brainbrakers.API.Repository;
using Brainbrakers.API.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using podcast_api.Data;
using podcast_api.Services;
using System.Text;

namespace podcast_api
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);


            // Add services to the container.

            builder.Services.AddControllers();
            builder.Services.AddAuthorization(); // 



            builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme).AddJwtBearer(options =>
            {
                options.TokenValidationParameters = new Microsoft.IdentityModel.Tokens.TokenValidationParameters()
                {
                    ValidateIssuer = true, // 
                    ValidIssuer = builder.Configuration.GetSection("Jwt")["Issuer"], // 
                    ValidateAudience = true, // 
                    ValidAudience = builder.Configuration.GetSection("Jwt")["Audience"], // 
                    ValidateLifetime = true,  //
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration.GetSection("Jwt")["Key"])),
                    ValidateIssuerSigningKey = true, //
                    ClockSkew = TimeSpan.Zero // 
                };
            });

            builder.Services.AddScoped<ITokenService, TokenService>();
            builder.Services.AddScoped<IAuthRepository, AuthRepository>();

            builder.Services.AddScoped<IAuthService, AuthService>();
            builder.Services.AddScoped<IUserService, UserService>();


            builder.Services.AddDbContext<ApplicationContext>(options =>
                options.UseMySql(builder.Configuration.GetSection("ConnectionStrings")["DefaultConnection"], new MySqlServerVersion(new Version(8, 0, 32))));

            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();


            app.UseAuthorization();
            app.UseAuthentication();


            app.MapControllers();

            app.Run();
        }
    }
}