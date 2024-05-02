package net.njema.services;

import net.njema.dtos.*;
import net.njema.exceptions.BankAccountNotFoundException;
import net.njema.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long id) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialiseBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    CurrentAccountDTO saveCurrentAccount(double initialiseBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String id,double amount,String description) throws BankAccountNotFoundException;
    void credit(String id,double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination, double amount) throws BankAccountNotFoundException;
    List<BankAccountDTO> bankAccountList();
    List<AccountOperationDTO> accountHistory(String id);
    AccountHistoryDTO getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;
    List<CustomerDTO> searchCustumer(String keyword);
}
