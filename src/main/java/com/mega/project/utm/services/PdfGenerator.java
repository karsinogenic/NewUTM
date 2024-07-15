package com.mega.project.utm.services;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

    private static void generatePdfTable(List<String> dataList, String outputPath) throws DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            PdfPTable table = new PdfPTable(1); // 1 column
            addTableHeader(table);
            addRows(table, dataList);

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static void addTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell(new Phrase("Data List"));
        cell.setColspan(1); // Number of columns in your table
        table.addCell(cell);
    }

    private static void addRows(PdfPTable table, List<String> dataList) {
        for (String data : dataList) {
            table.addCell(data);
        }
    }

}
