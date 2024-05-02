package net.njema.web;

import lombok.AllArgsConstructor;
import net.njema.dtos.AccountHistoryDTO;
import net.njema.dtos.AccountOperationDTO;
import net.njema.dtos.EmployeeDTO;
import net.njema.dtos.EmployeeDetailsDTO;
import net.njema.entities.AccountOperation;
import net.njema.entities.Employee;
import net.njema.exceptions.BankAccountNotFoundException;
import net.njema.mappers.BankAccountMapperImpl;
import net.njema.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class EmployeeRestController {
    private EmployeeRepository employeeRepository;
    private BankAccountMapperImpl employeeMapper;

    @GetMapping("/employees")
    public List<EmployeeDTO> employees(){
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().map(employeeMapper::fromEmployee).collect(Collectors.toList());
    }
    @GetMapping("/employees/pages")
    public EmployeeDetailsDTO getEmployeesPage(@RequestParam(defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "10") int size){
        Page<Employee> employees = employeeRepository.findAll( PageRequest.of(page, size));
        EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
        List<EmployeeDTO> employee = employees.getContent().stream().map(employeeMapper::fromEmployee)
                        .collect(Collectors.toList());
       return EmployeeDetailsDTO.builder()
                .employees(employee)
                .currentPage(page)
                .TotalPage(employees.getTotalPages())
                .sizePage(size)
                .build();

    }

    @GetMapping("/employees/total")
    public Long employeesTotal(){
        return employeeRepository.count();
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO getEmployee(@PathVariable(name = "id") Long id)  {
        Employee employee = employeeRepository.findById(id).get();
        return employeeMapper.fromEmployee(employee);
    }

    @PostMapping("/employees/add")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO){ //@RequestBody : cad les données de customerDTO on va récupérer à partir de format JSON
        Employee employee = employeeMapper.fromEmployeeDTO(employeeDTO);
        return employeeMapper.fromEmployee(employee);
    }

    @PutMapping("/employees/edit/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        employeeDTO.setId(id);
        Employee employee = employeeMapper.fromEmployeeDTO(employeeDTO);
        Employee newEmployee = employeeRepository.save(employee);
        return employeeMapper.fromEmployee(newEmployee);
    }

    @DeleteMapping("/employees/delete/{id}")
    public void deleteEmployee(@PathVariable(name = "id") Long id)  {
        employeeRepository.deleteById(id);
    }
}
