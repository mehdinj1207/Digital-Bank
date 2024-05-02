package net.njema.dtos;

import net.njema.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDTO {
    private String type;
    private String id;
    private Date creatAt;
    private double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customerDTO;

}
