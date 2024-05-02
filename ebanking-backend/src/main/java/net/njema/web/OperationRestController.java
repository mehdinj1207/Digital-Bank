package net.njema.web;

import jdk.dynalink.Operation;
import lombok.AllArgsConstructor;
import net.njema.dtos.AccountOperationDTO;
import net.njema.entities.AccountOperation;
import net.njema.mappers.BankAccountMapperImpl;
import net.njema.repositories.AccountOperationRepository;
import net.njema.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class OperationRestController {
    AccountOperationRepository accountOperationRepository;
    BankAccountRepository bankAccountRepository;
    BankAccountMapperImpl bankAccountMapper;
    @GetMapping("/operations")
    public List<AccountOperationDTO> getOperations(){
        List<AccountOperation> allOperations = accountOperationRepository.findAll();
        return allOperations.stream().map(bankAccountMapper::fromAccountOperation).collect(Collectors.toList());
    }
    @GetMapping("/operations/{idAccount}")
    List<AccountOperationDTO> getAccountOperations(@PathVariable String idAccount){
        List<AccountOperation> accountOperationByBankAccount = accountOperationRepository.findAccountOperationByBankAccount(bankAccountRepository.findById(idAccount).get());
        return accountOperationByBankAccount.stream().map(bankAccountMapper::fromAccountOperation).collect(Collectors.toList());
    }
}
