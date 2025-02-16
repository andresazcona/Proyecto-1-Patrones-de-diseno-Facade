package com.andresazcona.adapters;

import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.util.List;

/**
 * Interfaz que define un adaptador para la extracción de datos bancarios.
 * Esta interfaz permite extraer transacciones desde un archivo dado,
 * facilitando la integración con distintos formatos de datos bancarios.
 */
public interface BancoAdapter {

    /**
     * Extrae una lista de transacciones desde el archivo proporcionado.
     *
     * @param archivo Archivo que contiene los datos de las transacciones.
     * @return Lista de transacciones extraídas del archivo.
     * @throws Exception Si ocurre un error al procesar el archivo.
     */
    List<Transaccion> extraerDatos(File archivo) throws Exception;
}
