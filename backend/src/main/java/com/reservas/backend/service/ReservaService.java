package com.reservas.backend.service;

import com.reservas.backend.dto.ReservaDTO;
import com.reservas.backend.model.Cliente;
import com.reservas.backend.model.Reserva;
import com.reservas.backend.model.Servicio;
import com.reservas.backend.repository.ClienteRepository;
import com.reservas.backend.repository.ReservaRepository;
import com.reservas.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Transactional
    public ReservaDTO registrarReserva(ReservaDTO reservaDTO) {
        // 1. Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + reservaDTO.getClienteId()));

        // 2. Validar que el servicio exista
        Servicio servicio = servicioRepository.findById(reservaDTO.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + reservaDTO.getServicioId()));

        // 3. Validar y parsear la fecha/hora
        if (reservaDTO.getFechaReserva() == null || reservaDTO.getFechaReserva().isBlank()) {
            throw new RuntimeException("La fecha y hora de la reserva es obligatoria (formato: yyyy-MM-dd HH:mm:ss)");
        }
        LocalDateTime fechaReserva;
        try {
            fechaReserva = LocalDateTime.parse(reservaDTO.getFechaReserva(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de fecha/hora inválido. Use yyyy-MM-dd HH:mm:ss");
        }

        // 4. Validar que la fecha sea futura
        if (!fechaReserva.isAfter(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de la reserva debe ser futura");
        }

        // 5. Validar que no existan reservas solapadas para el mismo servicio
        LocalDateTime nuevaFechaFin = fechaReserva.plusMinutes(servicio.getDuracionMinutos());
        List<Reserva> solapadas = reservaRepository.findReservasSolapadas(
                servicio.getId(), fechaReserva, nuevaFechaFin);
        if (!solapadas.isEmpty()) {
            throw new RuntimeException("Ya existe una reserva para este servicio en el horario solicitado");
        }

        // 6. Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setServicio(servicio);
        reserva.setFechaReserva(fechaReserva);
        reserva.setEstado("PENDIENTE");
        reserva.setNotas(reservaDTO.getNotas());

        Reserva reservaGuardada = reservaRepository.save(reserva);
        return convertirADTO(reservaGuardada);
    }

    public ReservaDTO consultarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        return convertirADTO(reserva);
    }

    public List<ReservaDTO> consultarTodasLasReservas() {
        return reservaRepository.findAllOrdenadosPorFechaDesc().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> consultarReservasPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }
        return reservaRepository.findByClienteId(clienteId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> consultarReservasPorServicio(Long servicioId) {
        if (!servicioRepository.existsById(servicioId)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + servicioId);
        }
        return reservaRepository.findByServicioId(servicioId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDTO> consultarReservasPorEstado(String estado) {
        return reservaRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        if ("CANCELADA".equals(reserva.getEstado())) {
            throw new RuntimeException("La reserva ya se encuentra cancelada");
        }
        reserva.setEstado("CANCELADA");
        reservaRepository.save(reserva);
    }

    private ReservaDTO convertirADTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setServicioId(reserva.getServicio().getId());
        dto.setFechaReserva(reserva.getFechaReserva().format(FORMATTER));
        dto.setEstado(reserva.getEstado());
        dto.setNotas(reserva.getNotas());
        if (reserva.getFechaCreacion() != null) {
            dto.setFechaCreacion(reserva.getFechaCreacion().format(FORMATTER));
        }
        return dto;
    }
}