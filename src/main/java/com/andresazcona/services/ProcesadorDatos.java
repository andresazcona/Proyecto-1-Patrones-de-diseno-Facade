package com.andresazcona.services;

import com.andresazcona.domain.Transaccion;
import java.util.*;

/**
 * Servicio encargado de procesar las transacciones financieras.
 * Realiza operaciones como ordenamiento por fecha y eliminación de duplicados.
 */
public class ProcesadorDatos {

    /**
     * Procesa una lista de transacciones ordenándolas por fecha y eliminando duplicados.
     *
     * @param transacciones Lista de transacciones a procesar.
     * @return Lista de transacciones ordenadas y sin duplicados.
     */
    public List<Transaccion> procesarDatos(List<Transaccion> transacciones) {
        // Ordenar las transacciones por fecha (implementación de Comparable en Transaccion)
        Collections.sort(transacciones);

        // Eliminar duplicados utilizando un LinkedHashSet (conserva el orden)
        Set<Transaccion> conjunto = new LinkedHashSet<>(transacciones);
        return new ArrayList<>(conjunto);
    }
}
