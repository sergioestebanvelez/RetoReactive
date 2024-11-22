# Proyecto - Cashout Service

Este proyecto implementa un servicio de **Cashout** donde los usuarios pueden solicitar un retiro de su saldo. La arquitectura del proyecto incluye varios microservicios, validaciones, controladores y repositorios, además de pruebas unitarias y de integración.

## Estructura del Proyecto

- **Controller**: Controladores REST para manejar las solicitudes de los usuarios.
- **Service**: Lógica de negocio que maneja las operaciones.
- **Domain**: Entidades y objetos del dominio.
- **Repository**: Repositorios para manejar la persistencia de datos.
- **Microservices**: Microservicios de pago y transacciones para realizar pagos y consultar el historial de transacciones.
- **Tests**: Casos de prueba automatizados para verificar el correcto funcionamiento del sistema.

---

## Casos de Prueba

### 1. **Prueba de Creación de Usuario**

| ID de Caso | Descripción                     | Entrada                                   | Resultado Esperado                             |
|------------|----------------------------------|-------------------------------------------|------------------------------------------------|
| TC01       | Crear un nuevo usuario           | `{"name": "John Doe", "email": "john.doe@example.com", "balance": 100.0}` | `{"id": 1, "name": "John Doe", "email": "john.doe@example.com", "balance": 100.0}` |
| TC02       | Crear un usuario sin balance     | `{"name": "Jane Doe", "email": "jane.doe@example.com", "balance": null}` | HTTP 400 (Bad Request) |
| TC03       | Crear un usuario sin email       | `{"name": "Jake Doe", "balance": 100.0}`  | HTTP 400 (Bad Request - 'email' es obligatorio) |

### 2. **Prueba de Actualización de Saldo de Usuario**

| ID de Caso | Descripción                     | Entrada                                   | Resultado Esperado                             |
|------------|----------------------------------|-------------------------------------------|------------------------------------------------|
| TC04       | Actualizar saldo correctamente   | `{"amount": 50.0}`                        | `{"id": 1, "name": "John Doe", "email": "john.doe@example.com", "balance": 150.0}` |
| TC05       | Actualizar saldo a valor negativo| `{"amount": -30.0}`                       | HTTP 400 (Bad Request)                         |

### 3. **Prueba de Cashout**

| ID de Caso | Descripción                     | Entrada                                   | Resultado Esperado                             |
|------------|----------------------------------|-------------------------------------------|------------------------------------------------|
| TC06       | Crear cashout válido             | `{"userId": 1, "amount": 50.0}`           | `{"id": 1, "userId": 1, "amount": 50.0}`       |
| TC07       | Crear cashout con saldo insuficiente | `{"userId": 1, "amount": 200.0}`          | HTTP 400 (Balance insuficiente)                |
| TC08       | Crear cashout con fallo de pago   | `{"userId": 1, "amount": 50.0}`           | HTTP 400 (Payment failed)                      |

### 4. **Prueba de Historial de Cashouts**

| ID de Caso | Descripción                     | Entrada                                   | Resultado Esperado                             |
|------------|----------------------------------|-------------------------------------------|------------------------------------------------|
| TC09       | Obtener historial de cashouts    | `{"userId": 1}`                           | `[{ "id": 1, "userId": 1, "amount": 50.0 }, { "id": 2, "userId": 1, "amount": 30.0 }]` |

---



