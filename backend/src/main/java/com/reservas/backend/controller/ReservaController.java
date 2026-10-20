package com.reservas.backend.controller;

import com.reservas.backend.dto.ReservaDTO;
import com.reservas.backend.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO reservaCreada = reservaService.registrarReserva(reservaDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Reserva registrada exitosamente");
            response.put("data", reservaCreada);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> consultarReserva(@PathVariable Long id) {
        try {
            ReservaDTO reserva = reservaService.consultarReserva(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reserva);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> consultarTodasLasReservas() {
        try {
            List<ReservaDTO> reservas = reservaService.consultarTodasLasReservas();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reservas);
            response.put("total", reservas.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al consultar reservas");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Map<String, Object>> consultarReservasPorCliente(@PathVariable Long clienteId) {
        try {
            List<ReservaDTO> reservas = reservaService.consultarReservasPorCliente(clienteId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reservas);
            response.put("total", reservas.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<Map<String, Object>> consultarReservasPorServicio(@PathVariable Long servicioId) {
        try {
            List<ReservaDTO> reservas = reservaService.consultarReservasPorServicio(servicioId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reservas);
            response.put("total", reservas.size());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> consultarReservasPorEstado(@PathVariable String estado) {
        try {
            List<ReservaDTO> reservas = reservaService.consultarReservasPorEstado(estado);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reservas);
            response.put("total", reservas.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al consultar reservas por estado");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Reserva cancelada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}