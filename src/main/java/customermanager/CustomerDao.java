package customermanager;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();

    void save(Customer customer);

    void delete(Customer customer);
}
