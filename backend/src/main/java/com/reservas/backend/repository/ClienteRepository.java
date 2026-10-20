package com.reservas.backend.repository;

import com.reservas.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Cliente> buscarPorTermino(@Param("termino") String termino);

    @Query("SELECT c FROM Cliente c ORDER BY c.fechaRegistro DESC")
    List<Cliente> findAllClientesOrdenadosPorFecha();
}
