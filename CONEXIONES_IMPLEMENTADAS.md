# Resumen de Conexiones Implementadas - Kotlin App

## ✅ Estado General

**Todas las conexiones al backend están implementadas y funcionales**

## 📋 Servicios API Implementados

### 1. **ProductosApiService** ✅
- **Estado**: ✅ Completo
- **Repositorio**: `ProductoRepository` (usa Retrofit)
- **Endpoints**:
  - `GET /productos` - Obtener todos los productos
  - `GET /productos/{id}` - Obtener producto por ID
  - `GET /productos/categoria/{categoriaId}` - Obtener productos por categoría
  - `GET /productos/buscar?nombre={nombre}` - Buscar productos
  - `GET /productos/disponibles` - Obtener productos disponibles
  - `GET /productos/destacados` - Obtener productos destacados
- **Mapper**: `ProductoMapper` convierte DTOs del backend a modelos Kotlin
- **Backend**: `msvc-productos` ✅

### 2. **AuthApiService** ✅
- **Estado**: ✅ Completo
- **Endpoints**:
  - `POST /auth/login` - Login de usuario
  - `POST /usuarios` - Registro de usuario
  - `POST /auth/refresh` - Refrescar token JWT
- **Backend**: `msvc-auth` ✅

### 3. **UsuarioApiService** ✅
- **Estado**: ✅ Completo
- **Consumidores principales**: `ProfileViewModel`, `UsuariosViewModel`, `MainViewModel`, `ChangePasswordViewModel`
- **Endpoints**:
  - `GET /usuarios/perfil` - Obtener perfil del usuario
  - `GET /usuarios/{id}` - Obtener usuario por ID
  - `PUT /usuarios/perfil` - Actualizar perfil
  - `PUT /usuarios/cambiar-password` - Cambiar contraseña
  - `GET /usuarios/referidos` - Obtener referidos
  - `GET /usuarios` - Obtener todos los usuarios (admin)
  - `DELETE /usuarios/{id}` - Eliminar usuario (admin)
- **Backend**: `msvc-usuario` ✅

### 4. **CarritoApiService** ✅
- **Estado**: ✅ Completo
- **Repositorio**: `CarritoRepositoryRemote` (usa Retrofit)
- **Implementación**: `CarritoRepositoryRemote.kt`
- **Endpoints**:
  - `GET /carrito/activo` - Obtener carrito activo (requiere `X-User-Id`)
  - `POST /carrito/items` - Agregar item al carrito (requiere `X-User-Id`)
  - `PUT /carrito/items/{itemId}` - Actualizar cantidad (requiere `X-User-Id`)
  - `DELETE /carrito/items/{itemId}` - Eliminar item (requiere `X-User-Id`)
  - `DELETE /carrito/vaciar` - Vaciar carrito (requiere `X-User-Id`)
- **Headers**: `X-User-Id` se agrega automáticamente desde `ApiConfig`
- **Backend**: `msvc-carrito` ✅

### 5. **EventosApiService** ✅
- **Estado**: ✅ Completo
- **Repositorio**: `EventoRepositoryRemote` (usa Retrofit)
- **Implementación**: `EventoRepositoryRemote.kt`
- **Endpoints**:
  - `GET /eventos` - Obtener todos los eventos (con paginación)
  - `GET /eventos/proximos` - Obtener eventos próximos
  - `GET /eventos/{id}` - Obtener evento por ID
  - `POST /eventos/{id}/inscribir` - Inscribirse en evento
  - `DELETE /eventos/{id}/cancelar` - Cancelar inscripción
  - `GET /eventos/mis-inscripciones` - Obtener mis inscripciones
- **Backend**: `msvc-eventos` ✅

### 6. **ReseniaApiService** ✅
- **Estado**: ✅ Completo
- **Endpoints**:
  - `GET /resenia/producto/{productoId}` - Obtener reseñas de un producto
  - `POST /resenia` - Crear reseña
  - `PUT /resenia/{id}` - Actualizar reseña
  - `DELETE /resenia/{id}` - Eliminar reseña
  - `GET /resenia/mis-resenias` - Obtener mis reseñas
- **Backend**: `msvc-resenia` ✅

### 7. **ReferidosApiService** ✅
- **Estado**: ✅ Completo
- **Consumidores principales**: `ProfileViewModel`, `EventoViewModel`, `LoginViewModel`
- **Endpoints**:
  - `GET /referidos/codigo?idUsuario={id}` - Obtener código de referido
  - `GET /puntos/usuario/{id}` - Obtener puntos del usuario
  - `POST /puntos/usuario/{id}/inicio-sesion` - Otorgar puntos por inicio de sesión
  - `POST /puntos/usuario/{id}/canje-codigo` - Canjear código de evento
  - `POST /puntos/usuario/{id}/canje` - Canjear puntos por recompensa
