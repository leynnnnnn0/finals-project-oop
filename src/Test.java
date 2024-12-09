import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        try {
            Document document = new Document();

            String pdfPath = "certificates/barangay-certificate.pdf";
            String imagePath = "src/images/loginBanner.png";

            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font regularFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            try {
                Image img = Image.getInstance(imagePath);
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleToFit(50, 50);
                document.add(img);
            } catch (Exception e) {
                System.out.println("Image not found: " + e.getMessage());
            }

            // Header Texts
            Paragraph repub = new Paragraph("REPUBLIKA NG PILIPINAS", boldFont);
            repub.setAlignment(Element.ALIGN_CENTER);
            document.add(repub);

            Paragraph city = new Paragraph("LUNGSOD NG CAVITE", boldFont);
            city.setAlignment(Element.ALIGN_CENTER);
            document.add(city);

            // Certificate Title
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            Paragraph title = new Paragraph("BARANGAY CERTIFICATE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add some spacing
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            // Salutation
            Paragraph salutation = new Paragraph("To Whom It May Concern:", regularFont);
            document.add(salutation);
            document.add(new Paragraph("\n"));

            // Certificate Body
            String fullName = "Juan P. Dela Cruz";
            String requesterAddress = "Lavanya Home Subdivision Phase 2";
            String baranggayAddress = "Barangay 32, Cavite City";

            Paragraph body = new Paragraph();

            body.add(new Phrase("       This is to certify that ", regularFont));
            body.add(new Phrase(fullName, boldFont));
            body.add(new Phrase(" of ", regularFont));
            body.add(new Phrase(requesterAddress, boldFont));
            body.add(new Phrase(" is a bonafide resident of ", regularFont));
            body.add(new Phrase(baranggayAddress, boldFont));
            body.add(new Phrase(".", regularFont));
            body.add(new Phrase(" The above named person is of good moral standing and has no derogatory record on file of whatsoever as per our record."));
            document.add(body);

            document.add(new Paragraph("\n"));

            // Additional Certification
            Paragraph additionalCert = new Paragraph(
                    "       This is to certify further that our community experienced inclement weather last night " +
                            "and their house is inundated with waters from the heavy rain.", regularFont);
            document.add(additionalCert);

            // Purpose
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            Paragraph purposeIntro = new Paragraph("This certification is issued upon the request of the concerned for:", regularFont);
            purposeIntro.setAlignment(Element.ALIGN_CENTER);
            document.add(purposeIntro);

            document.add(new Paragraph("\n"));


            Paragraph purpose = new Paragraph("WORK RELATED REQUIREMENT", boldFont);
            purpose.setAlignment(Element.ALIGN_CENTER);
            document.add(purpose);
            document.add(new Paragraph("\n"));

            // Date and Location
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            String formattedDate = sdf.format(new Date());

            Paragraph issuedDate = new Paragraph("Issued this " + formattedDate + " at " + baranggayAddress, regularFont);
            issuedDate.setAlignment(Element.ALIGN_CENTER);
            document.add(issuedDate);

            // Add signature area
            document.add(new Paragraph("\n\n\n"));

            // Create a table for the signature to right-align it
            PdfPTable signatureTable = new PdfPTable(1);
            signatureTable.setWidthPercentage(30); // Only take up 30% of the width
            signatureTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Signature Name
            PdfPCell nameCell = new PdfPCell(new Paragraph("Travis Scott", boldFont));
            nameCell.setBorder(Rectangle.NO_BORDER);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            signatureTable.addCell(nameCell);

            // Signature Line
            PdfPCell lineCell = new PdfPCell(new Paragraph("_____________________", regularFont));
            lineCell.setBorder(Rectangle.NO_BORDER);
            lineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            signatureTable.addCell(lineCell);

            // Position
            PdfPCell positionCell = new PdfPCell(new Paragraph("PUNONG BARANGAY", boldFont));
            positionCell.setBorder(Rectangle.NO_BORDER);
            positionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            signatureTable.addCell(positionCell);

            document.add(signatureTable);

            document.close();

            System.out.println("PDF Created successfully at " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}