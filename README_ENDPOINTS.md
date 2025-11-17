# 📘 Endpoints y Flujos Clave – Kotlin App

Este documento resume los endpoints REST que consume la aplicación Kotlin y los pasos recomendados para ejecutar el flujo end‑to‑end contra los microservicios Spring Boot.

---

## 🚀 Pasos de Ejecución

1. **Levantar los microservicios**  
   ```bash
   cd Backend_Java_Spring/Fullstack_Ecommerce
   docker compose up -d
   ```
   - Verifica que todos los contenedores estén en `healthy` con `docker compose ps`.
   - Si necesitas logs puntuales, usa `docker compose logs -f msvc-<nombre>`.

2. **Configurar la app Kotlin**  
   ```bash
   cd Kotlin_app/levelup-backend
   ./gradlew clean assembleDebug
   ```
   - Para instalar en un emulador/dispositivo: `./gradlew installDebug`.
   - Si trabajas desde Android Studio basta con ejecutar **Run > Run 'app'**.

3. **Variables clave dentro de la app**  
   - `ApiConfig` inyecta automáticamente `X-API-Key`, `Authorization` y `X-User-Id` una vez que el usuario inicia sesión.
   - La sesión se persiste en `UserSessionDataStore` (tokens, id de usuario, región, etc.).

---

## 🔗 Endpoints por Módulo

### 🔐 LoginViewModel
| Endpoint | Método | Descripción | Microservicio |
|----------|--------|-------------|---------------|
| `/auth/login` | POST | Autenticación y obtención de tokens | `msvc-auth` |
| `/puntos/usuario/{id}/inicio-sesion` | POST | Bonificación diaria por inicio de sesión | `msvc-referidos` |

> Tras un login exitoso se ejecuta `saveUserSession`, se registran los tokens en `ApiConfig` y se dispara el flujo de puntos (`referidosService`).

### 🛒 ProductoRepository.kt
| Endpoint | Método | Uso |
|----------|--------|-----|
| `/productos` | GET | Catálogo general |
| `/productos/{id}` | GET | Ficha detallada |
| `/productos/categoria/{categoriaId}` | GET | Filtrado por categoría |
| `/productos/buscar?nombre=` | GET | Búsqueda por texto |
| `/productos/disponibles` | GET | Productos activos |
| `/resenia/producto/{productoId}` | GET | Reseñas asociadas |
| `/resenia/{id}` | DELETE | Eliminar reseña (cuando procede) |

> El mapeo de DTOs → modelos se centraliza en `ProductoMapper`. Las reseñas y productos relacionados se resuelven siempre vía microservicios (`msvc-productos` y `msvc-resenia`).

### 🧭 MainActivity & MainViewModel
- **MainActivity** inicializa los viewmodels principales y configura navegación. No consume endpoints directamente, pero orquesta:
  - `MainViewModel`: sincroniza sesión y avatar usando `GET /usuarios/perfil` (`msvc-usuario`).
  - `CarritoViewModel`: delega en `CarritoRepositoryRemote` (`/carrito/**` en `msvc-carrito`).
  - `ProfileViewModel`: gestiona perfil con `PUT /usuarios/perfil`, `DELETE /usuarios/{id}` y consultas a `referidosService`.
  - `UsuariosViewModel`: usa `GET /usuarios` y `DELETE /usuarios/{id}` para la vista admin.
  - `EventoViewModel`: mezcla `EventosApiService` (`/eventos/**`) y `ReferidosApiService` (`/puntos/usuario/{id}` y canjes).

> El flujo central es: tokens ↔ `ApiConfig` ↔ viewmodels. Cualquier logout limpia `ApiConfig.clear()` y `UserSessionDataStore`.

### 📦 Otros módulos relevantes
- **CarritoRepositoryRemote**: `GET/POST/PUT/DELETE /carrito/**` (`msvc-carrito`).
- **ProfileViewModel**: `GET /usuarios/perfil`, `PUT /usuarios/perfil`, `DELETE /usuarios/{id}`, `GET /referidos/codigo`, `GET /puntos/usuario/{id}`.
- **EventoViewModel**: `GET /eventos`, `GET /eventos/proximos`, `POST /puntos/usuario/{id}/canje-codigo`, `POST /puntos/usuario/{id}/canje`.

---

## ✅ Checklist de Consumo
- [x] Inicio de sesión + refresco de puntos (Auth + Referidos)
- [x] Catálogo, detalle y reseñas remotas (Productos + Reseñas)
- [x] Perfil y usuarios administrados vía `msvc-usuario`
- [x] Carrito 100% sincronizado con `msvc-carrito`
- [x] Eventos + gamificación conectados (`msvc-eventos` + `msvc-referidos`)

Si agregas un módulo nuevo, documenta su endpoint aquí para mantener la trazabilidad completa de la app móvil.  
¡Nivel-Up listo para la defensa y para despliegue en AWS cuando corresponda! 💪

