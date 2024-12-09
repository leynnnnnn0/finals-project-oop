import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Blotter;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class BlotterController implements Initializable {
    public MFXTableView<Blotter> table;
    public AnchorPane blottersIndexPane;
    public AnchorPane blotterCreatePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setTableData();
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

        table.getTableColumns().addAll(referenceNumberColumn, complainantColumn, againstColumn, dateColumn, timeColumn, notedByColumn);

    }

    public void openCreateNewBlotter(ActionEvent actionEvent) {
        blottersIndexPane.setVisible(false);
        blotterCreatePane.setVisible(true);
    }

    public void createNewBlotter(ActionEvent actionEvent) {
    }

    public void backToIndex(ActionEvent actionEvent) {
        blottersIndexPane.setVisible(true);
        blotterCreatePane.setVisible(false);
    }
}
