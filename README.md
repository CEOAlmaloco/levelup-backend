# LevelUp - App Android Gamer

App móvil para la comunidad gamer chilena. Permite explorar productos gaming, participar en eventos locales y acumular puntos.

## Funcionalidades

- Catálogo de productos con filtros
- Sistema de reviews y calificaciones
- Eventos locales con mapas
- Sistema de puntos y referidos
- Perfil de usuario editable
- Carrito de compras

## Stack Tecnológico

- Kotlin + Jetpack Compose
- Retrofit para APIs
- Material Design 3
- Coroutines + StateFlow
- OSMDroid para mapas
- Coil para imágenes

## Setup

1. Clonar repo
2. Abrir en Android Studio
3. Copiar `config/api-config.sample.properties` a `config/api-config.properties`
4. Configurar URLs del backend en `api-config.properties`
5. Sync Gradle

## Endpoints

La app consume microservicios Spring Boot:

- **Auth**: `/auth/login`, `/auth/register`
- **Productos**: `/productos`, `/productos/{id}`
- **Eventos**: `/eventos`, `/eventos/proximos`
- **Usuarios**: `/usuarios/perfil`, `/usuarios`
- **Carrito**: `/carrito/**`
- **Referidos**: `/puntos/**`, `/referidos/**`

Ver `README_ENDPOINTS.md` para detalles completos.

## Tests

Ejecutar tests:
```bash
./gradlew test
```

Tests implementados:
- Model tests (UsuarioValidator, LoginValidator, Producto, Carrito)
- ViewModel tests (ProductoViewModel)
- Repository tests (ProductoRepository)

## Build APK

```bash
./gradlew assembleRelease
```

El APK estará en `app/build/outputs/apk/release/app-release.apk`

## Integrantes

[Agregar nombres aquí]

## Licencia

Proyecto académico - DSY1105
