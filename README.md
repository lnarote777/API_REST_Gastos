# API_REST_Gastos

## Idea del proyecto 
La API de Planificación de Gastos está diseñada para gestionar usuarios, clasificar sus gastos en categorías y registrar los gastos diarios.

## Justificación del proyecto
  He decidido hacer un planificador porque es muy útil y ayuda a gestionar los gastos.

## Descripción de las tablas
Se crearán 3 tablas para gestionar los datos:

- Usuarios
- Tipos de gastos
- Gastos diarios

### Usuarios
Esta tabla será donde se guarde a cada usuario que servirá para identificar a al mismo a la hora de entrar en la aplicación.

- **Estructura**

| Campo      | Tipo   | Descripción                              |
|------------|--------|------------------------------------------|
| `id`       | Long   | Identificador del usuario                |
| `name`     | String | Nombre de usuario                        |
| `password` | String | Contraseña cifrada para la autenticación |
| `roles`    | String | Rol del usuario (`USER` o `ADMIN`)       |

- **Endpoints**

| Método | Endpoint                      | Descripción                          |
|--------|-------------------------------|--------------------------------------|
| POST   | `/usuarios/login`             | Identifica a un usuario              |
| POST   | `/usuarios/register`          | Registra un nuevo usuario            |
| GET    | `/usuarios/{username}`        | Obtiene un usuario según su username |
| DELETE | `/usuarios/delete/{username}` | Elimina un usuario                   |
| UPDATE | `/usuarios/update`            | Actualiza a un usuario               |


### Tipos de gastos
Esta tabla contendrá la información del tipo de gasto que sea. Por ejemplo: `Comida`, `Ocio`, etc.

- **Estructura**

| Campo    | Tipo        | Descripción                                       |
|----------|-------------|---------------------------------------------------|
| `id`     | Long        | Identificador único del tipo de gasto             |
| `name`   | String      | Nombre del tipo de gasto                          |
| `gastos` | List<Gasto> | Lista de gastos relacionados con el tipo de gasto |


- **Endpoints**

| Método | Endpoint               | Descripción                            |
|--------|------------------------|----------------------------------------|
| POST   | `/tipos/nuevo_tipo`    | Añade un nuevo tipo de gasto           |
| GET    | `/tipos/`              | Obtiene todos los tipos de gasto       |
| GET    | `/tipos/{id}`          | Obtiene un tipo de gasto por su id     |
| UPDATE | `/tipos/update`        | Actualiza un tipo de gasto             |
| DELETE | `/tipos/delete/{name}` | Elimina un tipo de gasto por su nombre |


### Gastos diarios
Aquí se almacenarán todos los datos respecto al gasto.

- **Estructura**

| Campo         | Tipo          | Descripción                            |
|---------------|---------------|----------------------------------------|
| `id`          | Long          | Identificador único del gasto.         |
| `name`        | String        | Nombre del gasto.                      |
| `cantidad`    | Double        | Monto del gasto.                       |
| `fecha`       | LocalDateTime | Fecha del gasto.                       |
| `comentario`  | String        | Descripción o comentario adicional.    |

- **Endpoints**

| Método | Endpoint                     | Descripción                                    |
|--------|------------------------------|------------------------------------------------|
| POST   | `/gastosDiarios`             | Registra un nuevo gasto diario.                |
| GET    | `/gastosDiarios/`            | Recupera todos los gastos diarios del usuario. |
| GET    | `/gastosDiarios/{id}`        | Obtiene un gasto específico.                   |
| DELETE | `/gastosDiarios/delete/{id}` | Elimina un gasto especifico por su id.         |
| UPDATE | `/gastosDiarios/update`      | Actualiza un gasto especifico.                 |

## Lógica de negocio

1. Control de asociaciones:
   
   - Un tipo de gasto no puede eliminarse si se tienen gastos asociados.
   
2. Validación de fechas y cantidades

    - Las cantidades deben ser positivas.
    - Las fechas deben ser válidas.
   
3. Gestión de usuarios

   - Los usuarios deben estar registrados y deben logearse/iniciar sesion para acceder a sus datos 

## Excepciones

| Situación                   | Código | Excepción                |
|-----------------------------|--------|--------------------------|
| Usuario no encontrado       | 404    | `UserNotFoundException`  |
| Gasto diario no encontrado  | 404    | `GastoNotFoundException` |
| Tipo de gasto no encontrado | 404    | `TipoNotFoundException`  |
| Datos inválidos             | 400    | `BadRequestException`    |
| Sin autorización            | 401    | `NotAuthorizedException` |

## Restricciones de seguridad

1. Autenticación obligatoria:
   Se requiere que todos los usuarios se autentiquen con un token único para acceder a los recursos.
2. Validación de datos:
   Se aplican reglas estrictas para verificar los datos enviados por los clientes, evitando valores inválidos o maliciosos.
3. Cifrado de contraseñas:
   Las contraseñas se almacenan cifradas.
4. Roles de usuario:
   Los usuarios con rol ADMIN tienen permisos adicionales, como gestionar tipos de gastos.


