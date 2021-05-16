package ru.kpfu.itis.converterdemo.service;


import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.converterdemo.entity.Entity;
import ru.kpfu.itis.converterdemo.entity.Pdf;
import ru.kpfu.itis.converterdemo.entity.Root;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ConvertingService {

    public String createPdf(List<Pdf> pdfs) {

        String filename = UUID.randomUUID().toString() + ".pdf";
        String FILE = "data/";
        String name = FILE + filename;
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(19, 19, 26, 60);
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(name));

            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            document.open();
            addData(document, pdfs);

            document.close();
            return filename;
        } catch (DocumentException | FileNotFoundException e) {
            throw new IllegalStateException();
        }
    }

    private static void addData(Document document, List<Pdf> pdfs) {
        for (Pdf pdf : pdfs) {
            BaseFont times = null;
            try {
                times = BaseFont.createFont("pdfUtils/8277.ttf", "cp1251", BaseFont.EMBEDDED);


                Font font = new Font(times, 11, Font.NORMAL);
                Font font9 = new Font(times, 10, Font.NORMAL);
                Font fontBold = new Font(times, 11, Font.BOLD);
                Font fontTableHead = new Font(times, 9, Font.BOLD);
                Font fontTable = new Font(times, 9, Font.NORMAL);
                Image img = null;
                try {
                    img = Image.getInstance("pdfUtils/kfu.jpg");

                    Paragraph paragraph1 = new Paragraph("Подготовленный отчет по данным \n по № ", font);

                    Chunk chunk1 = new Chunk(pdf.getNumber(), fontBold);
                    Phrase phrase1 = new Phrase();
                    phrase1.add(paragraph1);
                    phrase1.add(chunk1);
                    img.scalePercent(8f);
                    PdfPTable t = new PdfPTable(2);
                    t.setWidthPercentage(100); //Width 100%
                    t.setSpacingBefore(10f); //Space before table
                    t.setSpacingAfter(10f); //Space after table
                    PdfPCell cell = new PdfPCell(img);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    t.addCell(cell);
                    cell = new PdfPCell(phrase1);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    t.addCell(cell);
                    document.add(t);

                    PdfPTable tableHead = new PdfPTable(new float[]{9, 30});
                    tableHead.setWidthPercentage(100); //Width 100%
                    tableHead.setSpacingBefore(10f); //Space before table
                    tableHead.setSpacingAfter(10f); //Space after table
                    cell = new PdfPCell(new Phrase("Институт:", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase(pdf.getHeader().getInstitute().getName() +
                            " (ИНН: " + pdf.getHeader().getInstitute().getInn() + ")", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase("Логин:", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase(pdf.getHeader().getLogin(), font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase("Количество студентов:", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(pdf.getHeader().getCountOfStudents()), font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase("Номер отчета:", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(pdf.getHeader().getNumber()), font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase("Тип отчета:", font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);
                    cell = new PdfPCell(new Phrase(pdf.getHeader().getType(), font));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingTop(6);
                    tableHead.addCell(cell);

                    document.add(tableHead);
                    DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    Phrase phrase = new Phrase("Перечень участников конференции " + dateTimeFormat.format(pdf.getDate()) + ":", font9);

                    document.add(phrase);
                    PdfPTable table = new PdfPTable(new float[]{11, 11, 11, 25, 29, 11});
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);
                    cell = new PdfPCell(new Phrase("Сформирован", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingLeft(0);
                    cell.setPaddingRight(0);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Оформлен", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Зачислил", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);
                    table.setHeaderRows(1);

                    cell = new PdfPCell(new Phrase("Комметарий", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);
                    table.setHeaderRows(1);

                    cell = new PdfPCell(new Phrase("ФИО, Должность", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);
                    table.setHeaderRows(1);

                    cell = new PdfPCell(new Phrase("IP-адрес", fontTableHead));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPaddingTop(15);
                    cell.setPaddingBottom(15);
                    table.addCell(cell);
                    table.setHeaderRows(1);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

                    for (int i = 0; i < pdf.getEntities().size(); i++) {
                        if (i % 2 == 0) {
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getFormed()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getFormed()), fontTable));
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getFormalized()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getFormalized()), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getCredited()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getCredited()), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getComment().getDate()) + ",\n"
                                    + pdf.getEntities().get(i).getComment().getComment() + ",\n"
                                    + pdf.getEntities().get(i).getComment().getVersion(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            cell.setPaddingRight(3);
                            cell.setPaddingLeft(3);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getEmployee().getFullname() + "\n" +
                                    pdf.getEntities().get(i).getEmployee().getPosition(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            cell.setPaddingRight(30);
                            cell.setPaddingLeft(30);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getIpAddress(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBackgroundColor(new BaseColor(240, 240, 240));
                            table.addCell(cell);
                        } else {
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getFormed()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getFormed()), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getFormalized()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getFormalized()), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getCredited()) + "\n" +
                                    timeFormat.format(pdf.getEntities().get(i).getCredited()), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(dateFormat.format(pdf.getEntities().get(i).getComment().getDate()) + ",\n"
                                    + pdf.getEntities().get(i).getComment().getComment() + ",\n"
                                    + pdf.getEntities().get(i).getComment().getVersion(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPaddingRight(3);
                            cell.setPaddingLeft(3);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getEmployee().getFullname() + "\n" +
                                    pdf.getEntities().get(i).getEmployee().getPosition(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPaddingRight(30);
                            cell.setPaddingLeft(30);
                            table.addCell(cell);
                            cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getIpAddress(), fontTable));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                        }
                    }

                    Paragraph paragraph = new Paragraph("Примечание: время указано в часовом поясе MSK (UTC+3) в " +
                            "соответствии с системными часами сервера или АРМ.", font9);
                    document.add(table);
                    document.add(paragraph);
                    document.newPage();
                    document.setPageCount(1);
                } catch (BadElementException | IOException e) {
                    throw new IllegalStateException();
                }
            } catch (DocumentException | IOException e) {
                throw new IllegalStateException();
            }
        }
    }


    public static class HeaderFooterPageEvent extends PdfPageEventHelper {

        private PdfTemplate t;

        public void onOpenDocument(PdfWriter writer, Document document) {
            t = writer.getDirectContent().createTemplate(30, 16);
            try {
                Image total = Image.getInstance(t);
                total.setRole(PdfName.ARTIFACT);
            } catch (DocumentException de) {
                throw new IllegalStateException();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            addFooter(writer);
        }

        private void addFooter(PdfWriter writer) {
            PdfPTable footer = new PdfPTable(3);
            try {
                // set defaults
                footer.setWidths(new int[]{24, 2, 1});
                footer.setTotalWidth(527);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(40);
                footer.getDefaultCell().setBorderColor(BaseColor.WHITE);

                footer.addCell(new Paragraph());

                // add current page count
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                footer.addCell(new Phrase(String.format("%d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

                footer.addCell(new Paragraph());

                // write page
                PdfContentByte canvas = writer.getDirectContent();
                canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
                footer.writeSelectedRows(0, -1, 34, 50, canvas);
                canvas.endMarkedContentSequence();
            } catch (DocumentException de) {
                throw new IllegalStateException();
            }
        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                    new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                    totalWidth, 6, 0);
        }
    }

    public boolean checkValidity(Root root) {
        try {
            System.out.println(root);
            for (Pdf pdf : root.getPdfs()) {
                if (pdf.getDate().before(new Date()) & pdf.getHeader().getNumber() != 0
                        & pdf.getHeader().getInstitute().getInn() != 0 & pdf.getHeader().getInstitute().getName().length() != 0) {
                    for (Entity entity : pdf.getEntities()) {
                        if (entity.getFormalized().before(new Date()) & entity.getComment().getDate().before(new Date())
                                & entity.getFormed().before(new Date()) & entity.getCredited().before(new Date())
                                & entity.getEmployee().getPosition().length() != 0 & entity.getEmployee().getFullname().length() != 0) {
                            if (!entity.getIpAddress().matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$")) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
}
