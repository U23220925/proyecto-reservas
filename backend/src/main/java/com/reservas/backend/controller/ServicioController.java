package com.reservas.backend.controller;

import com.reservas.backend.dto.ServicioDTO;
import com.reservas.backend.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarServicio(@Valid @RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO servicioCreado = servicioService.registrarServicio(servicioDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Servicio registrado exitosamente");
            response.put("data", servicioCreado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> modificarServicio(@PathVariable Long id,
                                                                  @Valid @RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO servicioActualizado = servicioService.modificarServicio(id, servicioDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Servicio actualizado exitosamente");
            response.put("data", servicioActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/precio-duracion")
    public ResponseEntity<Map<String, Object>> actualizarPrecioYDuracion(
            @PathVariable Long id,
            @RequestParam(required = false) BigDecimal precio,
            @RequestParam(required = false) Integer duracionMinutos) {
        try {
            servicioService.actualizarPrecioYDuracion(id, precio, duracionMinutos);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Precio y duración actualizados exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> consultarServicio(@PathVariable Long id) {
        try {
            ServicioDTO servicio = servicioService.consultarServicio(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", servicio);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> consultarServicios(
            @RequestParam(required = false) Boolean activos) {
        try {
            List<ServicioDTO> servicios;
            if (Boolean.TRUE.equals(activos)) {
                servicios = servicioService.consultarServiciosActivos();
            } else {
                servicios = servicioService.consultarTodosLosServicios();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", servicios);
            response.put("total", servicios.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al consultar servicios");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarServicios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax) {
        try {
            List<ServicioDTO> servicios;

            if (nombre != null && !nombre.trim().isEmpty()) {
                servicios = servicioService.buscarServiciosPorNombre(nombre);
            } else if (categoria != null && !categoria.trim().isEmpty()) {
                servicios = servicioService.consultarServiciosPorCategoria(categoria);
            } else if (precioMin != null && precioMax != null) {
                servicios = servicioService.consultarServiciosPorPrecio(precioMin, precioMax);
            } else {
                servicios = servicioService.consultarServiciosActivos();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", servicios);
            response.put("total", servicios.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al buscar servicios");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, Object>> obtenerCategorias() {
        try {
            List<String> categorias = servicioService.obtenerCategorias();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", categorias);
            response.put("total", categorias.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener categorías");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable Long id) {
        try {
            servicioService.eliminarServicio(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Servicio eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
