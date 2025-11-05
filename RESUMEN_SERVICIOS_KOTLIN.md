# Resumen: Servicios API en Kotlin

## Servicios Implementados

### ✅ Productos
- `ProductosApiService` - Todos los endpoints GET
- Endpoints: `productos`, `productos/{id}`, `productos/categorias`, etc.

### ✅ Usuario  
- `UsuarioApiService` - Endpoints GET
- **IMPORTANTE**: `/usuarios/perfil` requiere header `X-User-Id`
- Endpoints: `usuarios/perfil`, `usuarios/{id}`, `usuarios/referidos`, etc.

### ✅ Carrito
- `CarritoApiService` - Todos los endpoints
- Requiere header `X-User-Id` (se agrega automáticamente)

### ✅ Inventario
- `InventarioApiService` - Nuevo servicio creado
- Endpoints: `inventario`, `inventario/{id}`, `inventario/producto/{productoId}`, etc.

### ✅ Referidos
- `ReferidosApiService` - Nuevo servicio creado
- Endpoints: `referidos`, `referidos/codigo?idUsuario=1`, etc.

### ✅ Eventos
- `EventosApiService` - Ya implementado

### ✅ Reseñas
- `ReseniaApiService` - Ya implementado

## Puertos Correctos

| Servicio | Puerto | URL Base | Context Path |
|----------|--------|----------|--------------|
| Productos | 8003 | `http://localhost:8003` | `/api/v1` |
| Usuario | 8095 | `http://localhost:8095` | `/api/v1` |
| Carrito | 8008 | `http://localhost:8008` | `/api/v1` |
| Inventario | 8004 | `http://localhost:8004` | `/api/v1` |
| Referidos | 8005 | `http://localhost:8005` | `/api/v1` |

**NOTA**: Los puertos en la documentación pueden estar incorrectos. Los puertos REALES son los de arriba.

## URLs Correctas

### Inventario
- **Correcto**: `http://localhost:8004/api/v1/inventario`
- **Incorrecto**: `http://localhost:8084/inventario` ❌

### Referidos  
- **Correcto**: `http://localhost:8005/api/v1/referidos/codigo?idUsuario=1`
- **Incorrecto**: `http://localhost:8089/referidos/codigo?idUsuario=1` ❌

### Usuario Perfil
- **Correcto**: `http://localhost:8095/api/v1/usuarios/perfil` + Header `X-User-Id: 5`
- **Error común**: Falta header `X-User-Id` → Error 500

## Configuración en Kotlin

### ApiConfig.kt
```kotlin
// Todos los servicios están disponibles:
val productosService: ProductosApiService
val usuarioService: UsuarioApiService
val carritoService: CarritoApiService
val inventarioService: InventarioApiService // ✅ Nuevo
val referidosService: ReferidosApiService // ✅ Nuevo
val eventosService: EventosApiService
val reseniaService: ReseniaApiService
```

### Headers Automáticos
- `X-API-Key`: Se agrega automáticamente a todas las peticiones
- `X-User-Id`: Se agrega automáticamente si `ApiConfig.setUserId()` fue llamado
- `Authorization`: Se agrega automáticamente si hay token JWT

## Ejemplos de Uso

### Inventario
```kotlin
// Obtener todo el inventario
val response = ApiConfig.inventarioService.getInventario()
if (response.isSuccessful) {
    val inventario = response.body()
}

// Obtener inventario por producto
val inventario = ApiConfig.inventarioService.getInventarioPorProducto(1L)
```

### Referidos
```kotlin
// Obtener código de referido
val response = ApiConfig.referidosService.getCodigoReferido(1L)
if (response.isSuccessful) {
    val codigo = response.body()?.get("codigoReferido")
}

// Obtener referidos de un usuario
val referidos = ApiConfig.referidosService.getReferidosDeUsuario(1L)
```

### Usuario Perfil
```kotlin
// IMPORTANTE: Primero configurar el userId
ApiConfig.setUserId("5")

// Luego obtener el perfil
val response = ApiConfig.usuarioService.getPerfil()
if (response.isSuccessful) {
    val perfil = response.body()
}
```

## Seguridad: Contraseñas

### ❌ NO puedes hacer GET a contraseñas

**Por seguridad, las contraseñas NUNCA deben exponerse en endpoints GET.**

### ✅ Cómo verificar que las contraseñas están hasheadas:

1. **En la base de datos H2**:
   ```
   http://localhost:8095/h2-console
   JDBC URL: jdbc:h2:file:./data/msvc_usuario_dev
   Username: sa
   Password: (vacío)
   
   SELECT id_usuario, correo, password FROM usuarios WHERE correo = 'aa@test.com';
   ```

2. **Verificar formato BCrypt**:
   - Las contraseñas hasheadas con BCrypt tienen formato: `$2a$10$...` o `$2b$10$...`
   - Tienen 60 caracteres de longitud
   - Ejemplo: `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`

3. **En los logs del inicializador**:
   ```
   Usuario de prueba completo creado: aa@test.com / aaaa - ID: 5
   ```
   La contraseña "aaaa" se hashea automáticamente con BCrypt antes de guardarse.

4. **Verificar en código**:
   ```java
   // En UsuarioDataInitializer.java
   usuarioPrueba.setPassword(passwordEncoder.encode("aaaa"));
   // passwordEncoder.encode() crea el hash BCrypt automáticamente
   ```

### ✅ Verificación de Login

La única forma de verificar que la contraseña es correcta es intentando hacer login:

```kotlin
// Login con contraseña original
val response = ApiConfig.authService.login(
    LoginRequest(email = "aa@test.com", password = "aaaa")
)
// Si el login es exitoso, la contraseña es correcta
```

## Endpoints que Requieren Headers

### Requieren X-User-Id
- `GET /usuarios/perfil` - Requiere `X-User-Id: 5`
- `PUT /usuarios/perfil` - Requiere `X-User-Id: 5`
- `GET /usuarios/direcciones` - Requiere `X-User-Id: 5`
- `GET /carrito/activo` - Requiere `X-User-Id: 5`
- `POST /carrito/items` - Requiere `X-User-Id: 5`

**En Kotlin**: `ApiConfig.setUserId("5")` agrega automáticamente el header.

## Troubleshooting

### Error: "X-User-Id header is not present"
**Solución**: Configurar userId antes de llamar al endpoint:
```kotlin
ApiConfig.setUserId("5") // ID del usuario
val perfil = ApiConfig.usuarioService.getPerfil()
```

### Error: "connect ECONNREFUSED 127.0.0.1:8089"
**Causa**: Puerto incorrecto. El puerto correcto es 8005, no 8089.
**Solución**: Usar `http://localhost:8005/api/v1/referidos` o a través del gateway `http://localhost:8094/referidos`

### Error: "connect ECONNREFUSED 127.0.0.1:8084"
**Causa**: Puerto incorrecto. El puerto correcto es 8004, no 8084.
**Solución**: Usar `http://localhost:8004/api/v1/inventario` o a través del gateway `http://localhost:8094/inventario`

