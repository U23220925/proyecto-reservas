# Matriz de Trazabilidad - Sistema de Reservas v1.0

Este documento registra la correspondencia lineal entre los requerimientos del cliente, las actividades del tablero, los responsables y los entregables de código integrados en el repositorio.

| Requerimiento (Caso) | Actividad (Issue) | Responsable | Desarrollo (Rama) | Estado en PR | Código Fuente (Backend) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **RF-01:** Registrar Clientes | #1 Módulo Clientes | Nicolas Jara | `feature/clientes` | Integrado | `ClienteController.java`, `ClienteService.java` |
| **RF-02:** Gestionar Servicios | #2 Módulo Servicios | Herbert Morales | `feature/servicios` | Integrado | `ServicioController.java`, `ServicioService.java` |
| **RF-03:** Registrar Reservas | #9 Registrar Reservas | Nicolas Jara | `feature/modulo-reservas` | Integrado | `ReservaController.java` (POST), `Reserva.java` |
| **RF-04:** Vincular Entidades | #10 Seleccionar Cliente/Servicio | Nicolas Jara | `feature/modulo-reservas` | Integrado | `ReservaDTO.java` (IDs validados) |
| **RF-05:** Controlar Horarios | #11 Registrar Fecha/Hora | Nicolas Jara | `feature/modulo-reservas` | Integrado | `ReservaRepository.java` (`findReservasSolapadas`) |
| **RF-06:** Consultar Historial | #12 Consultar Reservas | Nicolas Jara | `feature/modulo-reservas` | Integrado | `ReservaController.java` (GET /api/reservas) |
| **RF-07:** Cancelar Reservas | #13 Cancelar Reservas | Nicolas Jara | `feature/modulo-reservas` | Integrado | `ReservaService.java` (`cancelarReserva` - PUT) |
| **RF-08:** Validar Reglas | #24 PR de Validaciones | Nicolas Jara | `feature/24-validaciones` | En Revisión | `ValidacionReserva.java` |
