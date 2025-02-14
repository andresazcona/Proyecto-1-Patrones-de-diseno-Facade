package com.andresazcona;

import com.andresazcona.domain.Transaccion;
import com.andresazcona.services.ExtractorDatos;
import com.andresazcona.services.GeneradorInforme;
import com.andresazcona.services.ProcesadorDatos;

import java.util.List;

public class ClearFinanceFacade {
    private final ExtractorDatos extractor = new ExtractorDatos();
    private final ProcesadorDatos procesador = new ProcesadorDatos();
    private final GeneradorInforme generador = new GeneradorInforme();

    public void generarInformeUnificado(String carpetaEntrada, String archivoSalida) {
        try {
            System.out.println("\nIniciando proceso de consolidación...");

            List<Transaccion> transacciones = extractor.extraerDatos(carpetaEntrada);
            System.out.println("✓ Transacciones extraídas: " + transacciones.size());

            List<Transaccion> datosProcesados = procesador.procesarDatos(transacciones);
            System.out.println("✓ Datos procesados y normalizados");

            generador.generarInformeCSV(datosProcesados, archivoSalida);
            System.out.println("✓ Informe generado: " + archivoSalida);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}