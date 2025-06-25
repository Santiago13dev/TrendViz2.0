# TrendViz 2.0 🌎📈

Visualiza en tiempo real las noticias de Asia-Pacífico, con métricas de tendencias por país y un panel interactivo construido con Angular 20, Spring Boot 3.5, .NET 8 + Ocelot, MongoDB 6 e InfluxDB 1.8.

![TrendViz Banner](docs/assets/trendviz-banner.png)

[![CI](https://img.shields.io/badge/CI-passing-brightgreen)](#) [![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

---

## 📑 Tabla de contenidos
- 🎯 [Características](#-características)  
- 🗺️ [Arquitectura](#️-arquitectura)  
- ⚡ [Arranque rápido](#-arranque-rápido)  
- 💻 [Modo desarrollo](#-modo-desarrollo)  
- 🛣️ [Endpoints principales](#️-endpoints-principales)  
- 📂 [Estructura del repo](#-estructura-del-repo)  
- 🙌 [Contribuir](#-contribuir)  
- 📜 [Licencia](#-licencia)  
- 🚀 [Ideas futuras](#-ideas-futuras)  

---

## 🎯 Características

| Módulo              | Descripción                                                                                               |
|---------------------|-----------------------------------------------------------------------------------------------------------|
| **scheduler-service** | Microservicio Reactive Spring Boot 3.5 (Java 21) que cada hora consume NewsAPI, persiste en MongoDB e InfluxDB. |
| **api-gateway**       | Gateway .NET 8 + Ocelot que expone `/conflicts/**` y aplica CORS + forwarding a microservicios internos.   |
| **frontend**          | SPA Angular 20 con NgCharts + Chart.js 4: lista paginada de artículos y gráfico de barras por país.       |
| **infra**             | Contenedores Docker para MongoDB 6 e InfluxDB 1.8 listos en un solo comando.                               |

---

## 🗺️ Arquitectura

```mermaid
flowchart LR
  subgraph Frontend
    A[Angular 20<br/>http://localhost:4200] -->|/conflicts/*| G
  end

  subgraph Backend
    direction LR
    G[API Gateway<br/>.NET 8 + Ocelot<br/>:5270 / :7136] -->|REST| S[Scheduler-Service<br/>Spring Boot 3.5<br/>:8081]
    S -->|writes| M[(MongoDB 6<br/>trendvizdb)]
    S -->|writes| I[(InfluxDB 1.8<br/>trendviz)]
  end
⚡ Arranque rápido
bash
Copiar
Editar
# 1. Clona el repo
git clone git@github.com:<TU_USUARIO>/TrendViz2.0.git
cd TrendViz2.0

# 2. Lanza las bases de datos
docker run -d --name trendviz-mongo  -p 27017:27017 mongo:6.0
docker run -d --name trendviz-influx -p 8086:8086 influxdb:1.8

# 3. Backend
## 3.1 Scheduler (Java 21 + Maven 3.9)
cd scheduler-service/scheduler-service/scheduler-service
mvn spring-boot:run            # → http://localhost:8081

## 3.2 Gateway (.NET 8)
cd ../../../api-gateway/ApiGateway
dotnet run                     # → http://localhost:5270 (http) / 7136 (https)

# 4. Frontend
cd ../../frontend
npm ci                         # instala dependencias
ng serve --open                # abre http://localhost:4200

# TIP
El proxy.conf.json redirige `/conflicts/*` al puerto 5270 evitando CORS.
💻 Modo desarrollo
Módulo	Lanzar	Pruebas
Scheduler	mvn spring-boot:run	mvn test
Gateway	dotnet watch run	dotnet test
Frontend	ng serve	ng test / ng e2e

Cambios en Java recargan con Spring DevTools, en .NET con dotnet-watch y en Angular con Vite HMR.

🛣️ Endpoints principales
Método	Endpoint	Descripción
GET	/conflicts/articles?page=0&size=5	Lista paginada de artículos
GET	/conflicts/count	[ { "country":"china", "value":100 }, … ]
GET	/actuator/health	Health-check Spring
GET	/swagger	Próximamente en Gateway

📂 Estructura del repo
text
Copiar
Editar
TrendViz2.0
├── api-gateway/           # .NET 8 + Ocelot
├── scheduler-service/     # Spring Boot microservicio
├── frontend/              # Angular 20 SPA
│   ├── src/
│   ├── angular.json
│   └── proxy.conf.json
├── docs/                  # assets para el README / diagramas
└── docker-scripts/        # opcional: docker-compose y scripts
🙌 Contribuir
Fork el repositorio y crea una rama:

bash
Copiar
Editar
git checkout -b feature/mi-nueva-funcionalidad
Sigue los style-guides:

Java → Google Java Style (Spotless)

C# → dotnet format (ver .editorconfig)

TS/HTML/SCSS → Angular ESLint + Prettier

Haz commit con un mensaje claro:

bash
Copiar
Editar
git commit -m "✨ feat: breve descripción"
git push origin feature/mi-nueva-funcionalidad
Abre un Pull Request contra master.

📜 Licencia
Distribuido bajo la MIT License. Consulta LICENSE para más detalles.

🚀 Ideas futuras
Añadir un docker-compose.yml con los tres servicios + las bases de datos.

Configurar GitHub Actions para CI/CD y actualizar los badges.

Detallar la cron-job de NewsAPI (parámetros, filtros, frecuencia, etc.).

¡Disfruta desarrollando con TrendViz 2.0! 🎉
