package com.andresazcona.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaccion implements Comparable<Transaccion> {
    private Date fecha;
    private String descripcion;
    private double monto;
    private String tipo;
    private String bancoOrigen;

    public Transaccion(String fechaStr, String descripcion, double monto, String tipo, String bancoOrigen) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.fecha = sdf.parse(fechaStr);
        this.descripcion = descripcion;
        this.monto = monto;
        this.tipo = tipo;
        this.bancoOrigen = bancoOrigen;
    }

    public Date getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public double getMonto() { return monto; }
    public String getTipo() { return tipo; }
    public String getBancoOrigen() { return bancoOrigen; }

    @Override
    public int compareTo(Transaccion otra) {
        return this.fecha.compareTo(otra.fecha);
    }
}