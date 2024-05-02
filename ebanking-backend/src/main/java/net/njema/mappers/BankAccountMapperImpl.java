package net.njema.mappers;

import net.njema.dtos.*;
import net.njema.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    ModelMapper modelMapper=new ModelMapper();
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        /**[M1] Méthode Dynamique**/
        BeanUtils.copyProperties(customer, customerDTO); //source -> new
        /**[M2] Méthode Statique**/
        /*
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
         */
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        /**[M1] Méthode Dynamique**/
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO=new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO=new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation=new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO,accountOperation);
        return accountOperation;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
    public Employee fromEmployeeDTO(EmployeeDTO employeeDTO){
        return modelMapper.map(employeeDTO, Employee.class);
    }
    public EmployeeDTO fromEmployee(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Agency fromAgencyDTO(AgencyDTO agencyDTO){
        return modelMapper.map(agencyDTO, Agency.class);
    }
    public AgencyDTO fromAgency(Agency agency){
        return modelMapper.map(agency, AgencyDTO.class);
    }
}
