package com.andresazcona;

/**
 * Clase principal que inicia la aplicación ClearFinance.
 * Se encarga de ejecutar la interfaz de consola para la gestión y procesamiento de transacciones financieras.
 */
public class ClearFinanceApp {

    /**
     * Método principal que inicia la ejecución del programa.
     * Llama a la interfaz de consola para comenzar la interacción con el usuario.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta versión).
     */
    public static void main(String[] args) {
        new InterfazConsola().iniciar();
    }
}
