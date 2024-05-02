package net.njema.repositories;

import net.njema.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByNameContains(String keyword);
    //Autre méthode
    /*
    @Query("select c from Customer c where c.name like :kw")
    List<Customer> SearchCustomer(@Param(value = "kw") String keyword);
     */
}