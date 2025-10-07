# levelup-backend
LevelUP project in kotlin

## Descripción de la Rama: `copilot/update-user-profile-functionality`

Esta rama contiene la implementación base de una aplicación Android para **LevelUp-Gamer**, desarrollada en Kotlin con Jetpack Compose y siguiendo arquitectura MVVM.

### 🎯 Características Principales

#### 1. **Sistema de Autenticación y Registro**
   - Pantalla de Login (`LoginScreen.kt`)
   - Pantalla de Registro completa con validación de formularios (`RegisterScreen.kt`)
   - ViewModels para gestión de estado: `LoginViewModel` y `UsuarioViewModel`

#### 2. **Validaciones de Usuario**
   - Validador robusto de datos de usuario (`UsuarioValidator.kt`)
   - Validaciones implementadas:
     - Nombre y apellidos (longitud mínima/máxima)
     - Email (formato válido)
     - Contraseña (requisitos de seguridad)
     - RUT chileno (validación con dígito verificador)
     - Teléfono (formato válido)
     - Fecha de nacimiento (mayor de edad)
   - Mensajes de error personalizados en tiempo real

#### 3. **Gestión de Ubicación (Chile)**
   - Data classes para Región y Comuna (`Region.kt`, `Comuna.kt`)
   - Provider con todas las regiones y comunas de Chile (`RegionesYComunasProvider.kt`)
   - ViewModel para gestión de ubicación (`UbicacionViewModel.kt`)
   - Dropdowns dinámicos que filtran comunas según región seleccionada

#### 4. **Componentes UI Reutilizables**
   
   **Inputs:**
   - `LevelUpTextField` - Campo de texto personalizado
   - `LevelUpPasswordField` - Campo de contraseña con visibilidad toggle
   - `LevelUpFechaNacimientoField` - Selector de fecha con validación de edad
   - `LevelUpSwitchField` - Switch personalizado para términos y condiciones
   - `LevelUpDropdownMenu` - Menú desplegable reutilizable
   - `ErrorSupportingText` - Componente para mostrar errores de validación
   
   **Buttons:**
   - `LevelUpButton` - Botón principal de la app
   - `MenuButton` - Botón para menús
   
   **Dialogs & Overlays:**
   - `LevelUpAlertDialog` - Diálogo de alerta personalizado
   - `LevelUpLoadingOverlay` - Overlay de carga con indicador de progreso

#### 5. **Sistema de Diseño**
   - Tema personalizado de LevelUp (`Theme.kt`)
   - Color schemes para modo claro y oscuro (`ColorSchemes.kt`)
   - Tokens de color semánticos (`ColorTokens.kt`, `SemanticColors.kt`)
   - Tipografía personalizada (`Type.kt`)
   - Sistema de dimensiones responsivo (`Dimens.kt`)
   - Colores personalizados para componentes (`ButtonColors.kt`, `LevelUpTextFieldColors.kt`, `LevelUpSwitchColors.kt`)

#### 6. **Navegación**
   - Sistema de navegación con Compose Navigation (`AppNavigation.kt`)
   - Rutas configuradas para Login y Registro

#### 7. **Utilidades**
   - `FechaUtils.kt` - Funciones para manejo y formato de fechas
   - Cálculo de edad
   - Formateo de fechas

### 📁 Estructura del Proyecto

```
app/src/main/java/com/example/levelupprueba/
├── model/
│   ├── auth/          # Estados de autenticación (LoginUiState)
│   ├── registro/      # Estados de registro (RegistroUiState)
│   ├── ubicacion/     # Modelos de Región y Comuna
│   └── usuario/       # Modelos y validadores de usuario
├── ui/
│   ├── components/    # Componentes reutilizables
│   │   ├── buttons/
│   │   ├── dialogs/
│   │   ├── dropdown/
│   │   ├── inputs/
│   │   ├── overlays/
│   │   └── switches/
│   ├── screens/       # Pantallas de la app
│   │   └── home/      # LoginScreen y RegisterScreen
│   └── theme/         # Sistema de diseño
├── viewmodel/         # ViewModels MVVM
├── navigation/        # Configuración de navegación
├── utils/            # Utilidades generales
└── MainActivity.kt   # Actividad principal
```

### 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Estado:** StateFlow y MutableStateFlow
- **Navegación:** Navigation Compose
- **Material Design:** Material 3
- **Coroutines:** Para operaciones asíncronas
- **Dependencias de Red (configuradas):**
  - Retrofit 2.9.0
  - Gson Converter
  - OkHttp Logging Interceptor

### 📊 Estado del Proyecto

**Total de líneas de código Kotlin:** ~2,118 líneas

**Commits en esta rama:**
1. `b11811a` - feat: Agrega mejoras en las validaciones
   - Implementación inicial con componentes UI
   - Sistema de validaciones
   - Integración de regiones y comunas
   - Documentación parcial de componentes

2. `e62184a` - Initial plan
   - Planificación de funcionalidad de actualización de perfil

### 🎨 Características de UX

- **Validación en tiempo real:** Feedback inmediato al usuario
- **Diseño adaptativo:** Soporte para diferentes tamaños de pantalla
- **Modo oscuro:** Soporte completo para tema claro y oscuro
- **Accesibilidad:** Componentes optimizados para lectores de pantalla
- **Edge-to-edge:** Interfaz moderna con soporte para pantallas completas
- **Teclado inteligente:** Manejo automático del teclado en pantalla

### 🚀 Próximos Pasos Sugeridos

Esta rama parece estar preparada para implementar funcionalidad de actualización de perfil de usuario. Los componentes base están listos para:
- Edición de datos de usuario
- Actualización de ubicación
- Cambio de contraseña
- Integración con backend (Retrofit ya configurado)

### 📝 Notas Técnicas

- **API Level mínimo:** 24 (Android 7.0)
- **API Level objetivo:** 36
- **Versión de Kotlin:** Compatible con Compose
- **JVM Target:** 11
- El proyecto incluye configuración para ProGuard (actualmente deshabilitada) 
