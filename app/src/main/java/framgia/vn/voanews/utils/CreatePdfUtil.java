package framgia.vn.voanews.utils;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import framgia.vn.voanews.R;

/**
 * Created by toannguyen201194 on 31/05/2016.
 */
public class CreatePdfUtil {
    private static final String NAMEPDF = "VOANEWS.COM";
    private static final int TITLE_SIZE = 20;
    private static final int DESCRIPTION_SIZE = 15;
    private static final int SPACING_BEFORE = 5;
    private static final String DATAFORMAT = "yyyyMMdd _HHmmss";
    private File fileName = null;

    public void printerPdf(String title, String pubdate, String enclosure, String description) throws IOException, DocumentException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        File pdfFolder = null;
        pdfFolder = new File(Environment.getExternalStorageDirectory() + File.separator + NAMEPDF);
        if (!pdfFolder.exists())
            pdfFolder.mkdirs();
        Date datePrinter = new Date();
        String timeStamp = new SimpleDateFormat(DATAFORMAT).format(datePrinter);
        fileName = new File(pdfFolder, timeStamp + ".pdf");
        OutputStream outputStream = new FileOutputStream(fileName);
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Paragraph paraTitle = new Paragraph();
        Paragraph paraDescription = new Paragraph();
        Phrase phadescription = new Phrase();
        Phrase phaTitle = new Phrase();
        Font titleFont = new Font(Font.FontFamily.COURIER, TITLE_SIZE, Font.BOLD);
        Font pubdateFont = new Font(Font.FontFamily.COURIER, DESCRIPTION_SIZE);
        Font descritionFont = new Font(Font.FontFamily.COURIER, DESCRIPTION_SIZE);
        pubdateFont.setColor(BaseColor.BLUE);
        paraDescription.setAlignment(Element.ALIGN_JUSTIFIED);
        Chunk chTitle = new Chunk(title, titleFont);
        Chunk chpudate = new Chunk(pubdate, pubdateFont);
        Chunk chdescription = new Chunk(description, descritionFont);
        Image image = Image.getInstance(new URL(enclosure));
        image.setAlignment(Chunk.ALIGN_JUSTIFIED);
        phaTitle.add(chTitle);
        phaTitle.add(Chunk.NEWLINE);
        phaTitle.add(chpudate);
        paraTitle.add(phaTitle);
        phadescription.add(Chunk.NEWLINE);
        phadescription.add(chdescription);
        paraDescription.setSpacingBefore(SPACING_BEFORE);
        paraDescription.add(phadescription);
        document.add(paraTitle);
        document.add(image);
        document.add(paraDescription);
        document.close();
    }

    public void viewPdf(Application application) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(fileName), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(application.getPackageManager()) != null) {
            application.startActivity(intent);
        } else {
            Toast.makeText(application, R.string.no_app_read_pdf, Toast.LENGTH_SHORT).show();
        }

    }
}
