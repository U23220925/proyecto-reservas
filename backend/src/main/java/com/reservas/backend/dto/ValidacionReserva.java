package com.reservas.backend.dto;

import java.time.LocalDateTime;

public class ValidacionReserva {
    public static boolean esFechaValida(LocalDateTime fecha) {
        return fecha != null && fecha.isAfter(LocalDateTime.now().plusDays(1));
    }
}
