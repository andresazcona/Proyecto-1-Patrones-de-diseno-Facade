package com.andresazcona.adapters;

import com.andresazcona.domain.Transaccion;
import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BancoYAdapter implements BancoAdapter {
    @Override
    public List<Transaccion> extraerDatos(File archivo) throws Exception {
        List<Transaccion> transacciones = new ArrayList<>();
        List<String> lineas = Files.readAllLines(archivo.toPath());

        // Saltar cabecera
        for (int i = 1; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            transacciones.add(new Transaccion(
                    datos[0].trim(),
                    datos[1].trim(),
                    Double.parseDouble(datos[2].trim()),
                    datos[3].trim(),
                    "BancoY"
            ));
        }
        return transacciones;
    }
}