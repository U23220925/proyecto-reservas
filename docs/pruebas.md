# Protocolo de Pruebas de Endpoints (CLI)

Para verificar el correcto funcionamiento del backend de Spring Boot sin depender de interfaces gráficas ni Postman, se ejecutó un script automatizado en **Windows PowerShell** apuntando al puerto local `8080`.

## Script de Prueba Ejecutado

```powershell
$api = "http://localhost:8080/api"

# Función utilitaria para peticiones POST
function Post($ruta, $obj) {
  Invoke-RestMethod -Method Post -Uri "$api/$ruta" -ContentType "application/json" -Body ($obj | ConvertTo-Json -Depth 5)
}

# 1. Registro de Cliente Base
$c = Post "clientes" @{nombre="Luis"; apellido="Perez"; email="luis@test.com"; telefono="988777666"}

# 2. Registro de Servicio Base
$s = Post "servicios" @{nombre="Masaje"; categoria="Spa"; precio=80; duracionMinutos=30}

# 3. Registro de Reserva (Fecha Futura exitosa)
$r = Post "reservas" @{clienteId=$c.id; servicioId=$s.id; fechaReserva="2027-04-01T09:00:00"}

# 4. Cancelación de Reserva (Cambio de Estado - Tarea #13)
Invoke-RestMethod -Method Put -Uri "$api/reservas/$($r.id)/cancelar"
```

## Resultados Esperados
- **POST /api/reservas**: Retorna el objeto `ReservaDTO` con estado `PENDIENTE`.
- **PUT /api/reservas/{id}/cancelar**: Retorna el objeto actualizado con estado `CANCELADA`.
