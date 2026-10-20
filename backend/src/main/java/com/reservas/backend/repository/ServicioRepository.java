package com.reservas.backend.repository;

import com.reservas.backend.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Optional<Servicio> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Servicio> findByCategoria(String categoria);

    List<Servicio> findByActivoTrue();

    List<Servicio> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);

    @Query("SELECT s FROM Servicio s WHERE s.activo = true AND LOWER(s.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Servicio> buscarPorNombre(@Param("termino") String termino);

    @Query("SELECT s FROM Servicio s WHERE s.activo = true AND LOWER(s.categoria) = LOWER(:categoria)")
    List<Servicio> findByCategoriaActiva(@Param("categoria") String categoria);

    @Query("SELECT s FROM Servicio s WHERE s.activo = true ORDER BY s.precio ASC")
    List<Servicio> findAllActivosOrdenadosPorPrecio();

    @Query("SELECT DISTINCT s.categoria FROM Servicio s WHERE s.activo = true")
    List<String> findCategoriasActivas();
}
