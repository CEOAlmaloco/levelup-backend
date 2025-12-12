# Instrucciones para Generar APK Firmada

## Paso 1: Generar el Keystore

### En Windows:
Ejecuta el script `generar-keystore.bat`:
```bash
.\generar-keystore.bat
```

### En Linux/Mac:
Ejecuta el script `generar-keystore.sh`:
```bash
chmod +x generar-keystore.sh
./generar-keystore.sh
```

### Manualmente:
Si prefieres hacerlo manualmente, ejecuta:
```bash
keytool -genkey -v -keystore app/levelup-release.keystore -alias levelup-key -keyalg RSA -keysize 2048 -validity 9125 -storepass TU_CONTRASEÑA -keypass TU_CONTRASEÑA -dname "CN=LevelUp Gamer, OU=Development, O=LevelUp, L=Santiago, ST=Region Metropolitana, C=CL"
```

**IMPORTANTE**: 
- Guarda el keystore y las contraseñas en un lugar seguro
- Si pierdes el keystore o las contraseñas, NO podrás actualizar la app en Google Play Store
- El keystore tiene validez de 25 años (9125 días)

## Paso 2: Crear el archivo keystore.properties

Copia el archivo `keystore.properties.sample` como `keystore.properties` y completa los valores:

```properties
storeFile=app/levelup-release.keystore
storePassword=TU_CONTRASEÑA_AQUI
keyAlias=levelup-key
keyPassword=TU_CONTRASEÑA_AQUI
```

**IMPORTANTE**: 
- NO subas `keystore.properties` ni el keystore al repositorio Git
- Agrega estos archivos al `.gitignore`:
  ```
  keystore.properties
  app/*.keystore
  ```

## Paso 3: Generar la APK Firmada

### Opción 1: Desde Android Studio
1. Ve a `Build` > `Generate Signed Bundle / APK`
2. Selecciona `APK`
3. Selecciona el keystore y completa las credenciales
4. Selecciona `release` como build variant
5. Click en `Finish`

### Opción 2: Desde la línea de comandos

En Windows (PowerShell):
```powershell
cd app
.\gradlew assembleRelease
```

En Linux/Mac:
```bash
cd app
./gradlew assembleRelease
```

La APK firmada se generará en:
```
app/build/outputs/apk/release/app-release.apk
```

## Paso 4: Instalar la APK en tu dispositivo

### Opción 1: Transferencia USB
1. Conecta tu dispositivo Android al PC
2. Habilita "Depuración USB" en las opciones de desarrollador
3. Copia `app-release.apk` a tu dispositivo
4. Abre el archivo en tu dispositivo y permite la instalación desde fuentes desconocidas

### Opción 2: ADB (Android Debug Bridge)
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

## Verificar la Firma

Para verificar que la APK está correctamente firmada:
```bash
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

## Troubleshooting

### Error: "keystore.properties not found"
- Asegúrate de que el archivo `keystore.properties` existe en la raíz del proyecto
- Verifica que las rutas en el archivo sean correctas

### Error: "Keystore was tampered with, or password was incorrect"
- Verifica que la contraseña en `keystore.properties` sea correcta
- Asegúrate de que el keystore no esté corrupto

### Error: "keytool: command not found"
- Asegúrate de que Java JDK esté instalado y en tu PATH
- En Windows, busca `keytool.exe` en `C:\Program Files\Java\jdk-XX\bin\`
- En Linux/Mac, instala OpenJDK: `sudo apt install openjdk-XX-jdk` (Linux) o `brew install openjdk` (Mac)

