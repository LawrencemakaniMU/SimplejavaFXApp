package studentregistration;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StudentController {
    // Controls from the FXML (names must match fx:id)
    @FXML private TextField nameField;
    @FXML private ComboBox<String> programBox;
    @FXML private Button saveButton;
    @FXML private Label messageLabel;
    @FXML private Label countLabel;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> programColumn;

    private final StudentService service = new StudentService();

    @FXML
    private void initialize() {
        // Fill the combo box and connect the table to the observable list
        programBox.setItems(FXCollections.observableArrayList(StudentService.PROGRAMS));
        studentTable.setItems(service.getStudents());

        // Columns read properties from each Student
        nameColumn.setCellValueFactory(row -> row.getValue().nameProperty());
        programColumn.setCellValueFactory(row -> row.getValue().programProperty());

        // Disable Save until the form is complete
        saveButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> nameField.getText().isBlank(),
                        nameField.textProperty())
                        .or(programBox.valueProperty().isNull()));

        // Live student counter
        countLabel.textProperty().bind(
                Bindings.size(service.getStudents()).asString("Registered students: %d"));
    }

    @FXML
    private void handleSave() {
        // Validate through the service; on error keep the input
        try {
            service.register(nameField.getText(), programBox.getValue());
        } catch (IllegalArgumentException e) {
            showMessage(e.getMessage(), true);
            nameField.requestFocus();
            return;
        }

        showMessage("Student registered successfully.", false);
        clearForm();
    }

    @FXML
    private void handleClear() {
        clearForm();
        messageLabel.setText("");
    }

    // Empty the inputs and put the cursor back in the name field
    private void clearForm() {
        nameField.clear();
        programBox.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    private void showMessage(String text, boolean isError) {
        messageLabel.getStyleClass().removeAll("message-ok", "message-error");
        messageLabel.getStyleClass().add(isError ? "message-error" : "message-ok");
        messageLabel.setText(text);
    }
}
