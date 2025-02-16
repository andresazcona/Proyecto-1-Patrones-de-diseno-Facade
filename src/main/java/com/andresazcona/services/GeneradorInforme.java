package com.andresazcona.services;

import com.andresazcona.domain.Transaccion;
import org.apache.poi.ss.usermodel.*;  // Apache POI para Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.kernel.pdf.*;  // iText para PDF
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorInforme {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Genera un informe en formato CSV.
     */
    public void generarInformeCSV(List<Transaccion> transacciones, String archivoSalida) {
        try (FileWriter writer = new FileWriter(archivoSalida)) {
            writer.write("Fecha,Banco Origen,Tipo,Descripción,Monto\n");

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
     * Genera un informe en formato Excel (.xlsx).
     */
    public void generarInformeExcel(List<Transaccion> transacciones, String archivoSalida) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(archivoSalida)) {
            Sheet sheet = workbook.createSheet("Transacciones");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Fecha", "Banco Origen", "Tipo", "Descripción", "Monto"};

            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);  // 🔹 Evita conflicto con iText
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
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
     * Genera un informe en formato PDF.
     */
    public void generarInformePDF(List<Transaccion> transacciones, String archivoSalida) {
        try (PdfWriter writer = new PdfWriter(archivoSalida);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Informe de Transacciones").setBold().setFontSize(14));
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
