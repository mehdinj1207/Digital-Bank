package net.njema.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class EmployeeDetailsDTO {
    private String id;
    private int currentPage;
    private int TotalPage;
    private int sizePage;
    List<EmployeeDTO> employees;
}
