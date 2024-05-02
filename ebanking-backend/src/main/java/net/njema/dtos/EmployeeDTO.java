package net.njema.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import lombok.NoArgsConstructor;
import net.njema.entities.Agency;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {
    private Long id ;
    private String FirstName;
    private String LastName;
    private String cin;
    private String gender;
    private String email;
    private int age;

    private Agency agency;
}
