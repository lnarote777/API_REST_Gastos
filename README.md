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
| `rol`      | String | Rol del usuario (`USER` o `ADMIN`)       |

- **Endpoints**

| Método     | Endpoint             | Descripción               |
|------------|----------------------|---------------------------|
| POST       | `/usuarios/login`    | Identifica a un usuario   |
| POST       | `/usuarios/register` | registra un nuevo usuario |


### Tipos de gastos
Esta tabla contendrá la información del tipo de gasto que sea. Por ejemplo: `Comida`, `Ocio`, etc.

- **Estructura**

| Campo  | Tipo | Descripción                     |
|--------| ---- |---------------------------------|
| `id`   | Long | Identificador único del tipo de gasto |
| `name` | String | Nombre del tipo de gasto  |

- **Endpoints**

| Método     | Endpoint           | Descripción                  |
|------------|--------------------|------------------------------|
| POST       | `/tiposGastos/tipo` | Añade un nuevo tipo de gasto |


### Gastos diarios
Aquí se almacenarán todos los datos respecto al gasto.

- **Estructura**

| Campo        | Tipo      | Descripción                     |
|--------------|-----------|---------------------------------|
| `id`       | Long | Identificador único del gasto |
| `name`       | String | Nombre del gasto |
| `cantidad`   | Double | Monto del gasto |
| `fecha`      | Date   | Fecha del gasto |
| `comentario` | String | Descripción o comentario adicional |

- **Endpoints**

| Método | Endpoint                  | Descripción |
|--------|---------------------------| ----------- |
| POST   | `/gastosDiarios`          | Regisra un nuevo gasto diario|
| GET    | `/gastosDiarios`          | Recupera todos los gastos diarios del usuario |
| GET    | `/gastosDiarios/{name}` | Obtiene un gasto específico específico. |
| DELETE | `/gastosDiarios/{id}` | Elimina un gasto especifico por su id |

## Lógica de negocio

1. Control de asociaciones:
   
   - Un tipo de gasto no puede eliminarse si se tienen gastos asociados.
   
2. Validación de fechas y cantidades

    - Las cantidades deben ser positivas.
    - Las fechas deben ser válidas.
   
3. Gestión de usuarios

   - Los usuarios deben estar registrados y deben logearse/iniciar sesion para acceder a sus datos 

## Excepciones

| Situación                  | Código | Excepción                                |
|----------------------------|--------|------------------------------------------|
| Usuario no encontrado      | 404    | `NotFoundException`                      |
| Gasto diario no encontrado | 404    | `NotFoundException`                      |
| Datos inválidos            | 400    | `BadRequestException`                      |

## Restricciones de seguridad

1. Autenticación obligatoria:
   Se requiere que todos los usuarios se autentiquen con un token único para acceder a los recursos.
2. Validación de datos:
   Se aplican reglas estrictas para verificar los datos enviados por los clientes, evitando valores inválidos o maliciosos.
3. Cifrado de contraseñas:
   Las contraseñas se almacenan cifradas utilizando algoritmos como bcrypt.
4. Roles de usuario:
   Los usuarios con rol ADMIN tienen permisos adicionales, como gestionar tipos de gastos.


