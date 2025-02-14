package com.andresazcona;

import java.util.Scanner;

public class InterfazConsola {
    private final Scanner scanner = new Scanner(System.in);
    private final ClearFinanceFacade facade = new ClearFinanceFacade();

    public void iniciar() {
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== ClearFinance ===");
            System.out.println("1. Generar informe unificado");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    generarInforme();
                    break;
                case "2":
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void generarInforme() {
        try {
            System.out.print("\nIngrese ruta de la carpeta con reportes: ");
            String carpeta = scanner.nextLine();

            System.out.print("Ingrese nombre para el archivo de salida (ej: informe.csv): ");
            String salida = scanner.nextLine();

            // Validar extensión .csv
            if (!salida.toLowerCase().endsWith(".csv")) {
                salida += ".csv";
            }

            facade.generarInformeUnificado(carpeta, salida);
            System.out.println("\nOperación completada con éxito!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}