package customermanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CustomerService {
    // Short demo list, not a full list of provinces
    public static final List<String> PROVINCES = List.of(
            "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
            "Muchinga", "Northern", "North-Western", "Southern", "Western");

    private final CustomerDao dao;

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    public CustomerService(CustomerDao dao) {
        this.dao = dao;
        customers.setAll(dao.findAll());   // load whatever is already stored
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(String name, String province) throws ValidationException {
        String cleanName = (name == null) ? "" : name.trim();

        if (cleanName.isEmpty()) {
            throw new ValidationException("Enter the customer name.", ValidationException.Field.NAME);
        }
        if (province == null) {
            throw new ValidationException("Choose a province.", ValidationException.Field.PROVINCE);
        }

        Customer customer = new Customer(cleanName, province);
        dao.save(customer);
        customers.add(customer);
    }

    public void deleteCustomer(Customer customer) {
        dao.delete(customer);
        customers.remove(customer);
    }
}
