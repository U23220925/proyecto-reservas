# API Documentation - Sistema de Gestión de Reservas

## Base URL
```
http://localhost:8080/api
```

## Clientes

### Registrar Cliente
**POST** `/clientes`

Registra un nuevo cliente en el sistema.

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@email.com",
  "telefono": "+51 987 654 321",
  "direccion": "Av. Principal 123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Cliente registrado exitosamente",
  "data": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@email.com",
    "telefono": "+51 987 654 321",
    "direccion": "Av. Principal 123",
    "fechaRegistro": "2024-10-19 15:30:00"
  }
}
```

### Modificar Cliente
**PUT** `/clientes/{id}`

Actualiza la información de un cliente existente.

**Request Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez",
  "email": "juan.perez@email.com",
  "telefono": "+51 987 654 321",
  "direccion": "Av. Nueva 456"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Cliente actualizado exitosamente",
  "data": { ... }
}
```

### Consultar Cliente por ID
**GET** `/clientes/{id}`

Obtiene la información de un cliente específico.

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@email.com",
    "telefono": "+51 987 654 321",
    "direccion": "Av. Principal 123",
    "fechaRegistro": "2024-10-19 15:30:00"
  }
}
```

### Consultar Todos los Clientes
**GET** `/clientes`

Obtiene la lista de todos los clientes.

**Response:**
```json
{
  "success": true,
  "data": [ ... ],
  "total": 10
}
```

### Buscar Clientes
**GET** `/clientes/buscar?termino={termino}&nombre={nombre}`

Busca clientes por término (nombre, apellido o email) o por nombre específico.

**Parámetros:**
- `termino` (opcional): Término de búsqueda general
- `nombre` (opcional): Búsqueda por nombre

**Ejemplo:**
```
GET /clientes/buscar?nombre=Juan
GET /clientes/buscar?termino=perez
```

### Eliminar Cliente
**DELETE** `/clientes/{id}`

Elimina un cliente del sistema.

**Response:**
```json
{
  "success": true,
  "message": "Cliente eliminado exitosamente"
}
```

---

## Servicios

### Registrar Servicio
**POST** `/servicios`

Registra un nuevo servicio.

**Request Body:**
```json
{
  "nombre": "Corte de Cabello",
  "descripcion": "Corte de cabello profesional con productos premium",
  "categoria": "Peluquería",
  "precio": 50.00,
  "duracionMinutos": 45,
  "activo": true
}
```

**Response:**
```json
{
  "success": true,
  "message": "Servicio registrado exitosamente",
  "data": {
    "id": 1,
    "nombre": "Corte de Cabello",
    "descripcion": "Corte de cabello profesional con productos premium",
    "categoria": "Peluquería",
    "precio": 50.00,
    "duracionMinutos": 45,
    "activo": true,
    "fechaCreacion": "2024-10-19 15:30:00"
  }
}
```

### Modificar Servicio
**PUT** `/servicios/{id}`

Actualiza la información completa de un servicio.

**Request Body:**
```json
{
  "nombre": "Corte de Cabello Premium",
  "descripcion": "Corte de cabello profesional con productos premium y tratamiento",
  "categoria": "Peluquería",
  "precio": 60.00,
  "duracionMinutos": 60,
  "activo": true
}
```

### Actualizar Precio y Duración
**PATCH** `/servicios/{id}/precio-duracion?precio={precio}&duracionMinutos={duracion}`

Actualiza solo el precio y/o duración de un servicio.

**Parámetros:**
- `precio` (opcional): Nuevo precio
- `duracionMinutos` (opcional): Nueva duración en minutos

**Ejemplo:**
```
PATCH /servicios/1/precio-duracion?precio=55.00&duracionMinutos=50
```

**Response:**
```json
{
  "success": true,
  "message": "Precio y duración actualizados exitosamente"
}
```

### Consultar Servicio por ID
**GET** `/servicios/{id}`

Obtiene la información de un servicio específico.

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "nombre": "Corte de Cabello",
    "descripcion": "Corte de cabello profesional",
    "categoria": "Peluquería",
    "precio": 50.00,
    "duracionMinutos": 45,
    "activo": true,
    "fechaCreacion": "2024-10-19 15:30:00"
  }
}
```

### Consultar Servicios
**GET** `/servicios?activos=true`

Obtiene la lista de servicios. Opcionalmente filtra por activos.

**Parámetros:**
- `activos` (opcional): Filtrar solo servicios activos (true/false)

**Response:**
```json
{
  "success": true,
  "data": [ ... ],
  "total": 15
}
```

### Buscar Servicios
**GET** `/servicios/buscar?nombre={nombre}&categoria={categoria}&precioMin={min}&precioMax={max}`

Busca servicios por diferentes criterios.

**Parámetros:**
- `nombre` (opcional): Búsqueda por nombre
- `categoria` (opcional): Filtrar por categoría
- `precioMin` (opcional): Precio mínimo
- `precioMax` (opcional): Precio máximo

**Ejemplos:**
```
GET /servicios/buscar?nombre=Corte
GET /servicios/buscar?categoria=Peluquería
GET /servicios/buscar?precioMin=30&precioMax=100
```

### Obtener Categorías
**GET** `/servicios/categorias`

Obtiene la lista de categorías de servicios activos.

**Response:**
```json
{
  "success": true,
  "data": ["Peluquería", "Estética", "Masajes"],
  "total": 3
}
```

### Eliminar Servicio
**DELETE** `/servicios/{id}`

Elimina un servicio del sistema.

**Response:**
```json
{
  "success": true,
  "message": "Servicio eliminado exitosamente"
}
```

---

## Códigos de Estado HTTP

- `200 OK`: Solicitud exitosa
- `201 Created`: Recurso creado exitosamente
- `400 Bad Request`: Datos inválidos o error de validación
- `404 Not Found`: Recurso no encontrado
- `500 Internal Server Error`: Error interno del servidor

## Validaciones

### Cliente
- `nombre`: Requerido, 2-100 caracteres
- `apellido`: Requerido, 2-100 caracteres
- `email`: Requerido, formato válido, único
- `telefono`: Requerido, 7-20 caracteres, formato válido
- `direccion`: Opcional, máximo 255 caracteres

### Servicio
- `nombre`: Requerido, 2-150 caracteres, único
- `descripcion`: Opcional, máximo 500 caracteres
- `categoria`: Requerido, 2-100 caracteres
- `precio`: Requerido, mayor a 0
- `duracionMinutos`: Requerido, mínimo 1 minuto
- `activo`: Opcional, por defecto true
