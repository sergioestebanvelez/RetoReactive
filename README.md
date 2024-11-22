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

### **1. Usuario**

#### **1.1 Crear Usuario**

| ID de Caso | Descripción                       | Método | URL                | Entrada                                                                                                 | Resultado Esperado                                                                                           |
|------------|------------------------------------|--------|--------------------|---------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| TC01       | Crear un nuevo usuario (éxito)     | POST   | /users             | `{"name": "John Doe", "email": "john.doe@example.com", "balance": 100.0}`                                | `{"id": 1, "name": "John Doe", "email": "john.doe@example.com", "balance": 100.0}`                           |
| TC02       | Crear un usuario sin email         | POST   | /users             | `{"name": "Jane Doe", "balance": 100.0}`                                                                 | HTTP 400 (Bad Request - 'email' es obligatorio)                                                               |
| TC03       | Crear un usuario sin balance       | POST   | /users             | `{"name": "Jake Doe", "email": "jake.doe@example.com", "balance": null}`                                 | HTTP 400 (Bad Request - 'balance' no puede ser nulo)                                                          |

#### **1.2 Obtener Usuario por ID**

| ID de Caso | Descripción                       | Método | URL                | Entrada              | Resultado Esperado                                                                                               |
|------------|------------------------------------|--------|--------------------|----------------------|------------------------------------------------------------------------------------------------------------------|
| TC04       | Obtener usuario por ID (éxito)     | GET    | /users/{id}        | `{id: 1}`            | `{"id": 1, "name": "John Doe", "email": "john.doe@example.com", "balance": 100.0}`                              |
| TC05       | Obtener usuario con ID no existente| GET    | /users/{id}        | `{id: 999}`          | HTTP 404 (Not Found)                                                                                             |

#### **1.3 Actualizar Saldo del Usuario**

| ID de Caso | Descripción                       | Método | URL                     | Entrada                              | Resultado Esperado                                                                                               |
|------------|------------------------------------|--------|-------------------------|--------------------------------------|------------------------------------------------------------------------------------------------------------------|
| TC06       | Actualizar saldo del usuario (éxito)| PUT    | /users/{id}/balance      | `{"amount": 50.0}`                   | `{"id": 1, "name": "John Doe", "email": "john.doe@example.com", "balance": 150.0}`                              |
| TC07       | Actualizar saldo con valor negativo| PUT    | /users/{id}/balance      | `{"amount": -30.0}`                  | HTTP 400 (Bad Request)                                                                                           |
| TC08       | Actualizar saldo con saldo insuficiente | PUT | /users/{id}/balance      | `{"amount": 200.0}`                  | HTTP 400 (Bad Request - Saldo insuficiente)                                                                      |

---

### **2. Cashouts**

#### **2.1 Crear Cashout**

| ID de Caso | Descripción                          | Método | URL          | Entrada                                      | Resultado Esperado                                                                                        |
|------------|---------------------------------------|--------|--------------|----------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| TC09       | Crear cashout válido (éxito)          | POST   | /cashouts    | `{"userId": 1, "amount": 50.0}`              | `{"id": 1, "userId": 1, "amount": 50.0}`                                                                    |
| TC10       | Crear cashout con saldo insuficiente  | POST   | /cashouts    | `{"userId": 1, "amount": 200.0}`             | HTTP 400 (Bad Request - Saldo insuficiente)                                                                |
| TC11       | Crear cashout con fallo en el pago    | POST   | /cashouts    | `{"userId": 1, "amount": 50.0}`              | HTTP 400 (Payment Failed)                                                                                   |

#### **2.2 Obtener Historial de Cashouts**

| ID de Caso | Descripción                             | Método | URL                | Entrada                                      | Resultado Esperado                                                                                               |
|------------|------------------------------------------|--------|--------------------|----------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| TC12       | Obtener historial de cashouts (éxito)    | GET    | /cashouts/user/{userId} | `{userId: 1}`                              | `[{ "id": 1, "userId": 1, "amount": 50.0 }, { "id": 2, "userId": 1, "amount": 30.0 }]`                           |
| TC13       | Obtener historial de cashouts para un usuario inexistente | GET | /cashouts/user/{userId} | `{userId: 999}`                             | HTTP 404 (Not Found)                                                                                             |

---

## Descripción de los Endpoints

### **Usuario**

1. **Crear Usuario**

   - **Método**: POST
   - **URL**: `/users`
   - **Entrada**:

     ```json
     {
       "name": "John Doe",
       "email": "john.doe@example.com",
       "balance": 100.0
     }
     ```

   - **Salida**:

     ```json
     {
       "id": 1,
       "name": "John Doe",
       "email": "john.doe@example.com",
       "balance": 100.0
     }
     ```

2. **Obtener Usuario por ID**

   - **Método**: GET
   - **URL**: `/users/{id}`
   - **Entrada**: `id` (Long)
   - **Salida**:

     ```json
     {
       "id": 1,
       "name": "John Doe",
       "email": "john.doe@example.com",
       "balance": 100.0
     }
     ```

3. **Actualizar Balance del Usuario**

   - **Método**: PUT
   - **URL**: `/users/{id}/balance`
   - **Entrada**:

     ```json
     {
       "amount": 50.0
     }
     ```

   - **Salida**:

     ```json
     {
       "id": 1,
       "name": "John Doe",
       "email": "john.doe@example.com",
       "balance": 150.0
     }
     ```

### **Cashouts**

1. **Crear Cashout**

   - **Método**: POST
   - **URL**: `/cashouts`
   - **Entrada**:

     ```json
     {
       "userId": 1,
       "amount": 50.0
     }
     ```

   - **Salida**:

     ```json
     {
       "id": 1,
       "userId": 1,
       "amount": 50.0
     }
     ```

2. **Obtener Cashouts por Usuario**

   - **Método**: GET
   - **URL**: `/cashouts/user/{userId}`
   - **Entrada**: `userId` (Long)
   - **Salida**:

     ```json
     [
       {
         "id": 1,
         "userId": 1,
         "amount": 50.0
       },
       {
         "id": 2,
         "userId": 1,
         "amount": 30.0
       }
     ]
     ```

---

## Cómo Ejecutar las Pruebas

Para ejecutar las pruebas en este proyecto, sigue estos pasos:

---
![image](https://github.com/user-attachments/assets/70ef9e6c-1230-4777-9340-1c5971364245)




