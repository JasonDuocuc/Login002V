# Proyecto Evaluación 4 - Desarrollo de Aplicaciones Móviles

Este repositorio contiene el código fuente de la aplicación móvil nativa desarrollada para la asignatura DSY1105. El proyecto consiste en una solución integral desarrollada en Kotlin que implementa patrones de diseño modernos y consumo de servicios distribuidos.

## Equipo de Desarrollo
* Francisco Torres
* Jason Mancilla
* Manuel Vallegos

## Descripción del Proyecto
La aplicación funciona como un sistema de gestión de información que permite al usuario interactuar con datos locales y remotos. El desarrollo se centra en la implementación de una arquitectura escalable y la integración de servicios externos.

### Características Técnicas
* **Arquitectura:** MVVM (Model-View-ViewModel) con Clean Architecture.
* **Interfaz de Usuario:** Implementada mediante Jetpack Compose.
* **Servicios Web:** Consumo de API REST externa mediante Retrofit y conexión a microservicios propios.
* **Persistencia:** Manejo eficiente de estados y datos locales.

## Información sobre el Entregable (APK)
El archivo ejecutable **app-release.apk** se encuentra disponible en este repositorio.

**Nota sobre la Firma Digital (.jks):**
Siguiendo protocolos de seguridad estándar en el desarrollo de software, el archivo de almacenamiento de claves (Keystore) utilizado para la firma del APK no se ha incluido en este repositorio público. No obstante, el archivo APK adjunto ha sido compilado en modo release y firmado correctamente en el entorno local, cumpliendo con los requisitos de funcionamiento e instalación.

## Instrucciones de Despliegue
Para ejecutar el proyecto en un entorno de desarrollo:

1. Clonar este repositorio en el equipo local.
2. Abrir el directorio raíz utilizando Android Studio.
3. Permitir la sincronización de las dependencias de Gradle.
4. Ejecutar la aplicación en un emulador (API 28 o superior) o en un dispositivo físico con depuración USB habilitada.
