package com.andresazcona.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representa una transacción financiera con información sobre la fecha, descripción,
 * monto, tipo de transacción y el banco de origen.
 * Implementa {@link Comparable} para permitir la comparación de transacciones por fecha.
 */
public class Transaccion implements Comparable<Transaccion> {
    private Date fecha;
    private String descripcion;
    private double monto;
    private String tipo;
    private String bancoOrigen;

    /**
     * Constructor de la clase Transaccion.
     * Convierte una fecha en formato de cadena a un objeto {@link Date}.
     *
     * @param fechaStr    Fecha de la transacción en formato "yyyy-MM-dd".
     * @param descripcion Descripción de la transacción.
     * @param monto       Monto de la transacción.
     * @param tipo        Tipo de transacción (ej. ingreso o gasto).
     * @param bancoOrigen Nombre del banco de origen.
     * @throws ParseException Si el formato de la fecha no es válido.
     */
    public Transaccion(String fechaStr, String descripcion, double monto, String tipo, String bancoOrigen) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.fecha = sdf.parse(fechaStr);
        this.descripcion = descripcion;
        this.monto = monto;
        this.tipo = tipo;
        this.bancoOrigen = bancoOrigen;
    }

    /**
     * Obtiene la fecha de la transacción.
     *
     * @return Fecha de la transacción.
     */
    public Date getFecha() { return fecha; }

    /**
     * Obtiene la descripción de la transacción.
     *
     * @return Descripción de la transacción.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Obtiene el monto de la transacción.
     *
     * @return Monto de la transacción.
     */
    public double getMonto() { return monto; }

    /**
     * Obtiene el tipo de transacción (ej. ingreso o gasto).
     *
     * @return Tipo de la transacción.
     */
    public String getTipo() { return tipo; }

    /**
     * Obtiene el banco de origen de la transacción.
     *
     * @return Nombre del banco de origen.
     */
    public String getBancoOrigen() { return bancoOrigen; }

    /**
     * Compara esta transacción con otra en función de la fecha.
     *
     * @param otra Otra transacción a comparar.
     * @return Un valor negativo si esta transacción es anterior, 0 si son iguales,
     *         y un valor positivo si es posterior.
     */
    @Override
    public int compareTo(Transaccion otra) {
        return this.fecha.compareTo(otra.fecha);
    }
}
