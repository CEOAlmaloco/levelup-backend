# 📱 Guía de Inicio - Android Studio con Backend

## 🎯 Opciones de Configuración

Tienes dos opciones para probar la app Android:

### Opción A: Backend Local (Recomendado para desarrollo)
- ✅ Más rápido
- ✅ No requiere AWS
- ✅ Ideal para desarrollo y pruebas

### Opción B: Backend en AWS (Producción)
- ✅ Simula entorno real
- ✅ Requiere instancia AWS activa
- ✅ Ideal para pruebas de integración

---

## 🚀 Opción A: Backend Local

### Paso 1: Crear archivo de configuración

Crea el archivo `api-config.properties` en la carpeta `config/`:

**Ubicación:** `Kotlin_app/levelup-backend/config/api-config.properties`

```properties
# URL del API Gateway cuando se ejecuta el stack en Docker local.
# 10.0.2.2 apunta al host desde el emulador de Android.
gateway.url.debug=http://10.0.2.2:8094/

# URL alternativa para pruebas en un dispositivo físico dentro de la red local.
# IMPORTANTE: Cambia 192.168.1.100 por la IP de tu máquina (ver cómo obtenerla abajo)
gateway.url.device=http://192.168.1.100:8094/

# URL pública del API Gateway desplegado en AWS/EC2.
# Solo necesaria si usas AWS
gateway.url.release=http://ec2-54-161-72-45.compute-1.amazonaws.com:8094/

# API Key requerida por el gateway y microservicios.
gateway.api.key=levelup-2024-secret-api-key-change-in-production

# URL base para recursos multimedia (S3/CDN). Debe terminar en "/".
media.base.url=https://levelup-gamer-products.s3.us-east-1.amazonaws.com/
```

**📝 Nota:** Si no existe la carpeta `config/`, créala en `Kotlin_app/levelup-backend/config/`

### Paso 2: Obtener tu IP local (solo si usas dispositivo físico)

**En PowerShell:**
```powershell
ipconfig | findstr "IPv4"
```

Busca la línea que dice algo como:
```
IPv4 Address. . . . . . . . . . . : 192.168.1.XXX
```

**Actualiza** `gateway.url.device` en `api-config.properties` con esa IP.

### Paso 3: Iniciar microservicios backend

**Opción rápida (recomendada):**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK
.\iniciar-backend.ps1
```

**Opción manual:**
Abre 5 ventanas de PowerShell y ejecuta en cada una:

**Ventana 1 - API Gateway (PRIMERO):**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Fullstack_Ecommerce\msvc-gateway
.\mvnw.cmd spring-boot:run
```

**Ventana 2 - Auth:**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Fullstack_Ecommerce\msvc-auth
.\mvnw.cmd spring-boot:run
```

**Ventana 3 - Usuario:**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Fullstack_Ecommerce\msvc-usuario
.\mvnw.cmd spring-boot:run
```

