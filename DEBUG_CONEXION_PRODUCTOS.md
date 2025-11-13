# Guía de Debugging: Productos no cargan en Kotlin

## Pasos para Verificar

### 1. Verificar que los Microservicios estén Corriendo

**En Windows (PowerShell):**
```powershell
# Verificar que el puerto 8003 (productos) esté en uso
netstat -ano | findstr :8003

# Verificar que el puerto 8094 (gateway) esté en uso
netstat -ano | findstr :8094
```

**En Linux/Mac:**
```bash
# Verificar que el puerto 8003 (productos) esté en uso
lsof -i :8003

# Verificar que el puerto 8094 (gateway) esté en uso
lsof -i :8094
```

### 2. Probar el Endpoint Directamente

**Desde Postman o Thunder Client:**
```
GET http://localhost:8094/productos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Desde PowerShell (Windows):**
```powershell
Invoke-RestMethod -Uri "http://localhost:8094/productos" -Headers @{"X-API-Key"="levelup-2024-secret-api-key-change-in-production"} -Method GET
```

**Desde curl (Linux/Mac):**
```bash
curl -X GET "http://localhost:8094/productos" \
  -H "X-API-Key: levelup-2024-secret-api-key-change-in-production"
```

### 3. Verificar la Configuración de Kotlin

**URL Base en `build.gradle.kts`:**
```kotlin
buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8094/\"")
```

**API Key en `build.gradle.kts`:**
```kotlin
buildConfigField("String", "API_KEY", "\"levelup-2024-secret-api-key-change-in-production\"")
```

### 4. Verificar los Logs de Android

**En Android Studio:**
1. Abre Logcat
2. Filtra por `ApiConfig` o `ProductosApiService`
3. Busca errores de conexión o respuesta HTTP

**Comandos útiles en Logcat:**
```
Filter: "ApiConfig|ProductosApiService|Retrofit|OkHttp"
```

### 5. Verificar el Interceptor de Headers

**En `ApiConfig.kt`:**
- Verifica que el `authInterceptor` esté agregando el header `X-API-Key`
- Verifica que el `loggingInterceptor` esté habilitado para ver las peticiones

### 6. Verificar el Endpoint del Backend

**El gateway redirige:**
- `/productos/**` → `http://localhost:8003/api/v1/productos/**`

**El microservicio espera:**
- `/api/v1/productos` (con `context-path=/api/v1`)

**El endpoint real es:**
- `GET http://localhost:8003/api/v1/productos`

### 7. Verificar CORS

**El gateway debe tener:**
```java
corsConfig.addAllowedOrigin("http://10.0.2.2:8094"); // Android Emulator
```

**El ProductoController debe tener:**
```java
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://10.0.2.2:8094"})
```

## Problemas Comunes y Soluciones

### Problema 1: "Connection refused" o "Failed to connect"
**Solución:**
- Verifica que ambos microservicios estén corriendo
- Verifica que no haya firewall bloqueando las conexiones
- Verifica que la URL base sea correcta (`http://10.0.2.2:8094/` para emulador)

### Problema 2: "401 Unauthorized" o "403 Forbidden"
**Solución:**
- Verifica que el API Key esté configurado correctamente en Kotlin
- Verifica que el API Key en el backend coincida con el de Kotlin
- Verifica que el header `X-API-Key` se esté enviando correctamente

### Problema 3: "404 Not Found"
**Solución:**
- Verifica que el endpoint `/productos` esté correctamente configurado en el gateway
- Verifica que el gateway esté redirigiendo correctamente a `/api/v1/productos`
- Verifica que el microservicio de productos esté corriendo en el puerto 8003

### Problema 4: "CORS error" o "Network error"
**Solución:**
- Verifica que el origen `http://10.0.2.2:8094` esté permitido en CORS
- Verifica que el `ProductoController` tenga `@CrossOrigin` con el origen correcto
- Verifica que el gateway tenga CORS configurado correctamente

### Problema 5: "Empty response" o lista vacía
**Solución:**
- Verifica que la base de datos H2 tenga datos (consulta `http://localhost:8003/h2-console`)
- Verifica que el endpoint del backend esté devolviendo datos
- Verifica que el mapper `ProductoMapper` esté funcionando correctamente

## Testing Paso a Paso

### Paso 1: Probar el Microservicio Directamente
```bash
GET http://localhost:8003/api/v1/productos
Header: X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Resultado esperado:** Lista de productos en JSON

### Paso 2: Probar a través del Gateway
```bash
GET http://localhost:8094/productos
Header: X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Resultado esperado:** Misma lista de productos en JSON

### Paso 3: Probar desde Kotlin (Emulador)
- URL: `http://10.0.2.2:8094/productos`
- Header: `X-API-Key: levelup-2024-secret-api-key-change-in-production`

**Resultado esperado:** Lista de productos en Kotlin

## Verificar Logs en Android Studio

1. Abre Logcat en Android Studio
2. Filtra por: `OkHttp`, `Retrofit`, `ProductosApiService`
3. Busca las peticiones HTTP y sus respuestas
4. Verifica si hay errores de conexión, autenticación o parsing

## Código de Debug en Kotlin

Agrega esto temporalmente en `ProductoRepository.kt` para ver qué está pasando:

```kotlin
suspend fun obtenerProductos(): List<Producto> = withContext(Dispatchers.IO) {
    try {
        println("DEBUG: Intentando obtener productos desde ${ApiConfig.BASE_URL}productos")
        val response = productosService.getProductos()
        println("DEBUG: Response code: ${response.code()}")
        println("DEBUG: Response isSuccessful: ${response.isSuccessful}")
        println("DEBUG: Response body: ${response.body()}")
        if (response.isSuccessful && response.body() != null) {
            val dtos = response.body()!!
            println("DEBUG: Productos recibidos: ${dtos.size}")
            ProductoMapper.mapProductosDto(dtos)
        } else {
            println("DEBUG: Error en respuesta: ${response.message()}")
            emptyList()
        }
    } catch (e: Exception) {
        println("DEBUG: Excepción al obtener productos: ${e.message}")
        e.printStackTrace()
        emptyList()
    }
}
```

## Verificar Base de Datos H2

1. Abre el navegador
2. Ve a: `http://localhost:8003/h2-console`
3. JDBC URL: `jdbc:h2:file:./data/msvc_productos_dev`
4. Usuario: `sa`
5. Contraseña: (vacía)
6. Ejecuta: `SELECT * FROM productos;`

**Resultado esperado:** Debe haber productos en la base de datos

