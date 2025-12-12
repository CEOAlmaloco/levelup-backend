@echo off
REM Script para generar el keystore de producción para LevelUp
REM Este script genera un keystore con validez de 25 años

echo ========================================
echo Generador de Keystore para LevelUp
echo ========================================
echo.

set KEYSTORE_PATH=app\levelup-release.keystore
set KEY_ALIAS=levelup-key
set VALIDITY_YEARS=25

echo Ingresa la contraseña para el keystore (se usara para storePassword y keyPassword):
set /p STORE_PASSWORD=

echo.
echo Generando keystore en: %KEYSTORE_PATH%
echo Alias: %KEY_ALIAS%
echo Validez: %VALIDITY_YEARS% años
echo.

keytool -genkey -v -keystore %KEYSTORE_PATH% -alias %KEY_ALIAS% -keyalg RSA -keysize 2048 -validity %VALIDITY_YEARS%000 -storepass %STORE_PASSWORD% -keypass %STORE_PASSWORD% -dname "CN=LevelUp Gamer, OU=Development, O=LevelUp, L=Santiago, ST=Region Metropolitana, C=CL"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Keystore generado exitosamente!
    echo ========================================
    echo.
    echo Ahora crea el archivo keystore.properties en la raiz del proyecto con:
    echo.
    echo storeFile=%KEYSTORE_PATH%
    echo storePassword=%STORE_PASSWORD%
    echo keyAlias=%KEY_ALIAS%
    echo keyPassword=%STORE_PASSWORD%
    echo.
    echo IMPORTANTE: Guarda estas credenciales en un lugar seguro!
    echo Si pierdes el keystore o las contraseñas, no podras actualizar la app en Google Play.
    echo.
) else (
    echo.
    echo ERROR: No se pudo generar el keystore.
    echo Verifica que keytool este en tu PATH o usa la ruta completa de Java.
    echo.
)

pause

