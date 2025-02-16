package com.andresazcona;

import java.util.Scanner;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Interfaz de consola para la interacción con el usuario en la generación de informes financieros.
 * Permite ingresar rutas de entrada y salida, seleccionar formato de exportación y generar el informe consolidado.
 */
public class InterfazConsola {
    private final Scanner scanner = new Scanner(System.in);
    private final ClearFinanceFacade facade = new ClearFinanceFacade();

    // 🔹 Rutas por defecto
    private static final String RUTA_ENTRADA_DEFAULT = "src\\main\\resources\\reportes";
    private static final String RUTA_SALIDA_DEFAULT = "src\\main\\resources\\salida";

    /**
     * Inicia la interfaz de consola mostrando el título y ejecutando el flujo de generación del informe.
     */
    public void iniciar() {
        mostrarTitulo();
        generarInforme();  // 🔹 Se ejecuta y se cierra inmediatamente
    }

    /**
     * Muestra el título de la aplicación en la consola con un pequeño retraso para mejorar la experiencia del usuario.
     */
    private void mostrarTitulo() {
        System.out.println("\n============================================");
        System.out.println("  ClearFinance - Software de Unificación de Reportes  ");
        System.out.println("============================================\n");

        esperar(1000);
    }

    /**
     * Ejecuta el flujo de generación del informe, solicitando al usuario las rutas de entrada y salida,
     * el nombre del archivo y el formato deseado.
     */
    private void generarInforme() {
        try {
            // Pedir carpeta de entrada con opción de ruta por defecto
            System.out.print("\nIngrese la ruta de la carpeta con reportes de entrada (Presione Enter para usar la ruta por defecto): ");
            String carpetaEntrada = scanner.nextLine().trim();
            if (carpetaEntrada.isEmpty()) {
                carpetaEntrada = RUTA_ENTRADA_DEFAULT;
                System.out.println("🔹 Usando ruta por defecto: " + carpetaEntrada);
            }
            esperar(700);

            if (!validarCarpeta(carpetaEntrada)) {
                System.out.println("❌ La carpeta de entrada no existe o está vacía.");
                return;
            }

            // Pedir carpeta de salida con opción de ruta por defecto
            System.out.print("\nIngrese la ruta de la carpeta donde se guardará el informe (Presione Enter para usar la ruta por defecto): ");
            String carpetaSalida = scanner.nextLine().trim();
            if (carpetaSalida.isEmpty()) {
                carpetaSalida = RUTA_SALIDA_DEFAULT;
                System.out.println("🔹 Usando ruta por defecto: " + carpetaSalida);
            }
            esperar(700);

            if (!validarCarpeta(carpetaSalida)) {
                System.out.println("⚠ La carpeta de salida no existe. Creándola...");
                new File(carpetaSalida).mkdirs();
            }

            // Pedir nombre del archivo sin extensión
            System.out.print("\nIngrese el nombre del archivo de salida (sin extensión): ");
            String nombreArchivo = scanner.nextLine().trim();
            if (nombreArchivo.isEmpty()) {
                nombreArchivo = "informe";  // 🔹 Nombre por defecto
                System.out.println("🔹 Usando nombre por defecto: informe");
            }
            esperar(700);

            // Seleccionar formato con números
            String formato = seleccionarFormato();
            if (formato == null) {
                System.out.println("❌ Opción inválida. Cancelando operación.");
                return;
            }
            esperar(700);

            // Construir ruta de salida
            String archivoSalida = carpetaSalida + File.separator + nombreArchivo + "." + formato;

            System.out.println("\n⏳ Generando informe...");
            esperar(1500);

            // Ejecutar la generación del informe
            facade.generarInformeUnificado(carpetaEntrada, archivoSalida, formato);

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Valida si la carpeta ingresada existe y contiene archivos.
     *
     * @param ruta Ruta de la carpeta a validar.
     * @return {@code true} si la carpeta existe y es válida, {@code false} en caso contrario.
     */
    private boolean validarCarpeta(String ruta) {
        File carpeta = new File(ruta);
        return carpeta.exists() && carpeta.isDirectory();
    }

    /**
     * Permite seleccionar el formato de salida mediante una opción numérica ingresada por el usuario.
     *
     * @return El formato seleccionado como cadena ("csv", "xlsx" o "pdf"), o {@code null} si la opción es inválida.
     */
    private String seleccionarFormato() {
        while (true) {
            System.out.println("\nSeleccione el formato de salida:");
            System.out.println("1. CSV");
            System.out.println("2. Excel (.xlsx)");
            System.out.println("3. PDF");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    return "csv";
                case "2":
                    return "xlsx";
                case "3":
                    return "pdf";
                default:
                    System.out.println("❌ Opción inválida. Debe seleccionar 1, 2 o 3.");
            }
        }
    }

    /**
     * Pausa la ejecución por un tiempo determinado para mejorar la experiencia del usuario.
     *
     * @param milisegundos Cantidad de milisegundos a esperar.
     */
    private void esperar(int milisegundos) {
        try {
            TimeUnit.MILLISECONDS.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
