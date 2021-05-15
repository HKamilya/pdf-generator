package ru.kpfu.itis.converterdemo.service;


import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.converterdemo.entity.Pdf;


import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class ConvertingService {


    public String createPdf(List<Pdf> pdfs) {
        try {
            Date date = new Date();
            String filename = date.getTime() + ".pdf";
            String FILE = "data/";
            String name = FILE + filename;
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            document.setMargins(19, 19, 26, 60);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name));
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            document.open();
            addData(document, pdfs);

            document.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addData(Document document, List<Pdf> pdfs) {
        for (Pdf pdf : pdfs) {
            BaseFont times = null;
            try {
                times = BaseFont.createFont("8277.ttf", "cp1251", BaseFont.EMBEDDED);

                Font font = new Font(times, 11, Font.NORMAL);
                Font font9 = new Font(times, 10, Font.NORMAL);
                Font fontBold = new Font(times, 11, Font.BOLD);
                Font fontTableHead = new Font(times, 9, Font.BOLD);
                Font fontTable = new Font(times, 9, Font.NORMAL);
                Image img = Image.getInstance("kfu.jpg");
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
                cell = new PdfPCell(new Phrase(pdf.getHeader().getInstitute(), font));
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
                cell = new PdfPCell(new Phrase(pdf.getHeader().getCountOfStudents(), font));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPaddingTop(6);
                tableHead.addCell(cell);
                cell = new PdfPCell(new Phrase("Номер отчета:", font));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPaddingTop(6);
                tableHead.addCell(cell);
                cell = new PdfPCell(new Phrase(pdf.getHeader().getNumber(), font));
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
                Phrase phrase = new Phrase("Перечень участников конференции " + pdf.getDate() + ":", font9);

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
                for (int i = 0; i < pdf.getEntities().size(); i++) {
                    if (i % 2 == 0) {
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getFormed().getDate() + "\n" +
                                pdf.getEntities().get(i).getFormed().getTime(), fontTable));
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        cell = addDataToCell(pdf, fontTable, cell, table, i);
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getCredited().getDate() + "\n" +
                                pdf.getEntities().get(i).getCredited().getTime(), fontTable));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        cell = addCell(pdf, fontTable, cell, table, i);
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        cell.setPaddingRight(3);
                        cell.setPaddingLeft(3);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getEmployee().getFullname() + "\n" +
                                pdf.getEntities().get(i).getEmployee().getPosition(), fontTable));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        cell = addPadding(pdf, fontTable, cell, table, i);
                        cell.setBackgroundColor(new BaseColor(240, 240, 240));
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getFormed().getDate() + "\n" +
                                pdf.getEntities().get(i).getFormed().getTime(), fontTable));
                        cell = addDataToCell(pdf, fontTable, cell, table, i);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getCredited().getDate() + "\n" +
                                pdf.getEntities().get(i).getCredited().getTime(), fontTable));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell = addCell(pdf, fontTable, cell, table, i);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getEmployee().getFullname() + "\n" +
                                pdf.getEntities().get(i).getEmployee().getPosition(), fontTable));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell = addPadding(pdf, fontTable, cell, table, i);
                        table.addCell(cell);
                    }
                }

                Paragraph paragraph = new Paragraph("Примечание: время указано в часовом поясе MSK (UTC+3) в соответствии с системными часами сервера или АРМ.", font9);
                document.add(table);
                document.add(paragraph);
                document.newPage();
                document.setPageCount(1);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static PdfPCell addCell(Pdf pdf, Font fontTable, PdfPCell cell, PdfPTable table, int i) {
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getComment().getDate() + ",\n"
                + pdf.getEntities().get(i).getComment().getComment() + ",\n"
                + pdf.getEntities().get(i).getComment().getVersion(), fontTable));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static PdfPCell addPadding(Pdf pdf, Font fontTable, PdfPCell cell, PdfPTable table, int i) {
        cell.setPaddingRight(30);
        cell.setPaddingLeft(30);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getIpAddress(), fontTable));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static PdfPCell addDataToCell(Pdf pdf, Font fontTable, PdfPCell cell, PdfPTable table, int i) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(pdf.getEntities().get(i).getFormalized().getDate() + "\n" +
                pdf.getEntities().get(i).getFormalized().getTime(), fontTable));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }


    public static class HeaderFooterPageEvent extends PdfPageEventHelper {

        private PdfTemplate t;

        public void onOpenDocument(PdfWriter writer, Document document) {
            t = writer.getDirectContent().createTemplate(30, 16);
            try {
                Image total = Image.getInstance(t);
                total.setRole(PdfName.ARTIFACT);
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
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
                throw new ExceptionConverter(de);
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

}
