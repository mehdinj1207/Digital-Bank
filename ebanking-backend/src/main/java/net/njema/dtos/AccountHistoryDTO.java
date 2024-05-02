package net.njema.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String id;
    private double balance;
    private int currentPage;
    private int TotalPage;
    private int sizePage;
    List<AccountOperationDTO> accountOperationDTOS;
}
