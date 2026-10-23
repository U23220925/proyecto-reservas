package com.reservas.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class ReservaDTO {

    private Long id;

    @NotNull(message = "El cliente es obligatorio")
    @Min(value = 1, message = "El ID del cliente debe ser mayor a 0")
    private Long clienteId;

    @NotNull(message = "El servicio es obligatorio")
    @Min(value = 1, message = "El ID del servicio debe ser mayor a 0")
    private Long servicioId;

    @NotBlank(message = "La fecha y hora de la reserva es obligatoria")
    private String fechaReserva; // formato: "yyyy-MM-dd HH:mm:ss"

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    private String notas;

    private String estado;

    private String fechaCreacion;

    public ReservaDTO() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}