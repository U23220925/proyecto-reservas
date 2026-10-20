package com.reservas.backend.controller;

import com.reservas.backend.dto.ClienteDTO;
import com.reservas.backend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteCreado = clienteService.registrarCliente(clienteDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente registrado exitosamente");
            response.put("data", clienteCreado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> modificarCliente(@PathVariable Long id,
                                                                 @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteActualizado = clienteService.modificarCliente(id, clienteDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente actualizado exitosamente");
            response.put("data", clienteActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> consultarCliente(@PathVariable Long id) {
        try {
            ClienteDTO cliente = clienteService.consultarCliente(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", cliente);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> consultarTodosLosClientes() {
        try {
            List<ClienteDTO> clientes = clienteService.consultarTodosLosClientes();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", clientes);
            response.put("total", clientes.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al consultar clientes");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarClientes(
            @RequestParam(required = false) String termino,
            @RequestParam(required = false) String nombre) {
        try {
            List<ClienteDTO> clientes;
            if (nombre != null && !nombre.trim().isEmpty()) {
                clientes = clienteService.buscarClientesPorNombre(nombre);
            } else if (termino != null && !termino.trim().isEmpty()) {
                clientes = clienteService.buscarClientesPorTermino(termino);
            } else {
                clientes = clienteService.consultarTodosLosClientes();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", clientes);
            response.put("total", clientes.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al buscar clientes");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
