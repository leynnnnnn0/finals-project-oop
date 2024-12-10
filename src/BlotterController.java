import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Blotter;
import model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;

public class BlotterController implements Initializable {
    public MFXTableView<Blotter> table;
    public AnchorPane blottersIndexPane;
    public AnchorPane blotterCreatePane;
    public MFXTextField referenceNumber;
    public MFXTextField complainantReporter;
    public MFXTextField against;
    public MFXDatePicker reportedDate;
    public TextArea details;
    public Label referenceNumberError;
    public Label complainantReporterError;
    public Label againstError;
    public Label reportedDateError;
    public Label detailsError;
    public TextField searchField;
    public AnchorPane blotterViewPane;
    public Label infolistReportedDate;
    public Label infolistReportedTime;
    public Label infolistComplainantReporter;
    public Label infolistAgainst;
    public Label infoListDetails;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setTableData();
        table.autosizeColumnsOnInitialization();

        Random random = new Random();
        String reference = "BN-" + random.nextInt(111111, 999999);
        referenceNumber.setText(reference);
        referenceNumber.setDisable(true);
    }

    private void setTableData()
    {
        Blotter blotterModel = new Blotter();
        ObservableList<Blotter> data = blotterModel.getAllRecords(Blotter.class);
        table.setItems(data);
    }

    private void setupTable() {
        MFXTableColumn<Blotter> referenceNumberColumn = new MFXTableColumn<>("Reference Number");
        MFXTableColumn<Blotter> complainantColumn = new MFXTableColumn<>("Complainant/Report");
        MFXTableColumn<Blotter> againstColumn = new MFXTableColumn<>("Against");
        MFXTableColumn<Blotter> dateColumn = new MFXTableColumn<>("Reported Date");
        MFXTableColumn<Blotter> timeColumn = new MFXTableColumn<>("Reported Time");
        MFXTableColumn<Blotter> notedByColumn = new MFXTableColumn<>("Noted By");

        referenceNumberColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getReference_number));
        complainantColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getComplainant_or_reporter));
        againstColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getAgainst));
        dateColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getReported_date));
        timeColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getReported_time));
        notedByColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Blotter::getNoted_by));

        table.getTableColumns().addAll(referenceNumberColumn, complainantColumn, againstColumn, dateColumn, notedByColumn);


        table.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            Blotter blotter = table.getSelectionModel().getSelectedValues().getFirst();
            infolistReportedDate.setText(infolistReportedDate.getText() + " " + blotter.getReported_date());
            infolistReportedTime.setText(infolistReportedTime.getText() + " " + blotter.getReported_time());
            infolistComplainantReporter.setText(infolistComplainantReporter.getText() + " " + blotter.getComplainant_or_reporter());
            infolistAgainst.setText(infolistAgainst.getText() + " " + blotter.getAgainst());
            infoListDetails.setText("       " + blotter.getDetails());

            blotterViewPane.setVisible(true);
            blottersIndexPane.setVisible(false);


        });
    }

    public void openCreateNewBlotter(ActionEvent actionEvent) {
        blottersIndexPane.setVisible(false);
        blotterCreatePane.setVisible(true);
    }

    public void createNewBlotter(ActionEvent actionEvent) {
        complainantReporterError.setText("");
        reportedDateError.setText("");
        detailsError.setText("");
        againstError.setText("");

        boolean isValid = true;

        // Complainant/Reporter validation
        if (complainantReporter.getText().trim().isEmpty()) {
            complainantReporterError.setText("Complainant/Reporter is required");
            isValid = false;
        }

        if (reportedDate.getValue() == null) {
            reportedDateError.setText("Reported Date is required");
            isValid = false;
        } else {
            if (reportedDate.getValue().isAfter(java.time.LocalDate.now())) {
                reportedDateError.setText("Reported Date cannot be in the future");
                isValid = false;
            }
        }

        if (details.getText().trim().isEmpty()) {
            detailsError.setText("Details are required");
            isValid = false;
        }

        if (!isValid) {
            return;
        }
        Blotter blotter = new Blotter(
                referenceNumber.getText(),
                complainantReporter.getText(),
                "Nathaniel Alvarez",
                against.getText(),
                Time.valueOf(LocalTime.now()),
                Date.valueOf(reportedDate.getValue()),
                details.getText()
                );
        blotter.create();

        try {
            Document document = new Document();

            String pdfPath = "blotters/blotter.pdf";
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
            Paragraph title = new Paragraph("BARANGAY BLOTTER", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add some spacing
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            // Salutation
            Paragraph date = new Paragraph("Reported Date: " + blotter.getReported_date(), regularFont);
            Paragraph time = new Paragraph("Reported Time: " + blotter.getReported_time(), regularFont);
            Paragraph complainantReporter = new Paragraph("Complainant/Reporter: " + blotter.getComplainant_or_reporter(), regularFont);
            Paragraph against = new Paragraph("Against: " + blotter.getAgainst(), regularFont);
            Paragraph reportDetails = new Paragraph("Report Details: ", regularFont);

            document.add(date);
            document.add(time);
            document.add(complainantReporter);
            document.add(against);
            document.add(new Paragraph("\n"));
            document.add(reportDetails);
            document.add(new Paragraph("\n"));

            Paragraph body = new Paragraph();

            body.add(new Phrase("       " + blotter.getDetails(), regularFont));
            document.add(body);

            document.add(new Paragraph("\n"));

            // Purpose
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));



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
            PdfPCell positionCell = new PdfPCell(new Paragraph("Recorder by", boldFont));
            positionCell.setBorder(Rectangle.NO_BORDER);
            positionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            signatureTable.addCell(positionCell);

            document.add(signatureTable);

            document.close();

            System.out.println("PDF Created successfully at " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }


        blottersIndexPane.setVisible(true);
        blotterCreatePane.setVisible(false);

    }

    public void backToIndex(ActionEvent actionEvent) {
        blottersIndexPane.setVisible(true);
        blotterCreatePane.setVisible(false);
    }


    public void search(KeyEvent keyEvent) {
        Blotter blotterModel = new Blotter();
        ObservableList<Blotter> allRecords = blotterModel.getAllRecords(Blotter.class);

        ObservableList<Blotter> filteredRecords = allRecords.filtered(blotter ->
                blotter.getReference_number().toLowerCase().contains(searchField.getText().toLowerCase())
        );

        table.setItems(filteredRecords);
    }
}
