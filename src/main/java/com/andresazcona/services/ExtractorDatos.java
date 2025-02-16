package com.andresazcona.services;

import com.andresazcona.adapters.BancoAdapter;
import com.andresazcona.adapters.BancoXAdapter;
import com.andresazcona.adapters.BancoYAdapter;
import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExtractorDatos {
    private final Map<String, BancoAdapter> adaptadores = new HashMap<>();

    public ExtractorDatos() {
        adaptadores.put("bancox", new BancoXAdapter());
        adaptadores.put("bancony", new BancoYAdapter());
    }

    /**
     * Extrae datos de todos los archivos CSV en la carpeta de entrada.
     *
     * @param carpetaEntrada Ruta de la carpeta con los archivos CSV.
     * @return Lista combinada de todas las transacciones extraídas.
     */
    public List<Transaccion> extraerDatos(String carpetaEntrada) throws Exception {
        List<Transaccion> todasTransacciones = new ArrayList<>();
        File directorio = new File(carpetaEntrada);

        if (!directorio.exists()) {
            throw new IOException("❌ El directorio de reportes no existe.");
        }

        File[] archivosCSV = directorio.listFiles((dir, nombre) ->
                nombre.toLowerCase().endsWith(".csv"));

        if (archivosCSV == null || archivosCSV.length == 0) {
            throw new IOException("❌ No se encontraron archivos CSV en el directorio.");
        }

        System.out.println("✓ Archivos detectados: " + archivosCSV.length);
        for (File archivo : archivosCSV) {
            System.out.println("   - Procesando: " + archivo.getName());

            String nombreArchivo = archivo.getName().toLowerCase();
            List<Transaccion> transaccionesArchivo = new ArrayList<>();

            // 🔹 Detectar correctamente el banco
            BancoAdapter adapter = null;
            if (nombreArchivo.contains("bancox")) {
                adapter = adaptadores.get("bancox");
            } else if (nombreArchivo.contains("bancoy")) {  // 🔹 Asegurar que detecte correctamente BancoY
                adapter = adaptadores.get("bancony");
            }

            if (adapter != null) {
                transaccionesArchivo = adapter.extraerDatos(archivo);
                System.out.println("   → Transacciones extraídas de " + archivo.getName() + ": " + transaccionesArchivo.size());
                todasTransacciones.addAll(transaccionesArchivo);
            } else {
                System.out.println("⚠ No se encontró un adaptador válido para " + archivo.getName() + ", se omitirá.");
            }
        }

        System.out.println("✓ Total de transacciones unificadas: " + todasTransacciones.size());
        return todasTransacciones;
    }
}
