package customermanager;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class CustomerController {
    // Controls from the FXML (names must match fx:id exactly)
    @FXML private Label nameLabel;
    @FXML private Label provinceLabel;
    @FXML private TextField nameField;
    @FXML private ComboBox<String> provinceBox;
    @FXML private Label statusLabel;
    @FXML private Label countLabel;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> provinceColumn;

    private final CustomerService service = new CustomerService(new InMemoryCustomerDao());

    @FXML
    private void initialize() {
        // Province choices and table data
        provinceBox.setItems(FXCollections.observableArrayList(CustomerService.PROVINCES));
        customerTable.setItems(service.getCustomers());

        // Columns read values from each Customer
        nameColumn.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getName()));
        provinceColumn.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getProvince()));

        // Keyboard and accessibility
        nameLabel.setLabelFor(nameField);
        provinceLabel.setLabelFor(provinceBox);

        customerTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                handleDelete();
            }
        });

        // Live customer counter
        countLabel.textProperty().bind(
                Bindings.size(service.getCustomers()).asString("Customers: %d"));

        nameField.requestFocus();
    }

    // Validate, then add the customer
    @FXML
    private void handleSave() {
        try {
            service.addCustomer(nameField.getText(), provinceBox.getValue());
        } catch (ValidationException e) {
            showStatus(e.getMessage(), true);
            if (e.getField() == ValidationException.Field.NAME) {
                nameField.requestFocus();
            } else {
                provinceBox.requestFocus();
            }
            return;
        }

        showStatus("Customer saved.", false);
        nameField.clear();
        provinceBox.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    // Ask for confirmation before deleting
    @FXML
    private void handleDelete() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        // Nothing selected? Explain what to do and stop.
        if (selected == null) {
            showStatus("Select a customer first.", true);
            return;
        }

        // Confirmation dialog with Delete and Cancel
        ButtonType deleteButton = new ButtonType("Delete");
        Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.getName() + " (" + selected.getProvince() + ")?",
                deleteButton, ButtonType.CANCEL);
        ask.setHeaderText("Confirm deletion");
        ask.initOwner(customerTable.getScene().getWindow());

        if (ask.showAndWait().orElse(ButtonType.CANCEL) == deleteButton) {
            service.deleteCustomer(selected);
            showStatus("Customer deleted.", false);
        } else {
            showStatus("Deletion cancelled.", false);
        }
    }

    // File > Close menu item
    @FXML
    private void handleClose() {
        ((Stage) customerTable.getScene().getWindow()).close();
    }

    private void showStatus(String text, boolean isError) {
        statusLabel.getStyleClass().removeAll("status-ok", "status-error");
        statusLabel.getStyleClass().add(isError ? "status-error" : "status-ok");
        statusLabel.setText(text);
    }
}
