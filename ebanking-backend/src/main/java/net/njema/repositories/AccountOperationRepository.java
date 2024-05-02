package net.njema.repositories;

import net.njema.entities.AccountOperation;
import net.njema.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccount_Id(String accountID); // _Id ou Id : criteria
    Page<AccountOperation> findByBankAccount_Id(String accountID, Pageable pageable); // _Id ou Id : criteria
    Page<AccountOperation> findByBankAccountIdOrderByDateDesc(String accountId, Pageable pageable);



     List<AccountOperation> findAccountOperationByBankAccount(BankAccount bankAccount);

}
