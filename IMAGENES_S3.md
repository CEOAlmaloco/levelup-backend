# Guía de Imágenes S3 para Android

## Formato de Imágenes

**PNG es completamente válido y soportado.** El formato de imagen no es el problema.

### Formatos Soportados
- ✅ **PNG** (.png) - Totalmente soportado
- ✅ **JPEG/JPG** (.jpg, .jpeg) - Totalmente soportado
- ✅ **WebP** (.webp) - Soportado en Android moderno
- ✅ **Base64** - Para imágenes embebidas

## Cómo Funciona la Carga de Imágenes

### 1. Configuración de S3

La URL base de S3 está configurada en:
- `config/api-config.properties`: `media.base.url=https://levelup-gamer-products.s3.us-east-1.amazonaws.com/`
- `app/build.gradle.kts`: Se lee y se inyecta como `BuildConfig.MEDIA_BASE_URL`

### 2. Resolución de URLs

El `MediaUrlResolver` resuelve automáticamente las URLs de imágenes:

```kotlin
// Si el backend envía una clave S3 como: "productos/teclado-gamer.png"
// MediaUrlResolver la convierte en:
// "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/teclado-gamer.png"

// Si el backend envía una URL completa:
// "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/teclado-gamer.png"
// Se usa tal cual
```

### 3. Formatos Aceptados por el Backend

El backend puede enviar imágenes en estos formatos:

1. **URL completa de S3**:
   ```
   https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/imagen.png
   ```

2. **Clave S3 (ruta relativa)**:
   ```
   productos/imagen.png
   productos/teclado-gamer.png
   ```

3. **Base64**:
   ```
   data:image/png;base64,iVBORw0KGgoAAAANS...
   ```

### 4. Problemas Comunes y Soluciones

#### ❌ Las imágenes no cargan

**Causas posibles:**
1. **CORS no configurado en S3**: El bucket S3 debe permitir acceso desde el dominio de la app
2. **URL base incorrecta**: Verificar `media.base.url` en `api-config.properties`
3. **Clave S3 incorrecta**: La clave debe ser la ruta exacta en el bucket (sin el bucket name)

**Solución:**
```json
// Configuración CORS para el bucket S3 (bucket policy)
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::levelup-gamer-products/*"
    }
  ]
}
```

#### ❌ Las imágenes PNG no se muestran

**Causa**: No es el formato, es la configuración.

**Verificaciones:**
1. ✅ El formato PNG es válido
2. ✅ Verificar que la URL se resuelve correctamente
3. ✅ Verificar permisos del bucket S3
4. ✅ Verificar que la imagen existe en la ruta especificada

### 5. Ejemplo de Uso en el Backend

El backend debe enviar en el DTO:

```json
{
  "id": 1,
  "nombre": "Teclado Gamer",
  "imagenUrl": "productos/teclado-gamer.png",  // Clave S3
  "imagenS3Key": "productos/teclado-gamer.png",  // Alternativa
  "imagenesUrls": ["productos/teclado-1.png", "productos/teclado-2.png"]
}
```

O con URL completa:

```json
{
  "id": 1,
  "nombre": "Teclado Gamer",
  "imagenUrl": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/teclado-gamer.png"
}
```

### 6. Debugging

Para ver qué URL se está usando, revisar los logs:

```kotlin
Log.d("MediaUrlResolver", "Resolved URL: $resolvedUrl")
```

O en el código:
```kotlin
val resolvedUrl = MediaUrlResolver.resolve(producto.imagenUrl)
Log.d("ProductoCard", "Imagen original: ${producto.imagenUrl}")
Log.d("ProductoCard", "Imagen resuelta: $resolvedUrl")
```

## Configuración de Permisos S3

### Bucket Policy (Permitir Lectura Pública)

Aplica esta política en tu bucket S3 (`levelup-gamer-products`):

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "AllowPublicRead",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::levelup-gamer-products/*"
        }
    ]
}
```

**Pasos para aplicar:**
1. Ve a tu bucket S3 en AWS Console
2. Pestaña **"Permissions"**
3. Sección **"Bucket policy"**
4. Pega el JSON de arriba
5. Guarda los cambios

### CORS Configuration

**Para Android: CORS no es crítico** (las apps Android no están sujetas a CORS como los navegadores), pero es útil si usas WebView o pruebas desde navegador.

#### Opción 1: Desarrollo (Permisivo)
```json
[
    {
        "AllowedHeaders": ["*"],
        "AllowedMethods": ["GET", "HEAD"],
        "AllowedOrigins": ["*"],
        "ExposeHeaders": []
    }
]
```

#### Opción 2: Producción (Tu Configuración Actual - Recomendada)
```json
[
    {
        "AllowedHeaders": ["*"],
        "AllowedMethods": ["GET", "HEAD"],
        "AllowedOrigins": [
            "http://localhost:5173",
            "http://localhost:3000",
            "http://10.0.2.2:8094",
            "https://levelupgamer.com"
        ],
        "ExposeHeaders": ["ETag"],
        "MaxAgeSeconds": 3000
    }
]
```

**Nota**: Tu configuración actual está bien. Si las imágenes no cargan en Android, el problema probablemente NO es CORS, sino:
1. **Bucket Policy** no aplicada (más importante)
2. Ruta incorrecta desde el backend
3. URL base incorrecta en `api-config.properties`

**Pasos para verificar/actualizar:**
1. En el bucket S3, pestaña **"Permissions"**
2. Sección **"Cross-origin resource sharing (CORS)"**
3. Usa tu configuración actual (Opción 2) - está correcta
4. Guarda

## Estructura de Carpetas en S3

Según lo que mostraste, tus imágenes están en:
```
levelup-gamer-products/
  └── img/
      ├── logo.png
      ├── teclado_rd_rgb.png
      ├── monitorasus.png
      └── ...
```

**Importante**: El backend debe enviar la ruta completa desde la raíz del bucket:
- ✅ Correcto: `img/teclado_rd_rgb.png`
- ✅ Correcto: `img/monitorasus.png`
- ❌ Incorrecto: `teclado_rd_rgb.png` (sin la carpeta `img/`)
- ❌ Incorrecto: `/img/teclado_rd_rgb.png` (con `/` al inicio)

El `MediaUrlResolver` automáticamente construirá:
```
https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png
```

## Resumen

- ✅ **PNG es válido** - No necesitas cambiar el formato
- ✅ El problema suele ser **CORS o URL incorrecta**, no el formato
- ✅ `MediaUrlResolver` resuelve automáticamente las URLs
- ✅ Verifica la configuración de S3 y los permisos del bucket
- ✅ **Aplica la Bucket Policy** para permitir lectura pública
- ✅ Las imágenes deben estar en `img/` y el backend debe enviar `img/nombre.png`

