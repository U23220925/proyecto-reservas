package com.reservas.backend.service;

import com.reservas.backend.dto.ServicioDTO;
import com.reservas.backend.model.Servicio;
import com.reservas.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ServicioDTO registrarServicio(ServicioDTO servicioDTO) {
        if (servicioRepository.existsByNombre(servicioDTO.getNombre())) {
            throw new RuntimeException("Ya existe un servicio con el nombre: " + servicioDTO.getNombre());
        }

        Servicio servicio = new Servicio();
        servicio.setNombre(servicioDTO.getNombre());
        servicio.setDescripcion(servicioDTO.getDescripcion());
        servicio.setCategoria(servicioDTO.getCategoria());
        servicio.setPrecio(servicioDTO.getPrecio());
        servicio.setDuracionMinutos(servicioDTO.getDuracionMinutos());
        servicio.setActivo(servicioDTO.getActivo() != null ? servicioDTO.getActivo() : true);

        Servicio servicioGuardado = servicioRepository.save(servicio);
        return convertirADTO(servicioGuardado);
    }

    public ServicioDTO modificarServicio(Long id, ServicioDTO servicioDTO) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

        if (!servicioExistente.getNombre().equals(servicioDTO.getNombre()) &&
                servicioRepository.existsByNombre(servicioDTO.getNombre())) {
            throw new RuntimeException("Ya existe un servicio con el nombre: " + servicioDTO.getNombre());
        }

        servicioExistente.setNombre(servicioDTO.getNombre());
        servicioExistente.setDescripcion(servicioDTO.getDescripcion());
        servicioExistente.setCategoria(servicioDTO.getCategoria());
        servicioExistente.setPrecio(servicioDTO.getPrecio());
        servicioExistente.setDuracionMinutos(servicioDTO.getDuracionMinutos());
        if (servicioDTO.getActivo() != null) {
            servicioExistente.setActivo(servicioDTO.getActivo());
        }

        Servicio servicioActualizado = servicioRepository.save(servicioExistente);
        return convertirADTO(servicioActualizado);
    }

    public ServicioDTO consultarServicio(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        return convertirADTO(servicio);
    }

    public List<ServicioDTO> consultarTodosLosServicios() {
        return servicioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ServicioDTO> consultarServiciosActivos() {
        return servicioRepository.findByActivoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ServicioDTO> buscarServiciosPorNombre(String nombre) {
        return servicioRepository.buscarPorNombre(nombre).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ServicioDTO> consultarServiciosPorCategoria(String categoria) {
        return servicioRepository.findByCategoriaActiva(categoria).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ServicioDTO> consultarServiciosPorPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return servicioRepository.findByPrecioBetween(precioMin, precioMax).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public void actualizarPrecioYDuracion(Long id, BigDecimal precio, Integer duracionMinutos) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

        if (precio != null) {
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("El precio debe ser mayor a 0");
            }
            servicio.setPrecio(precio);
        }

        if (duracionMinutos != null) {
            if (duracionMinutos < 1) {
                throw new RuntimeException("La duración debe ser al menos 1 minuto");
            }
            servicio.setDuracionMinutos(duracionMinutos);
        }

        servicioRepository.save(servicio);
    }

    public List<String> obtenerCategorias() {
        return servicioRepository.findCategoriasActivas();
    }

    public void eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        servicioRepository.deleteById(id);
    }

    private ServicioDTO convertirADTO(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setCategoria(servicio.getCategoria());
        dto.setPrecio(servicio.getPrecio());
        dto.setDuracionMinutos(servicio.getDuracionMinutos());
        dto.setActivo(servicio.getActivo());
        if (servicio.getFechaCreacion() != null) {
            dto.setFechaCreacion(servicio.getFechaCreacion().format(formatter));
        }
        return dto;
    }
}