**Ventana 4 - Productos:**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Fullstack_Ecommerce\msvc-productos
.\mvnw.cmd spring-boot:run
```

**Ventana 5 - Carrito:**
```powershell
cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Fullstack_Ecommerce\msvc-carrito
.\mvnw.cmd spring-boot:run
```

**⏰ Espera 1-2 minutos** hasta ver en cada ventana:
```
Started [NombreServicio]Application in X.XXX seconds
```

### Paso 4: Verificar que el backend está funcionando

Abre el navegador y ve a:
```
http://localhost:8094/productos
```

Deberías ver una respuesta JSON (puede estar vacía `[]` si no hay productos, pero no debe dar error).

### Paso 5: Configurar Android Studio

1. **Abrir el proyecto:**
   - Abre Android Studio
   - File → Open
   - Selecciona: `C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK\Kotlin_app\levelup-backend`

2. **Sincronizar Gradle:**
   - Android Studio debería sincronizar automáticamente
   - Si no, click en "Sync Now" o File → Sync Project with Gradle Files
   - Espera a que termine (puede tardar 1-2 minutos la primera vez)

3. **Verificar configuración:**
   - El archivo `api-config.properties` ya está configurado
   - Las URLs están en `build.gradle.kts` y se leen desde `api-config.properties`

### Paso 6: Ejecutar la app

**Opción A: Emulador Android (Recomendado)**
1. En Android Studio, click en el dispositivo selector (arriba)
2. Crea un emulador si no tienes:
   - Tools → Device Manager
   - Create Device → Selecciona un dispositivo (ej: Pixel 5)
   - Selecciona una imagen del sistema (API 24 o superior)
   - Finish
3. Ejecuta la app:
   - Click en el botón ▶️ (Run) o presiona `Shift + F10`
   - Selecciona el emulador
   - La app se instalará y ejecutará

**Opción B: Dispositivo físico**
1. Habilita "Opciones de desarrollador" en tu teléfono:
   - Settings → About Phone
   - Toca "Build Number" 7 veces
2. Habilita "USB Debugging":
   - Settings → Developer Options → USB Debugging
3. Conecta el teléfono por USB
4. En Android Studio, selecciona tu dispositivo
5. Ejecuta la app (▶️)

### Paso 7: Verificar conexión

Una vez que la app esté corriendo:

1. **Revisa los logs en Android Studio:**
   - Abre la pestaña "Logcat" (abajo)
   - Filtra por "ApiConfig" o "OkHttp"
   - Deberías ver logs de las peticiones HTTP

2. **Prueba hacer login o cargar productos:**
   - Si todo está bien, deberías ver productos o poder hacer login
   - Si hay errores, revisa los logs

---

## ☁️ Opción B: Backend en AWS

### Paso 1: Verificar que AWS está activo

Asegúrate de que tu instancia EC2 esté corriendo y el API Gateway esté accesible.

### Paso 2: Actualizar configuración

Edita `Kotlin_app/levelup-backend/config/api-config.properties`:

```properties
# Para usar AWS, cambia la URL de release
gateway.url.release=http://TU-IP-AWS:8094/
# O si tienes dominio:
gateway.url.release=https://api.tu-dominio.com/
```

### Paso 3: Cambiar a build de Release

En Android Studio:
1. Build → Select Build Variant
2. Cambia de "debug" a "release" para el módulo `app`

**O edita manualmente `build.gradle.kts`:**
- El build de release usa `gateway.url.release` automáticamente

### Paso 4: Ejecutar la app

Sigue los pasos 5-7 de la Opción A, pero usando el build variant "release".

---

## 🔍 Verificar que todo funciona

### En los logs de Android Studio (Logcat):

Busca estos mensajes:
```
✅ ApiConfig: BASE_URL configurada: http://10.0.2.2:8094/
✅ OkHttp: --> GET http://10.0.2.2:8094/productos
✅ OkHttp: <-- 200 OK http://10.0.2.2:8094/productos
```

### Si ves errores:

**Error: "Failed to connect to /10.0.2.2:8094"**
- ✅ Verifica que el Gateway esté corriendo en `localhost:8094`
- ✅ Verifica que el emulador esté activo
- ✅ Prueba en navegador: `http://localhost:8094/productos`

**Error: "Cleartext HTTP traffic not permitted"**
- ✅ Ya está solucionado con `network_security_config.xml`
- ✅ Si persiste, verifica que el archivo esté en `res/xml/network_security_config.xml`

**Error: "401 Unauthorized"**
- ✅ Verifica que la API Key sea correcta en `api-config.properties`
- ✅ Verifica que el backend esté usando la misma API Key

---

## 📝 Resumen Rápido

1. ✅ Crear `config/api-config.properties` (copiar de `api-config.sample.properties`)
2. ✅ Iniciar backend: `.\iniciar-backend.ps1`
3. ✅ Esperar 1-2 minutos
4. ✅ Verificar: `http://localhost:8094/productos`
5. ✅ Abrir Android Studio → Open → `levelup-backend`
6. ✅ Sync Gradle
7. ✅ Run ▶️ en emulador o dispositivo
8. ✅ Revisar logs en Logcat

---

## 🆘 Troubleshooting

### El backend no inicia
- Verifica que Java 17+ esté instalado: `java -version`
- Verifica que Maven esté instalado: `mvn -version`
- Revisa los logs en las ventanas de PowerShell

### La app no se conecta
- Verifica que el emulador esté corriendo
- Verifica que el backend esté en `localhost:8094`
- Revisa `network_security_config.xml`
- Revisa los logs en Logcat filtrando por "ApiConfig"

### Build errors en Android Studio
- File → Invalidate Caches → Invalidate and Restart
- Build → Clean Project
- Build → Rebuild Project

