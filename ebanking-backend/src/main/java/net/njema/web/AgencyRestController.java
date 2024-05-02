package net.njema.web;

import lombok.AllArgsConstructor;
import net.njema.dtos.AgencyDTO;
import net.njema.entities.Agency;
import net.njema.mappers.BankAccountMapperImpl;
import net.njema.repositories.AgencyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class AgencyRestController {
    private AgencyRepository agencyRepository;
    private BankAccountMapperImpl agencyMapper;

    @GetMapping("/agencies")
    public List<AgencyDTO> agencies(){
        List<Agency> allAgencies = agencyRepository.findAll();
        return allAgencies.stream().map(agencyMapper::fromAgency).collect(Collectors.toList());
    }

    @GetMapping("/agencies/total")
    public Long agenciesTotal(){
        return agencyRepository.count();
    }

    @GetMapping("/agencies/{id}")
    public AgencyDTO getAgency(@PathVariable(name = "id") Long id)  {
        Agency agency = agencyRepository.findById(id).get();
        return agencyMapper.fromAgency(agency);
    }

    @PostMapping("/agencies/add")
    public AgencyDTO saveAgency(@RequestBody AgencyDTO agencyDTO){ //@RequestBody : cad les données de customerDTO on va récupérer à partir de format JSON
        Agency agency = agencyMapper.fromAgencyDTO(agencyDTO);
        return agencyMapper.fromAgency(agency);
    }

    @PutMapping("/agencies/edit/{id}")
    public AgencyDTO updateAgency(@PathVariable Long id, @RequestBody AgencyDTO agencyDTO){
        agencyDTO.setId(id);
        Agency agency = agencyMapper.fromAgencyDTO(agencyDTO);
        Agency newAgency = agencyRepository.save(agency);
        return agencyMapper.fromAgency(newAgency);
    }

    @DeleteMapping("/agencies/delete/{id}")
    public void deleteAgency(@PathVariable(name = "id") Long id)  {
        agencyRepository.deleteById(id);
    }
}
