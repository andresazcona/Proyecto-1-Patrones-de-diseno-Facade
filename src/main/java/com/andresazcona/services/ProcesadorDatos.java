package com.andresazcona.services;

import com.andresazcona.domain.Transaccion;

import java.util.*;

public class ProcesadorDatos {
    public List<Transaccion> procesarDatos(List<Transaccion> transacciones) {
        // Ordenar por fecha
        Collections.sort(transacciones);

        // Eliminar duplicados (ejemplo simple)
        Set<Transaccion> conjunto = new LinkedHashSet<>(transacciones);
        return new ArrayList<>(conjunto);
    }
}