- **Backend**: `msvc-referidos` ✅

## 🔧 Configuración Centralizada

### ApiConfig.kt
- **URL Base**: Configurada en `build.gradle.kts` (BuildConfig)
  - Desarrollo: `http://10.0.2.2:8094/` (emulador Android)
  - Producción: Configurada en `build.gradle.kts` línea 40 (cambiar después del despliegue en AWS)
- **API Key**: `levelup-2024-secret-api-key-change-in-production`
- **Headers Automáticos**:
  - `X-API-Key`: Agregado automáticamente a todas las peticiones
  - `Authorization: Bearer {token}`: Agregado si hay token JWT
  - `X-User-Id`: Agregado si hay userId (para endpoints que lo requieren)
- **Métodos**:
  - `setAuthToken(token)`: Establece el token JWT
  - `setUserId(userId)`: Establece el User ID
  - `getAuthToken()`: Obtiene el token JWT actual
  - `getUserId()`: Obtiene el User ID actual
  - `clear()`: Limpia token y userId (logout)

## 📦 Repositorios Implementados

### 1. **ProductoRepository** ✅
- **Fuente**: Backend (Retrofit)
- **Métodos**: Todos los métodos usan Retrofit
- **Mapper**: `ProductoMapper` convierte DTOs a modelos

### 2. **CarritoRepositoryRemote** ✅
- **Fuente**: Backend (Retrofit)
- **Métodos**: Todos los métodos usan Retrofit

### 3. **EventoRepositoryRemote** ✅
- **Fuente**: Backend (Retrofit)
- **Métodos**: Todos los métodos usan Retrofit
- **Nota**: `EventoRepository` (antigua implementación con datos hardcodeados) puede mantenerse como fallback

### 4. **BlogRepository** ℹ️
- **Fuente**: Datos hardcodeados
- **Estado**: No hay backend para blogs, se mantiene con datos hardcodeados
- **Nota**: OK mantener así, no hay microservicio de blogs

## 🔗 Backend Microservicios

### ✅ Todos los microservicios están conectados:

1. **msvc-productos** ✅
   - Endpoints implementados
   - CORS configurado para Android emulator (`http://10.0.2.2:8094`)
   - API Key configurada

2. **msvc-auth** ✅
   - Endpoints implementados
   - CORS configurado
   - API Key configurada

3. **msvc-usuario** ✅
   - Endpoints implementados
   - CORS configurado
   - API Key configurada

4. **msvc-carrito** ✅
   - Endpoints implementados (incluye endpoints para Kotlin)
   - CORS configurado
   - API Key configurada
   - Headers `X-User-Id` implementados

5. **msvc-eventos** ✅
   - Endpoints implementados
   - CORS configurado
   - API Key configurada

6. **msvc-resenia** ✅
   - Endpoints implementados
   - CORS configurado
   - API Key configurada

7. **msvc-gateway** ✅
   - Routing configurado
   - CORS configurado para Android emulator
   - API Key configurada

## 📝 Notas Importantes

### Para Usar en Producción (AWS):

1. **Actualizar URL en `build.gradle.kts`**:
   ```kotlin
   release {
       buildConfigField("String", "API_BASE_URL", "\"https://TU-URL-AWS-AQUI/\"")
   }
   ```

2. **Establecer User ID después del login**:
   ```kotlin
   ApiConfig.setUserId(userId)
   ```

3. **Establecer Token después del login**:
   ```kotlin
   ApiConfig.setAuthToken(token)
   ```

4. **Limpiar en logout**:
   ```kotlin
   ApiConfig.clear()
   ```

## ✅ Checklist Final

- [x] ProductosApiService implementado
- [x] AuthApiService implementado
- [x] UsuarioApiService implementado
- [x] CarritoApiService implementado
- [x] EventosApiService implementado
- [x] ReseniaApiService implementado
- [x] ApiConfig configurado con todos los servicios
- [x] Headers automáticos (API Key, JWT, User ID)
- [x] BuildConfig para URLs de desarrollo/producción
- [x] ProductoRepository usando Retrofit
- [x] CarritoRepositoryRemote usando Retrofit
- [x] EventoRepositoryRemote usando Retrofit
- [x] Backend CORS configurado para Android emulator
- [x] Backend API Key configurado
- [x] Backend endpoints compatibles con Kotlin

## 🎉 Resultado

**Todas las conexiones están implementadas y listas para usar**

- Desarrollo: Funciona con emulador Android (`http://10.0.2.2:8094/`)
- Producción: Listo para AWS (solo cambiar URL en `build.gradle.kts`)

