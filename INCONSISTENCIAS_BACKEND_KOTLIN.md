# Inconsistencias entre Kotlin y Backend

Este documento lista todas las inconsistencias encontradas entre los endpoints que Kotlin está llamando y los que el backend está proporcionando.

## 🔴 CRÍTICAS - Requieren corrección inmediata

### 1. UsuarioController - Perfil

**Problema**: El endpoint `/usuarios/perfil` requiere el header `X-User-Id`, pero Kotlin espera que el backend extraiga el userId del token JWT automáticamente.

**Backend actual**:
```java
@GetMapping("/perfil")
public ResponseEntity<UsuarioResponseDTO> getPerfil(@RequestHeader("X-User-Id") Long userId)
```

**Kotlin espera**: Que el backend extraiga el userId del token JWT en el header `Authorization: Bearer <token>`.

**Solución**: 
- Opción 1: Modificar el backend para extraer el userId del token JWT
- Opción 2: Modificar Kotlin para enviar el header `X-User-Id` (ya lo hace, pero necesita verificar)

### 2. UsuarioController - DTO de Respuesta

**Problema**: El DTO `UsuarioResponseDTO` tiene campos que no coinciden con lo que Kotlin espera.

**Backend** (`UsuarioResponseDTO`):
- `idUsuario: Long` → Kotlin espera `id: String`
- `apellido: String` → Kotlin espera `apellidos: String`
- `puntosLevelUp: Integer` → Kotlin espera `puntos: Int`
- No tiene `role: String` → Kotlin espera `role: String`
- No tiene `avatar: String` → Kotlin espera `avatar: String?`
- `fechaNacimiento: LocalDate` → Kotlin espera `fechaNacimiento: String`

**Solución**: 
- Opción 1: Crear un DTO específico para Kotlin que mapee los campos correctamente
- Opción 2: Modificar `UsuarioResponseDTO` para que tenga todos los campos que Kotlin necesita
- Opción 3: Modificar Kotlin para mapear los campos correctamente

### 3. UsuarioController - Endpoint `/usuarios/referidos`

**Problema**: Kotlin llama a `/usuarios/referidos` pero este endpoint no existe en el backend.

**Kotlin**:
```kotlin
@GET("usuarios/referidos")
suspend fun getReferidos(): Response<List<ReferidoDto>>
```

**Backend**: No existe este endpoint.

**Solución**: 
- Opción 1: Agregar el endpoint `/usuarios/referidos` en el backend
- Opción 2: Modificar Kotlin para usar `/referidos/usuario/{usuarioId}/referidos`

### 4. UsuarioController - Actualizar Perfil

**Problema**: El DTO `UsuarioUpdateDTO` tiene campos que no coinciden con `ActualizarPerfilRequest` de Kotlin.

**Backend** (`UsuarioUpdateDTO`):
- Tiene `nombre`, `apellido`, `telefono`, `direccion`, `avatarUrl`
- También tiene muchos otros campos que Kotlin no envía

**Kotlin** (`ActualizarPerfilRequest`):
- Solo tiene `nombre`, `telefono`, `direccion`, `avatar` (no `avatarUrl`)

**Solución**: 
- Opción 1: Crear un DTO específico para actualizar perfil que coincida con Kotlin
- Opción 2: Modificar Kotlin para enviar todos los campos necesarios

### 5. EventoController - Endpoints de Inscripción

**Problema**: Los endpoints de eventos no coinciden con lo que Kotlin espera.

**Kotlin**:
- `GET /eventos/proximos` → Backend tiene `GET /eventos/futuros`
- `POST /eventos/{id}/inscribir` → Backend tiene `POST /eventos/{id}/participar`
- `DELETE /eventos/{id}/cancelar` → Backend tiene `DELETE /eventos/{id}/cancelar-participacion`
- `GET /eventos/mis-inscripciones` → Backend no tiene este endpoint

**Solución**: 
- Opción 1: Agregar los endpoints compatibles en el backend
- Opción 2: Modificar Kotlin para usar los endpoints del backend

### 6. EventoController - DTO de Respuesta

**Problema**: El DTO `EventoResponseDTO` tiene campos que no coinciden con `EventoDto` de Kotlin.

