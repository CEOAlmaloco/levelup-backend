# Configuración de URL para AWS en Kotlin

## Estado Actual

✅ **Configuración automática por Build Type**

La aplicación está configurada para usar diferentes URLs según el tipo de build:

- **DEBUG (Desarrollo)**: `http://10.0.2.2:8094/` (Emulador Android)
- **RELEASE (Producción)**: `https://api-gateway.tu-dominio.com/` (AWS - **DEBES CAMBIAR ESTA URL**)

## 📋 Pasos para Configurar AWS

### 1. Desplegar el Backend en AWS

Primero, desplegar el backend siguiendo `AWS_DEPLOY_GUIA_COMPLETA.md` para obtener:
- La URL del API Gateway (ej: `https://api-levelup.aws-region.elb.amazonaws.com:8094/`)
- O la URL del dominio personalizado (ej: `https://api.levelup-tu-dominio.com/`)

### 2. Actualizar URL en `build.gradle.kts`

**Ubicación**: `app/build.gradle.kts`

**Línea 40** - Cambiar la URL de producción:

```kotlin
release {
    // ... otras configuraciones ...
    
    // ⚠️ CAMBIAR ESTA URL DESPUÉS DE DESPLEGAR EN AWS
    buildConfigField("String", "API_BASE_URL", "\"https://TU-URL-AWS-AQUI/\"")
    
    // Ejemplos:
    // buildConfigField("String", "API_BASE_URL", "\"https://api-levelup.us-east-1.elb.amazonaws.com:8094/\"")
    // buildConfigField("String", "API_BASE_URL", "\"https://api.levelup-tu-dominio.com/\"")
}
```

### 3. Recompilar la App para Producción

```bash
# Generar APK de producción con la nueva URL
./gradlew assembleRelease

# O generar AAB para Google Play
./gradlew bundleRelease
```

### 4. Verificar la Configuración

La URL se obtiene automáticamente desde `BuildConfig`:

```kotlin
// En ApiConfig.kt
private val BASE_URL = BuildConfig.API_BASE_URL
```

## 🔍 Cómo Funciona

### Desarrollo (Debug Build)
- **URL**: `http://10.0.2.2:8094/` (Emulador Android)
- **API Key**: `levelup-2024-secret-api-key-change-in-production`
- **Uso**: Para desarrollo y pruebas locales

### Producción (Release Build)
- **URL**: Configurada en `build.gradle.kts` (línea 40)
- **API Key**: `levelup-2024-secret-api-key-change-in-production`
- **Uso**: Para distribución en Google Play o APK final

## 📝 Notas Importantes

1. **No cambiar código**: Solo cambiar la URL en `build.gradle.kts` línea 40
2. **Recompilar**: Después de cambiar la URL, recompilar la app
3. **HTTPS**: La URL de AWS debe usar HTTPS (no HTTP)
4. **Barra final**: La URL debe terminar con `/` (ej: `https://api.com/`)

## 🚀 Ejemplo de Configuración

### Antes del Despliegue (Desarrollo)
```kotlin
buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8094/\"")
```

### Después del Despliegue en AWS
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://api-levelup.us-east-1.elb.amazonaws.com:8094/\"")
// O con dominio personalizado:
buildConfigField("String", "API_BASE_URL", "\"https://api.levelup-tu-dominio.com/\"")
```

## ✅ Verificación

Para verificar que la URL está correcta:

1. Compilar la app en modo Release
2. Instalar en un dispositivo físico o emulador
3. Abrir los logs de Android (logcat)
4. Buscar peticiones HTTP - deberían apuntar a la URL de AWS

## 🔐 Seguridad

- La API Key está configurada en `build.gradle.kts`
- Para producción, considera usar variables de entorno o un sistema de gestión de secretos
- No subir `build.gradle.kts` con API Keys reales a repositorios públicos

