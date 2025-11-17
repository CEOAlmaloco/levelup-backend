# Guía de Filtros de Logcat para Android

## Filtros para Ver Imágenes

### Filtro Básico para Imágenes
```
package:com.example.levelupprueba tag:MediaUrlResolver|ProductoCard|AsyncImage
```

### Filtro Detallado (Múltiples Tags)
```
package:com.example.levelupprueba tag:MediaUrlResolver|ProductoCard|ProductoRepository|AsyncImage
```

### Filtro Solo Errores de Imágenes
```
package:com.example.levelupprueba level:ERROR tag:MediaUrlResolver|ProductoCard|Coil
```

### Filtro Combinado (Imágenes + Productos)
```
package:com.example.levelupprueba tag:MediaUrlResolver|ProductoCard|ProductoRepository|ProductoViewModel
```

## Filtros Útiles para Debugging

### Ver Todas las Resoluciones de URLs
```
package:com.example.levelupprueba tag:MediaUrlResolver
```

### Ver Carga de Productos e Imágenes
```
package:com.example.levelupprueba tag:ProductoRepository|ProductoCard|ProductoViewModel
```

### Ver Errores de Red e Imágenes
```
package:com.example.levelupprueba level:ERROR tag:ProductoRepository|MediaUrlResolver|ApiConfig|OkHttp
```

### Ver Todo lo Relacionado con S3
```
package:com.example.levelupprueba tag:MediaUrlResolver|S3|AsyncImage
```

## Cómo Usar en Android Studio

### Método 1: Filtro por Tag
1. Abre **Logcat** en Android Studio
2. En el campo de búsqueda, escribe:
   ```
   tag:MediaUrlResolver|ProductoCard
   ```
3. Presiona Enter

### Método 2: Filtro por Package
1. En Logcat, haz clic en el ícono de filtro (🔍)
2. Selecciona **"Edit Filter Configuration"**
3. Crea un nuevo filtro:
   - **Name**: "Imágenes y Productos"
   - **Package Name**: `com.example.levelupprueba`
   - **Tag**: `MediaUrlResolver|ProductoCard|ProductoRepository`
   - **Log Level**: `Debug` o `Verbose`

### Método 3: Filtro por Regex
```
package:com.example.levelupprueba tag:^(MediaUrlResolver|ProductoCard|AsyncImage)
```

## Ejemplos de Logs que Verás

### Cuando se Resuelve una URL de S3
```
D/MediaUrlResolver: Resolved S3 URL: productos/teclado.png -> https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/teclado.png
```

### Cuando se Carga una Imagen en ProductoCard
```
D/ProductoCard: Producto: Teclado Gamer RGB
D/ProductoCard: Imagen original: productos/teclado.png
D/ProductoCard: Imagen resuelta: https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/teclado.png
```

### Cuando se Obtienen Productos Destacados
```
D/ProductoRepository: Total productos: 19
D/ProductoRepository: Producto: Teclado, disponible: true, destacado: false, rating: 4.5
D/ProductoRepository: Productos destacados encontrados: 5
```

## Filtros para Problemas Específicos

### ANR (Application Not Responding)
```
level:ERROR tag:ActivityManager|ANR
```

### Errores de Red
```
package:com.example.levelupprueba level:ERROR tag:OkHttp|Retrofit|ApiConfig
```

### Errores de Carga de Imágenes
```
package:com.example.levelupprueba level:ERROR tag:Coil|AsyncImage|MediaUrlResolver
```

### Todo lo de la App (Debug Completo)
```
package:com.example.levelupprueba
```

## Consejos

1. **Usa filtros guardados**: Guarda tus filtros favoritos en Android Studio para acceso rápido
2. **Combina con nivel de log**: Agrega `level:DEBUG` o `level:ERROR` según necesites
3. **Filtra por proceso**: Si hay múltiples instancias, usa `pid:XXXX` para filtrar por proceso específico
4. **Limpia logs**: Usa el botón de limpiar (🗑️) antes de probar para ver solo logs nuevos

## Filtro Rápido Recomendado

Para ver todo lo relacionado con imágenes y productos:
```
package:com.example.levelupprueba tag:MediaUrlResolver|ProductoCard|ProductoRepository|ProductoViewModel level:DEBUG
```

