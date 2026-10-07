package customermanager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerDao implements CustomerDao {
    private final List<Customer> store = new ArrayList<>();

    public InMemoryCustomerDao() {
        // A little sample data so the table is not empty on first launch
        store.add(new Customer("Mary Banda", "Central"));
        store.add(new Customer("James Phiri", "Lusaka"));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public void save(Customer customer) {
        store.add(customer);
    }

    @Override
    public void delete(Customer customer) {
        store.remove(customer);
    }
}
