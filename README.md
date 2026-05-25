# FitClient Manager

FitClient Manager es una aplicación Android desarrollada como Proyecto de Fin de Grado del ciclo de Desarrollo de Aplicaciones Multiplataforma.

## Descripción

La aplicación está orientada a entrenadores personales y permite gestionar clientes, rutinas de entrenamiento y seguimientos físicos de forma local mediante una base de datos SQLite.

El objetivo del proyecto es ofrecer una herramienta sencilla, funcional y ampliable para organizar información básica de clientes sin depender de hojas de cálculo, notas dispersas o aplicaciones externas.

## Funcionalidades principales

- Registro de clientes.
- Consulta de clientes registrados.
- Edición y eliminación de clientes.
- Registro de rutinas asociadas a clientes existentes.
- Consulta de rutinas registradas.
- Registro de seguimientos físicos asociados a clientes existentes.
- Consulta de seguimientos físicos.
- Validaciones básicas de formularios.
- Almacenamiento local con SQLite.

## Tecnologías utilizadas

- Android Studio
- Kotlin
- XML
- SQLite
- SQLiteOpenHelper
- Git y GitHub

## Estructura general

La aplicación se organiza en diferentes pantallas o actividades:

- `MainActivity`: pantalla principal y navegación.
- `NuevoClienteActivity`: registro de clientes.
- `ListaClientesActivity`: consulta de clientes.
- `EditarEliminarClienteActivity`: edición y eliminación de clientes.
- `NuevaRutinaActivity`: registro de rutinas.
- `ListaRutinasActivity`: consulta de rutinas.
- `NuevoSeguimientoActivity`: registro de seguimientos físicos.
- `ListaSeguimientosActivity`: consulta de seguimientos.
- `ClienteDbHelper`: gestión de la base de datos SQLite.

## Estado del proyecto

Versión académica funcional.

Actualmente la aplicación permite gestionar clientes, rutinas y seguimientos de forma local. Como posibles mejoras futuras se contemplan la consulta filtrada por cliente, mejora visual de listados, autenticación, sincronización en la nube y gráficos de evolución.

## Autor

Pablo Fajardo Nogueroles