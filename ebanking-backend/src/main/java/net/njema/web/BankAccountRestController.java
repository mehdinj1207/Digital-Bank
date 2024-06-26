package net.njema.web;

import net.njema.dtos.*;
import net.njema.exceptions.BalanceNotSufficientException;
import net.njema.exceptions.BankAccountNotFoundException;
import net.njema.repositories.BankAccountRepository;
import net.njema.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Web Service RestFull ou API**/
@RestController
@AllArgsConstructor
@CrossOrigin("*") // autorise tous les domaines
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    private BankAccountRepository bankAccountRepository;

    @GetMapping("/accounts")
    public List<BankAccountDTO> Accounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/total")
    public Long compterAccounts(){
        return bankAccountRepository.count();
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }
    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String id){
        return bankAccountService.accountHistory(id);
    }
    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(id,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
    /*
    @PostMapping("/accounts")
    public BankAccountDTO saveAccount(@RequestBody double initialiseBalance, @RequestBody double overDraft, @RequestBody Long customerId ) {
        CustomerDTO customerDTO=bankAccountService.getCustomerById(customerId);
        BankAccountDTO bankAccountDTO=bankAccountService.getBankAccount(c);
        if (bankAccountDTO instanceof CurrentAccountDTO) {
            return bankAccountService.saveCurrentAccount(initialiseBalance,overDraft,customerId);
        } else {
            return bankAccountService.saveSavingAccount();
        }
    }

/*
    @PutMapping("/accounts/{id}")
    public BankAccountDTO updateAccount(@PathVariable Long id, @RequestBody BankAccountDTO customerDTO){
        customerDTO.setId(id);
        CustomerDTO newCustomerDTO = bankAccountService.updateCustomer(customerDTO);
        return newCustomerDTO;
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }

*/
}
