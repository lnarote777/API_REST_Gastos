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

| Campo | Tipo | Descripción |
| ----- | ---- | ----------- |
| `id` | Long | Identificador único del usuario |
| `name` | String | Nombre de usuario |
| `password` | String | Contraseña cifrada para la autenticación |

- **Endpoints**

| Método     | Endpoint             | Descripción               |
|------------|----------------------|---------------------------|
| POST       | `/usuarios/login`    | Identifica a un usuario   |
| POST       | `/usuarios/register` | registra un nuevo usuario |


### Tipos de gastos
Esta tabla contendrá la información del tipo de gasto que sea. Por ejemplo: `Comida`, `Ocio`, etc.

- **Estructura**

| Campo      | Tipo | Descripción                     |
|------------| ---- |---------------------------------|
| `id`       | Long | Identificador único del tipo de gasto |
| `tipo`     | String | Nombre del tipo de gasto  |

- **Endpoints**

| Método     | Endpoint           | Descripción                  |
|------------|--------------------|------------------------------|
| POST       | `/tiposGastos/tipo` | Añade un nuevo tipo de gasto |


### Gastos diarios
Aquí se almacenarán todos los datos respecto al gasto.

- **Estructura**

| Campo        | Tipo   | Descripción |
|--------------|--------| ----------- |
| `name`       | String | Identificador único del usuario |
| `cantidad`   | Double | Nombre de usuario |
| `fecha`      | Date   | Contraseña cifrada para la autenticación |
| `comentario` | String | Contraseña cifrada para la autenticación |

- **Endpoints**

| Método | Endpoint                  | Descripción |
|--------|---------------------------| ----------- |
| POST   | `/gastosDiarios`          | Identificador único del usuario |
| GET    | `/gastosDiarios`          | Nombre de usuario |
| GET    | `/gastosDiarios/{nombre}` | Contraseña cifrada para la autenticación |
| DELETE | `/gastosDiarios/{nombre}` | Contraseña cifrada para la autenticación |

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

1. Autenticación de usuarios
2. Validación de datos
3. Cifrado de contraseñas



