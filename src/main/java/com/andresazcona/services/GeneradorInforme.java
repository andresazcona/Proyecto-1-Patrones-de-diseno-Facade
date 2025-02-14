package com.andresazcona.services;

import com.andresazcona.domain.Transaccion;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorInforme {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public void generarInformeCSV(List<Transaccion> transacciones, String archivoSalida) {
        try (FileWriter writer = new FileWriter(archivoSalida)) {
            writer.write("Fecha,Banco Origen,Tipo,Descripción,Monto\n");

            for (Transaccion t : transacciones) {
                writer.write(String.format("%s,%s,%s,\"%s\",%.2f%n",
                        SDF.format(t.getFecha()),
                        t.getBancoOrigen(),
                        t.getTipo(),
                        t.getDescripcion(),
                        t.getMonto()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error al generar el informe: " + e.getMessage());
        }
    }
}