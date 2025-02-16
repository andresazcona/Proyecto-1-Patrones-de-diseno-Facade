package com.andresazcona.services;

import com.andresazcona.domain.Transaccion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorInforme {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Genera un informe en formato CSV con un resumen de ingresos, gastos y saldo.
     */
    public void generarInformeCSV(List<Transaccion> transacciones, String archivoSalida) {
        double totalIngresos = 0;
        double totalGastos = 0;

        for (Transaccion t : transacciones) {
            if (t.getTipo().equalsIgnoreCase("Ingreso")) {
                totalIngresos += t.getMonto();
            } else {
                totalGastos += t.getMonto();
            }
        }
        double saldoFinal = totalIngresos - totalGastos;

        try (FileWriter writer = new FileWriter(archivoSalida)) {
            // Escribir resumen de ingresos y gastos
            writer.write("Resumen del Reporte\n");
            writer.write(String.format("Total Ingresos: %.2f\n", totalIngresos));
            writer.write(String.format("Total Gastos: %.2f\n", totalGastos));
            writer.write(String.format("Saldo Final: %.2f\n\n", saldoFinal));

            // Escribir encabezados
            writer.write("Fecha,Banco Origen,Tipo,Descripción,Monto\n");

            // Escribir transacciones
            for (Transaccion t : transacciones) {
                writer.write(String.format("%s,%s,%s,\"%s\",%.2f%n",
                        SDF.format(t.getFecha()), t.getBancoOrigen(), t.getTipo(),
                        t.getDescripcion(), t.getMonto()));
            }

            System.out.println("✓ Informe CSV generado correctamente: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("❌ Error al generar el informe CSV: " + e.getMessage());
        }
    }

    /**
     * Genera un informe en formato Excel con un resumen de ingresos, gastos y saldo.
     */
    public void generarInformeExcel(List<Transaccion> transacciones, String archivoSalida) {
        double totalIngresos = 0;
        double totalGastos = 0;

        for (Transaccion t : transacciones) {
            if (t.getTipo().equalsIgnoreCase("Ingreso")) {
                totalIngresos += t.getMonto();
            } else {
                totalGastos += t.getMonto();
            }
        }
        double saldoFinal = totalIngresos - totalGastos;

        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(archivoSalida)) {
            Sheet sheet = workbook.createSheet("Transacciones");

            // Crear fila de resumen
            Row resumenRow1 = sheet.createRow(0);
            resumenRow1.createCell(0).setCellValue("Total Ingresos:");
            resumenRow1.createCell(1).setCellValue(totalIngresos);

            Row resumenRow2 = sheet.createRow(1);
            resumenRow2.createCell(0).setCellValue("Total Gastos:");
            resumenRow2.createCell(1).setCellValue(totalGastos);

            Row resumenRow3 = sheet.createRow(2);
            resumenRow3.createCell(0).setCellValue("Saldo Final:");
            resumenRow3.createCell(1).setCellValue(saldoFinal);

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(4);
            String[] headers = {"Fecha", "Banco Origen", "Tipo", "Descripción", "Monto"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Llenar las transacciones
            int rowNum = 5;
            for (Transaccion t : transacciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(SDF.format(t.getFecha()));
                row.createCell(1).setCellValue(t.getBancoOrigen());
                row.createCell(2).setCellValue(t.getTipo());
                row.createCell(3).setCellValue(t.getDescripcion());
                row.createCell(4).setCellValue(t.getMonto());
            }

            workbook.write(fos);
            System.out.println("✓ Informe Excel generado correctamente: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("❌ Error al generar el informe Excel: " + e.getMessage());
        }
    }

    /**
     * Genera un informe en formato PDF con un resumen de ingresos, gastos y saldo.
     */
    public void generarInformePDF(List<Transaccion> transacciones, String archivoSalida) {
        double totalIngresos = 0;
        double totalGastos = 0;

        for (Transaccion t : transacciones) {
            if (t.getTipo().equalsIgnoreCase("Ingreso")) {
                totalIngresos += t.getMonto();
            } else {
                totalGastos += t.getMonto();
            }
        }
        double saldoFinal = totalIngresos - totalGastos;

        try (PdfWriter writer = new PdfWriter(archivoSalida);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Agregar resumen de ingresos y gastos
            document.add(new Paragraph("Resumen del Reporte").setBold().setFontSize(14));
            document.add(new Paragraph(String.format("Total Ingresos: %.2f", totalIngresos)));
            document.add(new Paragraph(String.format("Total Gastos: %.2f", totalGastos)));
            document.add(new Paragraph(String.format("Saldo Final: %.2f\n", saldoFinal)));

            // Agregar tabla con transacciones
            Table table = new Table(5);
            table.addHeaderCell("Fecha");
            table.addHeaderCell("Banco Origen");
            table.addHeaderCell("Tipo");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Monto");

            for (Transaccion t : transacciones) {
                table.addCell(SDF.format(t.getFecha()));
                table.addCell(t.getBancoOrigen());
                table.addCell(t.getTipo());
                table.addCell(t.getDescripcion());
                table.addCell(String.format("%.2f", t.getMonto()));
            }

            document.add(table);
            System.out.println("✓ Informe PDF generado correctamente: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("❌ Error al generar el informe PDF: " + e.getMessage());
        }
    }
}
