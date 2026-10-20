package com.reservas.backend.repository;

import com.reservas.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteId(Long clienteId);

    List<Reserva> findByServicioId(Long servicioId);

    List<Reserva> findByEstado(String estado);

    Optional<Reserva> findByIdAndEstado(Long id, String estado);

    /**
     * Encuentra reservas de un servicio cuyo intervalo [fechaReserva, fechaReserva + duración]
     * se superponga con el intervalo [nuevaFechaStart, nuevaFechaFin].
     * Se usa Native Query compatible con PostgreSQL.
     */
    @Query(value = "SELECT r.* FROM reservas r " +
            "JOIN servicios s ON r.servicio_id = s.id " +
            "WHERE s.id = :servicioId " +
            "AND r.fecha_reserva < :nuevaFechaFin " +
            "AND (r.fecha_reserva + (s.duracion_minutos || ' minutes')::interval) > :nuevaFechaStart", 
            nativeQuery = true)
    List<Reserva> findReservasSolapadas(@Param("servicioId") Long servicioId,
                                        @Param("nuevaFechaStart") LocalDateTime nuevaFechaStart,
                                        @Param("nuevaFechaFin") LocalDateTime nuevaFechaFin);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.servicio s JOIN FETCH r.cliente c ORDER BY r.fechaReserva DESC")
    List<Reserva> findAllOrdenadosPorFechaDesc();

    long countByServicioId(Long servicioId);
}
