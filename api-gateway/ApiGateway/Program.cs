using Ocelot.DependencyInjection;   // <-- importa
using Ocelot.Middleware;            // <-- importa
using Microsoft.Extensions.Configuration;

var builder = WebApplication.CreateBuilder(args);

// Lee la configuración de Ocelot
builder.Configuration
       .SetBasePath(builder.Environment.ContentRootPath)
       .AddJsonFile("ocelot.json", optional: false, reloadOnChange: true);

builder.Services.AddOcelot(builder.Configuration);

var app = builder.Build();

// Si quieres solo HTTP en desarrollo, deja comentada la redirección
// app.UseHttpsRedirection();

await app.UseOcelot();   // <-- await porque devuelve Task

app.Run();
