# MELI Search
#### MELI Search tiene como objetivo poder realizar una búsqueda de productos así como poder visualizar el detalle y ubicación del producto mediante la API abierta de Mercado Libre.

## Previsualización Portrait
<img src="https://github.com/fernandomeg/MELISearch/assets/57393785/3dc088a0-131c-438f-aa94-4afd7b5a0049" alt="" width="200"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/75aaeed9-4166-4a21-84ee-f0f035d7d0f6" alt="" width="200"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/f5185c91-0dd1-4dbb-b748-963dccb5504c" alt="" width="200"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/ad32c8f4-764b-4c6a-baf5-7fb8b3de0cc7" alt="" width="200"/>

## Previsualización Landscape
<img src="https://github.com/fernandomeg/MELISearch/assets/57393785/61266372-eb9f-4899-9781-d77404728f67" alt="" width="300"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/92b29d60-6eb7-41e8-8c62-24363273d040" alt="" width="300"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/c1c6c89a-e4d2-4892-9851-4bb00411ea85" alt="" width="300"/>

## Arquitectura de MELI Search
Este proyecto implementa un patrón **MVVM Clean architecture** que nos permite tener una separación clara de las responsabilidades de las diferentes capas de nuestra aplicación facilitando así el mantenimiento del código y la modularización del mismo. Este diagrama representa el tipo de arquitectura que se implementó en el proyecto:
<img src="https://github.com/fernandomeg/MELISearch/assets/57393785/c1f45585-7c7f-4bd3-aeeb-02c8a2a69e22" alt="" height="150"/>

### Manejo de Casos de Error
Para poder mejorar el **performance** de la aplicación y manejar comportamientos inesperados se abordaron algunas técnicas que nos permitieran contemplar dichos escenarios:

- Creación de clase **Resource** para poder representar la respuesta de nuestras consultas validando si fue exitosa o si falló.
- Validación y excepciones controladas si nuestra clase **Resource** informó de un comportamiento inesperado.
- Implementación de **Timber** como herramienta de Logging para registrar la información y facilitar la depuración y seguimiento de problemas.
- UI adaptada para mostrar al usuario algún error con la petición al servicio de Mercado Libre.

<img src="https://github.com/fernandomeg/MELISearch/assets/57393785/5a3524ff-be44-4d69-ac03-10b1f4d48653" alt="" width="200"/>  <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/1e4dbb23-6879-4018-936b-d0dd7c609b63" alt="" width="200"/>

### Patrones de Diseño
- Estructurales: Adapters
- Creacionales: Dependency Injection HILT y Singleton
- Comportamiento: Observers

### Asegurar la calidad del proyecto
Para poder asegurar la calidad del desarrollo en este proyecto se implementaron **UNIT TESTS** con **JUnit5** y **Mockito** tanto a los **Casos de Uso** como al manejo del **View Model** con el fin de poder verificar que la información que se transmite entre las distintas capas del proyecto sea coherente con lo que se va a mostrar al usuario.

Se realizó la implementación de Canary Leaks en el proyecto MELI Search para poder detectar y erradicar **Memory Leaks** dentro del funcionamiento de la aplicación.

<img src="https://github.com/fernandomeg/MELISearch/assets/57393785/d38b0acf-7acb-4e43-85d2-7d3e94696cc7" alt="" width="200"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/ff741d7b-0810-4d31-8980-6a477b30b288" alt="" width="200"/> <img src="https://github.com/fernandomeg/MELISearch/assets/57393785/ad32444d-4532-41a7-88d8-8524f2909cc2" alt="" width="200"/>

### UI Material 3
