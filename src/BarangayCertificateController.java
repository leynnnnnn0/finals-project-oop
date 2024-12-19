import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.BarangayCertificate;
import model.Blotter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BarangayCertificateController implements Initializable {
    public MFXTextField fullName;
    public TextArea completeAddress;
    public TextArea additionalCertification;
    private static final String PDF_PATH = "C:/Users/natha/IdeaProjects/BarangaySystem/certificates/barangay-certificate.pdf";
    public Label fullNameError;
    public Label completeAddressError;
    public Label additionalCertificationError;
    public MFXTableView<BarangayCertificate> table;
    public AnchorPane certificateIndexPane;
    public TextField searchField;
    public AnchorPane createCertificatePane;
    public Label reasonForRequestError;
    public MFXTextField reasonForRequest;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setTableData();
        table.autosizeColumnsOnInitialization();
    }

    private void setTableData()
    {
        BarangayCertificate blotterModel = new BarangayCertificate();
        ObservableList<BarangayCertificate> data = blotterModel.getAllRecords(BarangayCertificate.class);
        table.setItems(data);
    }

    private void setupTable() {
        MFXTableColumn<BarangayCertificate> id = new MFXTableColumn<>("Id");
        MFXTableColumn<BarangayCertificate> fullName = new MFXTableColumn<>("Full Name");
        MFXTableColumn<BarangayCertificate> completeAddress = new MFXTableColumn<>("Complete Address");
        MFXTableColumn<BarangayCertificate> reasonForRequest = new MFXTableColumn<>("Reason For Request");
        MFXTableColumn<BarangayCertificate> status = new MFXTableColumn<>("Status");

        id.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getId));
        fullName.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getFullName));
        completeAddress.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getCompleteAddress));
        reasonForRequest.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getReasonForRequest));
        status.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getStatus));

        table.getTableColumns().addAll(id, fullName, completeAddress, reasonForRequest, status);


        table.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            BarangayCertificate barangayCertificate = table.getSelectionModel().getSelectedValues().getFirst();



        });
    }

    public void openCreateBarangayCertificate(ActionEvent actionEvent){
        certificateIndexPane.setVisible(false);
        createCertificatePane.setVisible(true);
    }

    public void generateBarangayCertificate(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }
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
            String fullName = this.fullName.getText();
            String requesterAddress = this.completeAddress.getText();
            String baranggayAddress = "Barangay 32 Purok 3 limasawa, Georgia Cavite";
            String additionalCertification = this.additionalCertification.getText();

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

            if(!additionalCertification.isEmpty()) {
                document.add(new Paragraph("\n"));

                // Additional Certification
                Paragraph additionalCert = new Paragraph(
                        "       This is to certify further that " + additionalCertification, regularFont);
                document.add(additionalCert);
            }

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
            showPdfConfirmationModal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        fullNameError.setText("");
        completeAddressError.setText("");
        reasonForRequest.setText("");

        if (fullName.getText().trim().isEmpty()) {
            fullNameError.setText("Full name is required");
            isValid = false;
        }

        if (completeAddress.getText().trim().isEmpty()) {
            completeAddressError.setText("Complete address is required");
            isValid = false;
        }

        if (reasonForRequest.getText().trim().isEmpty()) {
            reasonForRequestError.setText("Reason for request is required");
            isValid = false;
        }

        return isValid;
    }


    private void showPdfConfirmationModal() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Certificate Generated");
        confirmationAlert.setHeaderText("Barangay Certificate Created Successfully");
        confirmationAlert.setContentText("Would you like to open the certificate?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                openPdfInBrowser();
            }
        });
    }

    private void openPdfInBrowser() {
        try {
            File pdfFile = new File(PDF_PATH);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                if (pdfFile.exists()) {
                    desktop.open(pdfFile);
                } else {
                    showErrorAlert("PDF File Not Found",
                            "The certificate file could not be located.");
                }
            } else {
                showErrorAlert("Operation Not Supported",
                        "Opening PDF files is not supported on this system.");
            }
        } catch (IOException e) {
            showErrorAlert("Error Opening PDF",
                    "An error occurred while trying to open the PDF: " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void search(KeyEvent keyEvent) {
    }

    public void backToIndex(ActionEvent actionEvent) {
        createCertificatePane.setVisible(false);
        certificateIndexPane.setVisible(true);
    }
}
