# Endpoints de la App

Documentación de los endpoints que consume la aplicación móvil.

## Microservicios

### Auth (msvc-auth)
- `POST /auth/login` - Login de usuario
- `POST /auth/register` - Registro de usuario
- `POST /auth/logout` - Cerrar sesión

### Productos (msvc-productos)
- `GET /productos` - Lista de productos
- `GET /productos/{id}` - Detalle de producto
- `GET /productos/categoria/{categoriaId}` - Por categoría
- `GET /productos/buscar?nombre=` - Búsqueda
- `GET /productos/carrusel` - Imágenes del carrusel
- `GET /productos/logo` - Logo de la app

### Eventos (msvc-eventos)
- `GET /eventos` - Lista de eventos
- `GET /eventos/proximos` - Próximos eventos
- `GET /eventos/{id}` - Detalle de evento

### Usuarios (msvc-usuario)
- `GET /usuarios/perfil` - Perfil del usuario
- `PUT /usuarios/perfil` - Actualizar perfil
- `DELETE /usuarios/{id}` - Eliminar cuenta
- `GET /usuarios` - Lista de usuarios (admin)
- `PUT /usuarios/{id}/password` - Cambiar contraseña

### Carrito (msvc-carrito)
- `GET /carrito` - Obtener carrito
- `POST /carrito/agregar` - Agregar producto
- `PUT /carrito/actualizar` - Actualizar cantidad
- `DELETE /carrito/{itemId}` - Eliminar item

### Referidos (msvc-referidos)
- `GET /puntos/usuario/{id}` - Puntos del usuario
- `POST /puntos/usuario/{id}/inicio-sesion` - Bonificación diaria
- `POST /puntos/usuario/{id}/canje-codigo` - Canjear código evento
- `POST /puntos/usuario/{id}/canje` - Canjear puntos
- `GET /referidos/codigo/{usuarioId}` - Código de referido

### Contenido (msvc-contenido)
- `GET /contenido/articulos/publicados` - Blogs publicados
- `GET /contenido/articulos/destacados` - Blogs destacados
- `GET /contenido/articulos/categoria/{categoria}` - Por categoría

## API Externa

- **OpenStreetMap**: Mapas para eventos (OSMDroid)
- **S3 AWS**: Almacenamiento de imágenes

## Configuración

Los endpoints se configuran en `config/api-config.properties`:
- `gateway.url.debug` - URL para emulador
- `gateway.url.device` - URL para dispositivo físico
- `gateway.url.release` - URL de producción
- `gateway.api.key` - API Key del gateway
- `media.base.url` - URL base de imágenes S3
