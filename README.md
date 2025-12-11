# LevelUp Gamer · App Android (Versión 2.0)

App móvil multiplataforma (Kotlin + Jetpack Compose) que centraliza catálogo gamer, blogs, eventos y programas de fidelización para la comunidad LevelUp. Esta versión 2.0 consolida la integración con todos los microservicios Spring Boot y añade automatización de pruebas, build firmado y documentación de despliegue.

---

## 👥 Integrantes

| Rol | Integrante |
| --- | ---------- |
| Líder mobile / QA | _(Completar)_ |
| Líder backend / DevOps | _(Completar)_ |

> Actualiza esta tabla antes de la entrega oficial.

---

## 🚀 Funcionalidades clave

- Catálogo completo con filtros, destacados y detalle con carrusel S3.
- Carrito sincronizado con `msvc-carrito`, lógica de incrementos y checkout.
- Eventos y blogs publicados desde `msvc-eventos` y `msvc-contenido`.
- Programa de puntos / referidos (`msvc-referidos`, `msvc-usuario`).
- Login / registro con `msvc-auth` + `msvc-usuario`.
- API externa: **OpenStreetMap (OSMDroid)** para mapas de eventos y **AWS S3** para assets (logo, carrusel, productos, blog).

---

## 🧱 Arquitectura & Stack

- **Frontend mobile**: Kotlin, Jetpack Compose, Material 3, Coroutines/StateFlow, Coil, OSMDroid.
- **Networking**: Retrofit + OkHttp (interceptores de auth, reintentos, logs).
- **Backend**: Microservicios Spring Boot (auth, productos, carrito, eventos, contenido, usuarios, referidos, etc.) corriendo en AWS EC2 + RDS PostgreSQL 15.x.
- **Infra**: API Gateway, S3, RDS, scripts `build-all-services` / `start-all-services`.

Para ver los endpoints detallados visita [`README_ENDPOINTS.md`](README_ENDPOINTS.md).

---

## ⚙️ Configuración local

1. **Clonar** este repo y los microservicios (`Backend_Java_Spring/Fullstack_Ecommerce`).
2. **Configurar** `config/api-config.properties`:
   ```properties
   gateway.url.debug=http://10.0.2.2:8094/
   gateway.url.device=http://192.168.1.100:8094/
   gateway.url.release=http://98.83.239.227:8094/
   carrito.url.debug=http://10.0.2.2:8008/
   carrito.url.device=http://192.168.1.100:8008/
   carrito.url.release=http://98.83.239.227:8094/
   gateway.api.key=levelup-2024-secret-api-key-change-in-production
   media.base.url=https://levelup-gamer-products.s3.us-east-1.amazonaws.com/
   ```
3. **Backend**: usar `build-all-services.(sh|bat)` y `start-all-services.(sh|bat)` o levantar cada microservicio con `mvn spring-boot:run`.
4. **App**:
   ```bash
   ./gradlew :app:clean :app:assembleDebug
   ```
5. **Login de prueba**: usar el usuario sembrado en `msvc-usuario` o registra uno nuevo desde la app.

---

## 📡 Endpoints consumidos

| Dominio | Endpoints principales |
| ------- | --------------------- |
| Auth (`msvc-auth`) | `POST /auth/login`, `POST /auth/register`, `POST /auth/logout` |
| Usuarios (`msvc-usuario`) | `GET/PUT /usuarios/perfil`, `POST /usuarios`, `GET /usuarios/{id}` |
| Productos (`msvc-productos`) | `GET /productos`, `/productos/{id}`, `/productos/carrusel`, `/productos/logo` |
| Carrito (`msvc-carrito`) | `GET /carrito/activo`, `POST /carrito/items`, `PUT /carrito/items/{id}`, `DELETE /carrito/items/{id}`, `DELETE /carrito/vaciar` |
| Contenido (`msvc-contenido`) | `GET /contenido/articulos/publicados`, `/destacados`, `/categoria/{cat}` |
| Eventos (`msvc-eventos`) | `GET /eventos`, `/proximos`, `/eventos/{id}` |
| Referidos / puntos | `GET /puntos/usuario/{id}`, `POST /puntos/usuario/{id}/canje`, `GET /referidos/codigo/{usuarioId}` |
| API Externa | **OpenStreetMap/OSMDroid** para mapas, **AWS S3** como CDN de imágenes |

Más detalles en `README_ENDPOINTS.md`.

---

## 🧪 Pruebas y cobertura (≥80 %)

```bash
./gradlew testDebugUnitTest jacocoTestReport
```

- Reporte JaCoCo: `app/build/reports/jacoco/testDebugUnitTest/html/index.html`
- Cobertura actual ≥80 % (ViewModels, Repositorios, Validadores).

> Adjunta captura del reporte en la entrega (README o carpeta `docs/`).

---

## 📦 Build firmado (APK/AAB)

1. Generar bundle/apk:
   ```bash
   ./gradlew :app:bundleRelease   # AAB
   ./gradlew :app:assembleRelease # APK
   ```
2. Archivos resultantes:
   - `app/build/outputs/bundle/release/app-release.aab`
   - `app/build/outputs/apk/release/app-release.apk`
3. Copiarlos a `release/LevelUpGamer-release.{aab,apk}` junto al `.jks` y capturas del wizard de firma.
4. Documentar en README (esta sección) + agregar credenciales en `release/keystore-info.txt` (enmascaradas si el repo es público).

---

## 📋 Pasos para ejecutar (resumen)

1. **Backend**
   - Ajustar variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `AWS_*`.
   - `cd Backend_Java_Spring/Fullstack_Ecommerce && ./build-all-services.sh && ./start-all-services.sh`
   - Verificar health en `http://localhost:8094/actuator/health`.
2. **Mobile**
   - `cp config/api-config.sample.properties config/api-config.properties` y setear URLs.
   - `./gradlew testDebugUnitTest jacocoTestReport` (validar antes de release).
   - `./gradlew assembleRelease` o usar Android Studio (Build > Generate Signed Bundle/APK).
3. **Instalar**
   - Emulador: `adb install release/LevelUpGamer-release.apk`.
   - Dispositivo físico: habilitar depuración USB o usar `bundletool install-apks`.

---

## 🗂 Evidencias de gestión

- **GitHub**: historial de commits por integrante (rama `main` + tags `v2.0-release`).
- **Trello / Planner**: [agrega el enlace del board aquí].
- **AVA**: subir ZIP con código + APK/AAB + `.jks` + README + evidencias.

---

## 📄 Licencia / Uso académico

Proyecto académico para **DSY1105 – Desarrollo de Aplicaciones Móviles**. Puede reutilizarse con fines educativos, citando al equipo LevelUp.
