package com.andresazcona.adapters;

import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del adaptador para el banco "BancoY".
 * Esta clase permite extraer datos de transacciones desde un archivo CSV específico de BancoY
 * y convertirlos en objetos de tipo {@link Transaccion}.
 */
public class BancoYAdapter implements BancoAdapter {

    /**
     * Extrae una lista de transacciones desde un archivo CSV de BancoY.
     * Se espera que el archivo tenga un formato de columnas separadas por comas (CSV),
     * donde la primera línea corresponde a la cabecera.
     *
     * @param archivo Archivo CSV que contiene los datos de las transacciones.
     * @return Lista de transacciones extraídas del archivo.
     * @throws Exception Si ocurre un error al leer el archivo o procesar los datos.
     */
    @Override
    public List<Transaccion> extraerDatos(File archivo) throws Exception {
        List<Transaccion> transacciones = new ArrayList<>();
        List<String> lineas = Files.readAllLines(archivo.toPath());

        // Saltar la cabecera y procesar las líneas de datos
        for (int i = 1; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Manejo de comas dentro de comillas

            // Se crea un objeto Transaccion con los datos extraídos
            transacciones.add(new Transaccion(
                    datos[0].trim(),  // ID de la transacción
                    datos[1].trim(),  // Fecha de la transacción
                    Double.parseDouble(datos[2].trim()),  // Monto de la transacción
                    datos[3].trim(),  // Descripción
                    "BancoY"  // Origen de la transacción
            ));
        }
        return transacciones;
    }
}
