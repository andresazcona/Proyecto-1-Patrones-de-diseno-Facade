package com.andresazcona.services;

import com.andresazcona.adapters.BancoAdapter;
import com.andresazcona.adapters.BancoXAdapter;
import com.andresazcona.adapters.BancoYAdapter;
import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Servicio encargado de extraer y unificar transacciones desde archivos CSV de distintos bancos.
 * Utiliza adaptadores específicos para cada banco detectado en los nombres de archivo.
 */
public class ExtractorDatos {
    private final Map<String, BancoAdapter> adaptadores = new HashMap<>();

    /**
     * Constructor de la clase. Inicializa los adaptadores de bancos soportados.
     */
    public ExtractorDatos() {
        adaptadores.put("bancox", new BancoXAdapter());
        adaptadores.put("bancony", new BancoYAdapter());
    }

    /**
     * Extrae datos de todos los archivos CSV dentro de una carpeta dada.
     * Detecta automáticamente el banco correspondiente según el nombre del archivo y utiliza
     * el adaptador adecuado para procesar los datos.
     *
     * @param carpetaEntrada Ruta de la carpeta que contiene los archivos CSV.
     * @return Lista combinada de todas las transacciones extraídas.
     * @throws Exception Si la carpeta no existe o no contiene archivos CSV válidos.
     */
    public List<Transaccion> extraerDatos(String carpetaEntrada) throws Exception {
        List<Transaccion> todasTransacciones = new ArrayList<>();
        File directorio = new File(carpetaEntrada);

        // Validar si el directorio existe
        if (!directorio.exists()) {
            throw new IOException("❌ El directorio de reportes no existe.");
        }

        // Filtrar archivos CSV dentro del directorio
        File[] archivosCSV = directorio.listFiles((dir, nombre) ->
                nombre.toLowerCase().endsWith(".csv"));

        if (archivosCSV == null || archivosCSV.length == 0) {
            throw new IOException("❌ No se encontraron archivos CSV en el directorio.");
        }

        System.out.println("✓ Archivos detectados: " + archivosCSV.length);

        // Procesar cada archivo CSV detectado
        for (File archivo : archivosCSV) {
            System.out.println("   - Procesando: " + archivo.getName());

            String nombreArchivo = archivo.getName().toLowerCase();
            List<Transaccion> transaccionesArchivo = new ArrayList<>();

            // Detectar el banco correspondiente según el nombre del archivo
            BancoAdapter adapter = null;
            if (nombreArchivo.contains("bancox")) {
                adapter = adaptadores.get("bancox");
            } else if (nombreArchivo.contains("bancony")) {
                adapter = adaptadores.get("bancony");
            }

            // Si se encuentra un adaptador adecuado, extraer datos
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
