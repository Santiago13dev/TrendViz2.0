# TrendViz 2.0 🌎📈

Visualiza en tiempo real las noticias de Asia-Pacífico, con métricas de tendencias por país y un panel interactivo construido con Angular 20, Spring Boot 3.5, .NET 8 + Ocelot, MongoDB 6 e InfluxDB 1.8.

![TrendViz Banner](docs/assets/trendviz-banner.png)

[![CI](https://img.shields.io/badge/CI-passing-brightgreen)](#) [![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

---

## 📑 Tabla de contenidos

1. [🎯 Características](#-características)  
2. [🗺️ Arquitectura](#️-arquitectura)  
3. [⚡ Arranque rápido](#-arranque-rápido)  
4. [💻 Modo desarrollo](#-modo-desarrollo)  
5. [🛣️ Endpoints principales](#️-endpoints-principales)  
6. [📂 Estructura del repo](#-estructura-del-repo)  
7. [🙌 Contribuir](#-contribuir)  
8. [📜 Licencia](#-licencia)  
9. [🚀 Ideas futuras](#-ideas-futuras)  

---

## 🎯 Características

| Módulo               | Descripción                                                                                                                              |
|----------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| **scheduler-service**| Microservicio **Reactive Spring Boot 3.5** (Java 21) que cada hora consume NewsAPI, persiste artículos en MongoDB y métricas en InfluxDB. |
| **api-gateway**      | **.NET 8 + Ocelot 19** que expone la API unificada (`/conflicts/*`), aplica CORS y enruta a los microservicios internos.                 |
| **frontend**         | SPA **Angular 20** + **Chart.js 4** que lista artículos paginados y muestra un gráfico de barras por país. Incluye proxy de desarrollo.  |
| **infra**            | Scripts y contenedores Docker para MongoDB 6 e InfluxDB 1.8, listos con un solo comando.                                                  |

---

## 🗺️ Arquitectura

```mermaid
flowchart LR
  subgraph Frontend
    A[Angular 20<br/>http://localhost:4200] -- /conflicts/* --> G
  end

  subgraph Backend
    direction LR
    G[API Gateway<br/>.NET 8 + Ocelot<br/>http://localhost:5270 / https://localhost:7136] -- REST --> S[Scheduler-Service<br/>Spring Boot 3.5<br/>http://localhost:8081]
    S -->|writes| M[(MongoDB 6<br/>trendvizdb)]
    S -->|writes| I[(InfluxDB 1.8<br/>trendviz)]
  end
```

---

## ⚡ Arranque rápido

```bash
# 1. Clona el repo
git clone git@github.com:Santiago13dev/TrendViz2.0.git
cd TrendViz2.0

# 2. Levanta las bases de datos
docker run -d --name trendviz-mongo  -p 27017:27017 mongo:6.0
docker run -d --name trendviz-influx -p 8086:8086 influxdb:1.8

# 3. Backend
## 3.1 Scheduler (Java 21 + Maven)
cd scheduler-service/scheduler-service/scheduler-service
mvn spring-boot:run            # → http://localhost:8081

## 3.2 Gateway (.NET 8)
cd ../../../api-gateway/ApiGateway
dotnet run                     # → http://localhost:5270 / https://localhost:7136

# 4. Frontend
cd ../../frontend
npm ci                         # instala dependencias
ng serve --open                # → http://localhost:4200

# TIP ▸ proxy.conf.json redirige /conflicts/* al gateway (puerto 5270) evitando CORS.
```

---

## 💻 Modo desarrollo

| Módulo     | Lanzar                  | Pruebas                  |
|------------|------------------------|--------------------------|
| Scheduler  | `mvn spring-boot:run`  | `mvn test`               |
| Gateway    | `dotnet watch run`     | `dotnet test`            |
| Frontend   | `ng serve`             | `ng test` / `ng e2e`     |

Recarga en Java con Spring DevTools, en .NET con dotnet-watch y en Angular con Vite HMR.

---

## 🛣️ Endpoints principales

| Método | Endpoint                                         | Descripción                                 |
|--------|--------------------------------------------------|---------------------------------------------|
| GET    | `/conflicts/articles?page=0&size=5`              | Lista paginada de artículos                 |
| GET    | `/conflicts/count`                               | [{"country":"china","value":100}, …]        |
| GET    | `/actuator/health`                               | Health-check del scheduler-service          |
| GET    | `/swagger`                                       | Swagger UI en el API Gateway (próximamente) |

---

## 📂 Estructura del repo

```
TrendViz2.0
├── api-gateway/           # .NET 8 + Ocelot
├── scheduler-service/     # Spring Boot microservicio
├── frontend/              # Angular 20 SPA
│   ├── src/
│   ├── angular.json
│   └── proxy.conf.json
├── docs/                  # assets para el README / diagramas
└── docker-scripts/        # opcional: docker-compose, scripts
```

---

## 🙌 Contribuir

Haz fork y crea una rama:

```bash
git checkout -b feature/mi-nueva-funcionalidad
```

Sigue los style guides:

- Java → Google Java Style (Spotless)
- C# → dotnet-format (ver .editorconfig)
- TS/HTML/SCSS → Angular ESLint + Prettier

Haz commit con Conventional Commits:

```bash
git commit -m "✨ feat: descripción breve"
```

Abre un Pull Request contra master.

---

## 📜 Licencia

Distribuido bajo la MIT License.  
Consulta el fichero [LICENSE](LICENSE) para más detalles.

---

## 🚀 Ideas futuras

- Añadir docker-compose.yml con todos los servicios y bases de datos.
- Configurar GitHub Actions para CI/CD y actualizar los badges.
- Documentar en detalle la cron-job de NewsAPI (parámetros, filtros, frecuencia, etc.).

¡Disfruta desarrollando con TrendViz 2.0! 🎉
