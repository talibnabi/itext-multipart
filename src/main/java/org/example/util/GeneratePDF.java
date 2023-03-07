package org.example.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.model.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePDF {

    public static ByteArrayInputStream generate(Person person) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(80);
            table.setWidths(new int[]{3, 5, 5, 5, 7});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("ID", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("NAME", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("USERNAME", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("EMAIL", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("DESCRIPTION", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(hcell);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase(person.getId().intValue()));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingLeft(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(person.getName()));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(person.getUsername())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingRight(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(person.getEmail())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingRight(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(person.getDescription())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingRight(5);
            table.addCell(cell);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (
                DocumentException ex) {

            Logger.getLogger(GeneratePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new

                ByteArrayInputStream(out.toByteArray());
    }
}