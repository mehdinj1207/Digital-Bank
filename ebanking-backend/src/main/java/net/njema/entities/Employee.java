package net.njema.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String FirstName;
    String LastName;
    String cin;
    String gender;
    String email;
    int age;
    @ManyToOne
    Agency agency;

}
