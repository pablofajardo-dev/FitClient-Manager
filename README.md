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
- Consulta general de rutinas registradas.
- Consulta de rutinas filtradas por cliente.
- Registro de seguimientos físicos asociados a clientes existentes.
- Consulta general de seguimientos físicos.
- Consulta de seguimientos filtrados por cliente.
- Validaciones básicas y avanzadas en formularios.
- Validación de teléfono, correo electrónico y edad.
- Selección de nivel del cliente mediante desplegable.
- Selección de días de entrenamiento mediante casillas.
- Selección de fechas mediante calendario.
- Almacenamiento local con SQLite.
- Interfaz adaptada con pantallas desplazables para mejorar la compatibilidad visual.

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
- `NuevoClienteActivity`: registro de clientes y validación de datos.
- `ListaClientesActivity`: consulta de clientes registrados.
- `EditarEliminarClienteActivity`: edición y eliminación de clientes.
- `NuevaRutinaActivity`: registro de rutinas con selección de días y calendario.
- `ListaRutinasActivity`: consulta general de rutinas.
- `RutinasPorClienteActivity`: consulta de rutinas filtradas por cliente.
- `NuevoSeguimientoActivity`: registro de seguimientos físicos con calendario.
- `ListaSeguimientosActivity`: consulta general de seguimientos.
- `SeguimientosPorClienteActivity`: consulta de seguimientos filtrados por cliente.
- `ClienteDbHelper`: gestión de la base de datos SQLite.

## Base de datos

La aplicación utiliza una base de datos SQLite local compuesta por tres entidades principales:

- `Cliente`: almacena los datos personales y deportivos del cliente.
- `Rutina`: almacena rutinas de entrenamiento asociadas a clientes.
- `Seguimiento`: almacena registros físicos asociados a clientes.

Las rutinas y seguimientos se relacionan con los clientes mediante el campo `cliente_id`.

## Validaciones implementadas

La aplicación incluye validaciones para mejorar la coherencia de los datos:

- El nombre del cliente es obligatorio.
- Nombre y apellidos no pueden contener números.
- El teléfono debe tener un formato válido de 9 dígitos.
- El correo electrónico debe tener un formato válido.
- La edad debe ser un número válido.
- No se pueden registrar rutinas o seguimientos para clientes inexistentes.
- Las rutinas deben tener al menos un día de entrenamiento seleccionado.
- Las fechas se seleccionan mediante calendario para evitar errores de formato.

## Estado del proyecto

Versión académica funcional.

Actualmente la aplicación permite gestionar clientes, rutinas y seguimientos de forma local, incorporando validaciones, consultas filtradas por cliente y mejoras de usabilidad en formularios.

Como posibles mejoras futuras se contemplan la creación de una biblioteca de ejercicios, mejora visual de listados, autenticación, sincronización en la nube, gráficos de evolución y generación de informes.

## Autor

Pablo Fajardo Nogueroles