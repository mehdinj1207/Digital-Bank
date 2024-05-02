package net.njema.web;

import net.njema.dtos.CustomerDTO;
import net.njema.exceptions.CustomerNotFoundException;
import net.njema.repositories.CustomerRepository;
import net.njema.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Web Service RestFull ou API**/
@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@CrossOrigin("*") // autorise tous les domaines
public class CustomerRestController {
    private BankAccountService bankAccountService;
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomer(@RequestParam(defaultValue = "") String keyword){
        return bankAccountService.searchCustumer(keyword); //"%"+keyword+"%"
    }
    @GetMapping("/total")
    public Long customerAccounts(){
        return customerRepository.count();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return bankAccountService.getCustomerById(id);
    }

    @PostMapping("/add")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){ //@RequestBody : cad les données de customerDTO on va récupérer à partir de format JSON
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/edit/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(id);
        CustomerDTO newCustomerDTO = bankAccountService.updateCustomer(customerDTO);
        return newCustomerDTO;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }
}
