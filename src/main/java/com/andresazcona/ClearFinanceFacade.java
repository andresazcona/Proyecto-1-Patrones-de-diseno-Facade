package com.andresazcona;

import com.andresazcona.domain.Transaccion;
import com.andresazcona.services.ExtractorDatos;
import com.andresazcona.services.GeneradorInforme;
import com.andresazcona.services.ProcesadorDatos;

import java.io.File;
import java.util.List;

/**
 * Fachada para la gestión de la extracción, procesamiento y generación de informes financieros.
 * Encapsula la lógica principal del sistema para facilitar su uso desde la interfaz de usuario.
 */
public class ClearFinanceFacade {
    private final ExtractorDatos extractor = new ExtractorDatos();
    private final ProcesadorDatos procesador = new ProcesadorDatos();
    private final GeneradorInforme generador = new GeneradorInforme();

    /**
     * Genera un informe consolidado en el formato seleccionado.
     *
     * Extrae datos de los archivos CSV de una carpeta dada, los procesa eliminando duplicados
     * y ordenándolos por fecha, y finalmente genera un informe en el formato especificado.
     *
     * @param carpetaEntrada Ruta de la carpeta donde están los archivos CSV de entrada.
     * @param archivoSalida  Ruta del archivo de salida con la extensión correspondiente.
     * @param formato        Formato del informe: "csv", "xlsx", "pdf".
     */
    public void generarInformeUnificado(String carpetaEntrada, String archivoSalida, String formato) {
        try {
            System.out.println("\n=== Iniciando proceso de consolidación ===");

            // Contar los archivos CSV disponibles en la carpeta de entrada
            File carpeta = new File(carpetaEntrada);
            File[] archivosCSV = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

            if (archivosCSV == null || archivosCSV.length == 0) {
                System.err.println("❌ No se encontraron archivos CSV en la carpeta especificada.");
                return;
            }

            System.out.println("✓ Archivos CSV detectados: " + archivosCSV.length);
            for (File archivo : archivosCSV) {
                System.out.println("   - " + archivo.getName());
            }

            // Extraer transacciones desde los archivos CSV detectados
            List<Transaccion> transacciones = extractor.extraerDatos(carpetaEntrada);
            System.out.println("✓ Total de transacciones extraídas: " + transacciones.size());

            // Procesar datos (ordenar y eliminar duplicados)
            List<Transaccion> datosProcesados = procesador.procesarDatos(transacciones);
            System.out.println("✓ Total de transacciones después de normalización: " + datosProcesados.size());

            // Generar el informe en el formato deseado
            switch (formato.toLowerCase()) {
                case "csv":
                    generador.generarInformeCSV(datosProcesados, archivoSalida);
                    break;
                case "xlsx":
                    generador.generarInformeExcel(datosProcesados, archivoSalida);
                    break;
                case "pdf":
                    generador.generarInformePDF(datosProcesados, archivoSalida);
                    break;
                default:
                    System.out.println("⚠ Formato desconocido. Solo se soportan CSV, Excel y PDF.");
                    return;
            }

            System.out.println("✅ Informe generado exitosamente: " + archivoSalida);
            System.exit(0);  // 🔹 Termina la ejecución inmediatamente después de generar el informe

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
