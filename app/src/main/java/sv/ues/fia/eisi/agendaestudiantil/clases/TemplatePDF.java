package sv.ues.fia.eisi.agendaestudiantil.clases;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.util.ArrayList;

import sv.ues.fia.eisi.agendaestudiantil.ui.calendario.CalendarUtils;

public class TemplatePDF {
    private Context context;
    public File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN,20, Font.BOLD);
    private Font fSubtitle = new Font(Font.FontFamily.TIMES_ROMAN,18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN,15, Font.BOLD, BaseColor.RED);

    public  TemplatePDF(Context context){
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDocument(){
        createFile();
        try {
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createFile(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),"PDF");
        if (!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder, "EventosDiarios"+ CalendarUtils.formattedDate(CalendarUtils.selectedDate.now())+".pdf");
    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTitles(String title, String subTitle, String date){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubtitle));
            addChildP(new Paragraph("Generado: " + date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles", e.toString());
        }
    }

    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text,fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]>eventos){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(110);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubtitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < eventos.size(); indexR++) {
                String[] row = eventos.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(50);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }


}
