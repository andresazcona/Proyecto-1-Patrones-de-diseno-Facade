package com.andresazcona.services;

import com.andresazcona.adapters.BancoAdapter;
import com.andresazcona.adapters.BancoXAdapter;
import com.andresazcona.adapters.BancoYAdapter;
import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExtractorDatos {
    private final Map<String, BancoAdapter> adaptadores = new HashMap<>();

    public ExtractorDatos() {
        adaptadores.put("bancox", new BancoXAdapter());
        adaptadores.put("bancony", new BancoYAdapter());
    }

    public List<Transaccion> extraerDatos(String carpetaEntrada) throws Exception {
        List<Transaccion> todasTransacciones = new ArrayList<>();
        File directorio = new File(carpetaEntrada);

        if (!directorio.exists()) {
            throw new IOException("El directorio de reportes no existe");
        }

        File[] archivosCSV = directorio.listFiles((dir, nombre) ->
                nombre.toLowerCase().endsWith(".csv"));

        if (archivosCSV == null || archivosCSV.length == 0) {
            throw new IOException("No se encontraron archivos CSV en el directorio");
        }

        for (File archivo : archivosCSV) {
            String nombreArchivo = archivo.getName().toLowerCase();

            if (nombreArchivo.startsWith("bancox")) {
                todasTransacciones.addAll(adaptadores.get("bancox").extraerDatos(archivo));
            } else if (nombreArchivo.startsWith("bancony")) {
                todasTransacciones.addAll(adaptadores.get("bancony").extraerDatos(archivo));
            }
        }
        return todasTransacciones;
    }
}