**Backend** (`EventoResponseDTO`):
- `idEvento: Long` → Kotlin espera `id: String`
- `nombreEvento: String` → Kotlin espera `titulo: String`
- `descripcionEvento: String` → Kotlin espera `descripcion: String`
- `ubicacionEvento: String` → Kotlin espera `ubicacion: String`
- `coordenadasLatitud: Double`, `coordenadasLongitud: Double` → Kotlin espera `latitud: Double`, `longitud: Double`
- `cuposMaximos: Integer`, `cuposDisponibles: Integer` → Kotlin espera `capacidadMaxima: Int`, `participantesActuales: Int`
- `puntosLevelUp: Integer` → Kotlin espera `puntosRecompensa: Int`
- `costoEntrada: Double` → Kotlin espera `precio: Double`
- `tipoEvento: String` → Kotlin espera `categoria: String`
- `ciudad: String` → Kotlin no tiene este campo
- `imagen: String`, `imagenes: String` → Kotlin espera `imagen: String`

**Solución**: 
- Opción 1: Crear un DTO específico para Kotlin que mapee los campos correctamente
- Opción 2: Modificar Kotlin para mapear los campos correctamente

## 🟡 MODERADAS - Requieren atención

### 7. ReferidoController - Endpoint `/referidos/codigo`

**Problema**: El endpoint requiere `idUsuario` como query param, lo cual coincide con Kotlin, pero el backend devuelve un `Map<String, String>` con `codigoReferido`, lo cual también coincide.

**Estado**: ✅ Coincide correctamente

### 8. ContenidoController - Endpoint de búsqueda

**Problema**: El backend usa `q` como query param, pero Kotlin podría usar otro nombre.

**Backend**:
```java
@GetMapping("/articulos/buscar")
public ResponseEntity<List<ArticuloResponseDTO>> buscarArticulos(@RequestParam String q)
```

**Kotlin**:
```kotlin
@GET("contenido/articulos/buscar")
suspend fun buscarArticulos(@Query("q") q: String): Response<List<ArticuloResponse>>
```

**Estado**: ✅ Coincide correctamente

### 9. CarritoController - Endpoints

**Problema**: Los endpoints del carrito parecen coincidir, pero hay que verificar los DTOs.

**Estado**: ✅ Coincide correctamente (verificado)

### 10. ReseniaController - Endpoints

**Problema**: Los endpoints de reseñas parecen coincidir, pero hay que verificar los DTOs.

**Estado**: ✅ Coincide correctamente (verificado)

## 🟢 MENORES - Bajo impacto

### 11. ProductosController - Endpoints

**Estado**: ✅ Coincide correctamente (verificado)

### 12. PedidosController - Endpoints

**Estado**: ✅ Coincide correctamente (verificado)

### 13. PagosController - Endpoints

**Estado**: ✅ Coincide correctamente (verificado)

### 14. PromocionesController - Endpoints

**Estado**: ✅ Coincide correctamente (verificado)

### 15. NotificacionesController - Endpoints

**Estado**: ✅ Coincide correctamente (verificado)

## 📋 Resumen de Acciones Requeridas

### Prioridad Alta (Críticas):
1. ✅ Corregir `UsuarioController.getPerfil()` para extraer userId del token JWT
2. ✅ Corregir `UsuarioResponseDTO` para que coincida con lo que Kotlin espera
3. ✅ Agregar endpoint `/usuarios/referidos` o modificar Kotlin
4. ✅ Corregir `UsuarioUpdateDTO` para que coincida con `ActualizarPerfilRequest`
5. ✅ Agregar endpoints compatibles en `EventoController` o modificar Kotlin
6. ✅ Corregir `EventoResponseDTO` para que coincida con `EventoDto`

### Prioridad Media (Moderadas):
7. Verificar todos los DTOs de respuesta para asegurar que coincidan con Kotlin

### Prioridad Baja (Menores):
8. Documentar todas las diferencias encontradas

## 🔧 Recomendaciones

1. **Crear DTOs específicos para Kotlin**: En lugar de modificar los DTOs existentes, crear DTOs específicos que mapeen correctamente los campos que Kotlin espera.

2. **Usar un mapper centralizado**: Crear un mapper que convierta los DTOs del backend a los DTOs que Kotlin espera.

3. **Documentar todos los endpoints**: Asegurar que todos los endpoints estén documentados y que Kotlin use exactamente los mismos nombres y estructuras.

4. **Tests de integración**: Crear tests de integración que verifiquen que Kotlin y el backend están sincronizados.

