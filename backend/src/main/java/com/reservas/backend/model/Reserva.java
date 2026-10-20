package com.reservas.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_reserva", nullable = false)
    private java.time.LocalDateTime fechaReserva;

    @NotBlank(message = "El estado de la reserva es obligatorio")
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    @Column(nullable = false, length = 20)
    private String estado;

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    @Column(length = 500)
    private String notas;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private java.time.LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = java.time.LocalDateTime.now();
        if (this.estado == null || this.estado.isBlank()) {
            this.estado = "PENDIENTE";
        }
    }

    // Constructores
    public Reserva() {}

    public Reserva(Cliente cliente, Servicio servicio, java.time.LocalDateTime fechaReserva, String estado) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public java.time.LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(java.time.LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public java.time.LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", clienteId=" + (cliente != null ? cliente.getId() : null) +
                ", servicioId=" + (servicio != null ? servicio.getId() : null) +
                ", fechaReserva=" + fechaReserva +
                ", estado='" + estado + '\'' +
                '}';
    }
}