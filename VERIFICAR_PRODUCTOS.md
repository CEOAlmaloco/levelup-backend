# Verificación Rápida: Productos no Cargando en Kotlin

## Pasos Inmediatos

### 1. Verificar que los Microservicios estén Corriendo

**Verifica que ambos servicios estén activos:**
- `msvc-productos` (Puerto 8003)
- `msvc-gateway` (Puerto 8094)

### 2. Probar el Endpoint Manualmente

**Desde PowerShell (Windows):**
```powershell
Invoke-RestMethod -Uri "http://localhost:8094/productos" -Headers @{"X-API-Key"="levelup-2024-secret-api-key-change-in-production"} -Method GET
```

**Si funciona, deberías ver una lista de productos en JSON.**

### 3. Verificar Logs en Android Studio

1. Abre **Logcat** en Android Studio
2. Filtra por: `ProductoRepository` o `ProductoViewModel`
3. Busca mensajes como:
   - "Intentando obtener productos desde el backend..."
   - "Response code: XXX"
   - "Error en respuesta: ..."

### 4. Verificar la Configuración

**URL Base (debe ser):**
```
http://10.0.2.2:8094/
```

**API Key (debe ser):**
```
levelup-2024-secret-api-key-change-in-production
```

**Endpoint:**
```
GET /productos
```

### 5. Problemas Comunes

#### Error: "Connection refused"
**Solución:** Los microservicios no están corriendo. Inícialos primero.

#### Error: "401 Unauthorized"
**Solución:** El API Key no coincide. Verifica que sea exactamente: `levelup-2024-secret-api-key-change-in-production`

#### Error: "404 Not Found"
**Solución:** El gateway no está redirigiendo correctamente. Verifica que el gateway esté corriendo y que la ruta `/productos` esté configurada.

#### Error: "Empty list" o lista vacía
**Solución:** 
1. Verifica que la base de datos H2 tenga datos: `http://localhost:8003/h2-console`
2. Verifica que el endpoint devuelva datos
3. Revisa los logs para ver si hay errores de mapeo

## Comandos Útiles

### Reiniciar Microservicios
```powershell
# Terminal 1: Productos
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
mvn spring-boot:run

# Terminal 2: Gateway
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-gateway
mvn spring-boot:run
```

### Verificar Puertos
```powershell
netstat -ano | findstr :8003
netstat -ano | findstr :8094
```

### Ver Logs de Android
En Logcat, filtra por:
```
tag:ProductoRepository OR tag:ProductoViewModel OR tag:OkHttp
```

## Si Nada Funciona

1. **Verifica la consola H2**: `http://localhost:8003/h2-console`
   - JDBC URL: `jdbc:h2:file:./data/msvc_productos_dev`
   - Usuario: `sa`
   - Contraseña: (vacía)
   - Ejecuta: `SELECT COUNT(*) FROM productos;`

2. **Recompila la app Kotlin** para asegurar que los cambios se apliquen

3. **Reinicia ambos microservicios** para asegurar que los cambios se apliquen

4. **Verifica el Logcat** para ver los mensajes de debug que agregamos

