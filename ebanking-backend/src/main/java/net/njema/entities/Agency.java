package net.njema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String location;

    @OneToMany(mappedBy = "agency")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Employee> employees;
}
