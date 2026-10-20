# Estructura del Proyecto - Sistema de Gestión de Reservas

## 📁 Estructura de Archivos Creados

```
backend/src/main/java/com/reservas/backend/
├── BackendApplication.java              # Clase principal de Spring Boot
├── model/                               # Entidades JPA
│   ├── Cliente.java                     # Entidad Cliente con validaciones
│   └── Servicio.java                    # Entidad Servicio con validaciones
├── dto/                                 # Data Transfer Objects
│   ├── ClienteDTO.java                  # DTO para transferencias de clientes
│   └── ServicioDTO.java                 # DTO para transferencias de servicios
├── repository/                          # Repositorios JPA
│   ├── ClienteRepository.java           # Operaciones CRUD para clientes
│   └── ServicioRepository.java          # Operaciones CRUD para servicios
├── service/                             # Lógica de negocio
│   ├── ClienteService.java              # Servicios para gestión de clientes
│   └── ServicioService.java             # Servicios para gestión de servicios
└── controller/                          # Controladores REST
    ├── ClienteController.java           # Endpoints REST para clientes
    ├── ServicioController.java          # Endpoints REST para servicios
    └── GlobalExceptionHandler.java      # Manejo global de excepciones
```

## 🎯 Funcionalidades Implementadas

### 👥 Gestión de Clientes

#### ✅ Registrar Cliente
**Endpoint:** `POST /api/clientes`
- Registra nuevos clientes con validación de datos
- Campos requeridos: nombre, apellido, email, teléfono
- Email único en el sistema
- Valida formato de email y teléfono

#### ✅ Modificar Información de Cliente
**Endpoint:** `PUT /api/clientes/{id}`
- Actualiza información de cliente existente
- Valida que el email no esté duplicado
- Mantiene registro de fecha de creación

#### ✅ Consultar Clientes
**Endpoints:**
- `GET /api/clientes` - Lista todos los clientes
- `GET /api/clientes/{id}` - Cliente específico por ID
- `GET /api/clientes/buscar` - Búsqueda por término o nombre

**Funciones de búsqueda:**
- Búsqueda por nombre, apellido o email
- Búsqueda parcial (contiene)
- Insensible a mayúsculas/minúsculas

### 🛠️ Gestión de Servicios

#### ✅ Registrar Servicio
**Endpoint:** `POST /api/servicios`
- Registra nuevos servicios
- Campos requeridos: nombre, categoría, precio, duración
- Nombre único en el sistema
- Precio debe ser > 0
- Duración mínima 1 minuto

#### ✅ Establecer Precio y Duración
**Endpoints:**
- `PUT /api/servicios/{id}` - Actualiza servicio completo
- `PATCH /api/servicios/{id}/precio-duracion` - Actualiza solo precio y duración

**Validaciones:**
- Precio debe ser mayor a 0
- Duración mínima 1 minuto

#### ✅ Consultar Servicios
**Endpoints:**
- `GET /api/servicios` - Lista servicios (filtrable por activos)
- `GET /api/servicios/{id}` - Servicio específico por ID
- `GET /api/servicios/buscar` - Búsqueda por nombre, categoría o precio
- `GET /api/servicios/categorias` - Lista de categorías disponibles

**Funciones de búsqueda:**
- Búsqueda por nombre (parcial)
- Filtrado por categoría
- Filtrado por rango de precio
- Servicios activos/inactivos

## 🔧 Tecnologías Utilizadas

- **Spring Boot 4.1.1** - Framework principal
- **Java 21** - Versión de lenguaje
- **Spring Data JPA** - Persistencia de datos
- **Spring Boot Web** - API REST
- **Spring Validation** - Validación de datos
- **PostgreSQL** - Base de datos
- **Maven** - Gestión de dependencias

## 📊 Base de Datos

### Tabla: clientes
```sql
- id (BIGINT, PK, AUTO_INCREMENT)
- nombre (VARCHAR(100), NOT NULL)
- apellido (VARCHAR(100), NOT NULL)
- email (VARCHAR(150), UNIQUE, NOT NULL)
- telefono (VARCHAR(20), NOT NULL)
- direccion (VARCHAR(255))
- fecha_registro (TIMESTAMP, NOT NULL)
```

### Tabla: servicios
```sql
- id (BIGINT, PK, AUTO_INCREMENT)
- nombre (VARCHAR(150), UNIQUE, NOT NULL)
- descripcion (VARCHAR(500))
- categoria (VARCHAR(100), NOT NULL)
- precio (DECIMAL(10,2), NOT NULL)
- duracion_minutos (INTEGER, NOT NULL)
- activo (BOOLEAN, DEFAULT TRUE)
- fecha_creacion (TIMESTAMP, NOT NULL)
```

## 🚀 Cómo Iniciar

### 1. Configurar Base de Datos
Asegúrate de tener PostgreSQL corriendo en `localhost:5432` con la base de datos `reservas`.

### 2. Ejecutar la Aplicación
```bash
cd backend
./mvnw spring-boot:run
# o
mvn spring-boot:run
```

### 3. Probar Endpoints
La aplicación estará disponible en: `http://localhost:8080`

## 📝 Ejemplos de Uso

### Registrar un Cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María",
    "apellido": "González",
    "email": "maria.gonzalez@email.com",
    "telefono": "+51 987 654 321",
    "direccion": "Av. Principal 123"
  }'
```

### Registrar un Servicio
```bash
curl -X POST http://localhost:8080/api/servicios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Corte de Cabello",
    "descripcion": "Corte profesional",
    "categoria": "Peluquería",
    "precio": 50.00,
    "duracionMinutos": 45
  }'
```

### Actualizar Precio y Duración
```bash
curl -X PATCH "http://localhost:8080/api/servicios/1/precio-duracion?precio=55.00&duracionMinutos=50"
```

## 📚 Documentación Adicional

- Ver `docs/API.md` para documentación completa de la API
- Ver `backend/src/main/resources/application.properties` para configuración
