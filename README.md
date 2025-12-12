<<<<<<< HEAD
# LevelUp - Aplicación Android Gamer

Una aplicación móvil para la comunidad gamer chilena que permite explorar productos gaming, participar en eventos locales y acumular puntos por participación.

## Características Principales

- **Catálogo de Productos**: Explora productos gaming con filtros avanzados
- **Sistema de Reviews**: Califica y comenta productos
- **Eventos Locales**: Participa en eventos con geolocalización
- **Sistema de Puntos**: Acumula puntos por participación
- **Perfil de Usuario**: Gestiona tu información y avatar
- **Carrito de Compras**: Agrega productos y gestiona cantidades

## Tecnologías Utilizadas

- **Android Studio** - IDE oficial de Android
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework de UI moderna
- **Material Design 3** - Sistema de diseño
- **Room Database** - Base de datos local
- **Navigation Compose** - Navegación type-safe
- **Coroutines & StateFlow** - Programación asíncrona
- **OSMDroid** - Mapas interactivos
- **Coil** - Carga de imágenes

## Requisitos del Sistema

### Desarrollo
- **Android Studio** Arctic Fox (2020.3.1) o superior
- **JDK 11** o superior
- **Android SDK** API 36
- **Gradle** 8.0 o superior
- **Kotlin** 1.9.10 o superior

### Dispositivo/Emulador
- **Android 7.0** (API 24) o superior
- **RAM**: Mínimo 2GB recomendado
- **Almacenamiento**: 100MB libres
- **Permisos**: Cámara, Almacenamiento, Ubicación

## Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/levelup-backend.git
cd levelup-backend
```

### 2. Abrir en Android Studio

1. Abre **Android Studio**
2. Selecciona **"Open an existing project"**
3. Navega a la carpeta `levelup-backend`
4. Selecciona el archivo `build.gradle.kts` en la raíz
5. Haz clic en **"OK"**

### 3. Configurar el SDK

1. Ve a **File > Project Structure**
2. En **SDK Location**, verifica que esté configurado:
   - **Android SDK Location**: `C:\Users\[Usuario]\AppData\Local\Android\Sdk`
   - **JDK Location**: JDK 11 o superior

### 4. Sincronizar Dependencias

```bash
# En la terminal de Android Studio o línea de comandos
./gradlew build
```

O en Android Studio:
1. Haz clic en **"Sync Now"** cuando aparezca la notificación
2. O ve a **File > Sync Project with Gradle Files**

## Dependencias Principales

### Core Android
```kotlin
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
implementation("androidx.activity:activity-compose:1.8.2")
```

### Jetpack Compose
```kotlin
implementation(platform("androidx.compose:compose-bom:2024.02.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
```

### Navigation
```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```

### Room Database
```kotlin
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
```

### Coroutines
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

### Networking
```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
```

### Image Loading
```kotlin
implementation("io.coil-kt:coil-compose:2.5.0")
```

### Maps
```kotlin
implementation("org.osmdroid:osmdroid-android:6.1.18")
```

## Ejecutar la Aplicación

### Opción 1: Emulador Android

1. **Crear AVD (Android Virtual Device)**:
   - Ve a **Tools > AVD Manager**
   - Haz clic en **"Create Virtual Device"**
   - Selecciona **Pixel 6** o dispositivo similar
   - Elige **API 34** (Android 14) o superior
   - Configura las opciones y haz clic en **"Finish"**

2. **Ejecutar en Emulador**:
   - Inicia el emulador desde AVD Manager
   - En Android Studio, haz clic en **Run** (▶️) o presiona **Shift + F10**
   - Selecciona el emulador y haz clic en **"OK"**

### Opción 2: Dispositivo Físico

1. **Habilitar Opciones de Desarrollador**:
   - Ve a **Configuración > Acerca del teléfono**
   - Toca **"Número de compilación"** 7 veces
   - Ve a **Configuración > Opciones de desarrollador**
   - Habilita **"Depuración USB"**

2. **Conectar Dispositivo**:
   - Conecta el dispositivo via USB
   - Autoriza la depuración USB cuando aparezca la notificación
   - En Android Studio, selecciona tu dispositivo y haz clic en **Run**

### Opción 3: Línea de Comandos

```bash
# Compilar la aplicación
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug

# Ejecutar tests
./gradlew test
```

## Estructura del Proyecto

```
app/src/main/java/com/example/levelupprueba/
├── data/
│   ├── local/                 # Room Database
│   │   ├── AppDatabase.kt
│   │   ├── UsuarioDao.kt
│   │   └── ReviewDao.kt
│   ├── remote/                # API Services
│   │   └── UsuarioApiService.kt
│   └── repository/            # Repository Pattern
│       ├── UsuarioRepository.kt
│       ├── ProductoRepository.kt
│       └── EventoRepository.kt
├── model/                     # Data Models
│   ├── auth/                  # Authentication
│   ├── usuario/               # User Models
│   ├── producto/              # Product Models
│   ├── evento/                # Event Models
│   └── blog/                  # Blog Models
├── navigation/                # Navigation
│   ├── MainNavGraph.kt
│   ├── Screen.kt
│   └── NavigationEvents.kt
├── ui/                        # User Interface
│   ├── components/            # Reusable Components
│   │   ├── cards/
│   │   ├── buttons/
│   │   ├── inputs/
│   │   └── overlays/
│   ├── screens/               # App Screens
│   │   ├── home/
│   │   ├── productos/
│   │   ├── auth/
│   │   ├── profile/
│   │   └── eventos/
│   └── theme/                 # Material Design Theme
│       ├── Theme.kt
│       ├── ColorSchemes.kt
│       └── Dimens.kt
├── viewmodel/                 # Business Logic
│   ├── MainViewModel.kt
│   ├── LoginViewModel.kt
│   ├── ProductoViewModel.kt
│   └── EventoViewModel.kt
├── utils/                     # Utilities
│   ├── ImageUtils.kt
│   ├── FechaUtils.kt
│   └── MoneyUtils.kt
└── MainActivity.kt            # Main Activity
```

## Configuración Adicional

### Permisos Requeridos

La aplicación requiere los siguientes permisos (ya configurados en `AndroidManifest.xml`):

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

### Configuración de Base de Datos

La aplicación usa Room Database con las siguientes entidades:
- **Usuario**: Información del usuario
- **Review**: Reseñas de productos

La base de datos se crea automáticamente al ejecutar la aplicación.

## Solución de Problemas

### Error: "SDK location not found"
```bash
# Crear archivo local.properties en la raíz del proyecto
echo "sdk.dir=C:\\Users\\[Usuario]\\AppData\\Local\\Android\\Sdk" > local.properties
```

### Error: "Gradle sync failed"
1. Ve a **File > Invalidate Caches and Restart**
2. Selecciona **"Invalidate and Restart"**
3. Espera a que se reinicie Android Studio

### Error: "Emulator not starting"
1. Verifica que **Hyper-V** esté habilitado (Windows)
2. Asegúrate de tener **HAXM** instalado
3. Reinicia el emulador desde AVD Manager

### Error: "App not installing on device"
1. Verifica que **"Depuración USB"** esté habilitada
2. Autoriza la conexión cuando aparezca la notificación
3. Verifica que el dispositivo tenga **Android 7.0** o superior

## Funcionalidades de la App

### Pantalla Principal
- Carrusel de imágenes promocionales
- Productos destacados en grid de 2 columnas
- Navegación a secciones principales

### Catálogo de Productos
- Grid de productos con filtros avanzados
- Búsqueda por texto, categoría, precio
- Detalle de producto con reviews y rating

### Eventos
- Mapa interactivo con ubicaciones
- Lista de eventos con fechas y lugares
- Sistema de puntos por participación
- Canje de códigos de eventos

### Perfil de Usuario
- Edición de información personal
- Cambio de contraseña
- Gestión de avatar (cámara/galería)
- Sistema de referidos

### Carrito de Compras
- Agregar/quitar productos
- Gestión de cantidades
- Cálculo de totales

¡Disfruta desarrollando con LevelUp!

---

*Última actualización: Octubre 2025*
=======
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
>>>>>>> main
