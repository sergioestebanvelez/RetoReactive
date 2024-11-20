Diagrama de secuencias Reto

![image](https://github.com/user-attachments/assets/bf4232d2-b2e4-4f0f-9fe3-7eb811f9e763)


# Proyecto de Reto

## Descripción

Este proyecto se trata de una implementación que incluye la gestión de usuarios y operaciones de cashouts, simulando transacciones y validaciones a través de una API RESTful. Los detalles de las operaciones y su flujo de trabajo están descritos a continuación.

## Tecnologías utilizadas

- **Java** (versión 11 o superior)
- **Spring Boot** (para crear la API)
- **Spring WebFlux** (para el manejo reactivo)
- **Mockito** (para pruebas unitarias)
- **PostgreSQL** (para la base de datos)
- **JUnit 5** (para pruebas unitarias)
- **WebTestClient** (para pruebas de integración)

## Estructura del Proyecto

- **/src/main/java**: Contiene el código fuente de la aplicación.
    - **com.bancolombia.controllers**: Controladores que exponen las API REST.
    - **com.bancolombia.services**: Servicios que implementan la lógica de negocio.
    - **com.bancolombia.domain.entities**: Modelos de datos como `User` y `Cashout`.
- **/src/test/java**: Contiene las pruebas unitarias y de integración.
- **/src/resources/application.properties**: Configuración de la base de datos y otros parámetros de la aplicación.

## Requisitos

Para ejecutar este proyecto, necesitarás tener instalados los siguientes programas:

- **Java 17 o superior**: [Descargar Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **PostgreSQL**: [Descargar PostgreSQL](https://www.postgresql.org/download/)

## Instalación

1. Clona el repositorio en tu máquina local:
2. spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

 ## Endpoints de la API

| **Método** | **URL**                          | **Descripción**                                          | **Cuerpo de la solicitud**                                                                 | **Respuesta**                                                                                           |
|------------|-----------------------------------|----------------------------------------------------------|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| `POST`     | `/users`                          | Crea un nuevo usuario.                                  | ```json { "name": "Carlos", "email": "carlos@email.com" } ```                              | ```json { "id": 1, "name": "Carlos", "email": "carlos@email.com" } ```                                  |
| `GET`      | `/users/{id}`                     | Obtiene los detalles de un usuario por su id.            | No aplica.                                                                                 | ```json { "id": 1, "name": "Carlos", "email": "carlos@email.com" } ```                                  |
| `POST`     | `/cashouts`                       | Crea un nuevo cashout para un usuario.                   | ```json { "userId": 1, "amount": 500 } ```                                                  | ```json { "userId": 1, "amount": 500, "status": "approved" } ```                                        |
| `GET`      | `/cashouts/user/{userId}`         | Obtiene todos los cashouts realizados por un usuario.    | No aplica.                                                                                 | ```json [ { "userId": 1, "amount": 500, "status": "approved" } ] ```                                   |
| `GET`      | `/cashouts`                       | Obtiene todos los cashouts realizados en el sistema.     | No aplica.                                                                                 | ```json [ { "userId": 1, "amount": 500, "status": "approved" }, { "userId": 2, "amount": 300, "status": "pending" } ] ``` |

---  
