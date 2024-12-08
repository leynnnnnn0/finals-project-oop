import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
public class Test {
    public static void main(String[] args) {
        try {
            Document document = new Document();

            String pdfPath = "UserReport.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

            document.open();

            document.add(new Paragraph("User Report"));
            document.add(new Paragraph("Generated on: " + new java.util.Date()));




            document.close();



        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


    }
}
