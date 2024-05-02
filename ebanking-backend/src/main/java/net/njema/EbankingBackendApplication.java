package net.njema;

import net.njema.dtos.BankAccountDTO;
import net.njema.dtos.CustomerDTO;
import net.njema.entities.Agency;
import net.njema.entities.Employee;
import net.njema.exceptions.CustomerNotFoundException;
import net.njema.repositories.AgencyRepository;
import net.njema.repositories.EmployeeRepository;
import net.njema.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService,
                                        AgencyRepository agencyRepository,
                                        EmployeeRepository employeeRepository) {
        return args -> {

            Stream.of("ayoub", "hayat", "samira", "mustapha", "karima", "radouan").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });

            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentAccount(Math.random() * 10000, 100, customer.getId());
                    bankAccountService.saveSavingAccount(Math.random() * 120000, 5, customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });

            List<BankAccountDTO> bankAccountDTOS = bankAccountService.bankAccountList();

            for (BankAccountDTO bankAccountDTO : bankAccountDTOS) {
                for (int i = 0; i < 5; i++) {
                    bankAccountService.debit(bankAccountDTO.getId(), 1000 + Math.random() * 10000, "Debit");
                    bankAccountService.credit(bankAccountDTO.getId(), 1000 + Math.random() * 10000, "Credit");
                }
            }
            Stream.of("Sousse", "Mahdia", "Sfax", "Monastir", "Tunis", "Gabes").forEach(location -> {
                Agency agency = new Agency();
                agency.setLocation(location);
                agencyRepository.save(agency);
            });
            List<Agency> agencies = agencyRepository.findAll();

            Random random = new Random();

            String[] firstNames = {"Mehdi", "Ala", "Mohamed", "Fatma", "Aziz", "Dhia", "Baha"};
            String[] lastNames = {"Rjeb", "Njema", "Hmed", "Kacem", "Zaag", "Moahmed", "Khalil"};
            agencyRepository.findAll().forEach(agency -> {
                // Create 5 random employees for each agency
                for (int i = 0; i < 5; i++) {
                    // Generate random employee details
                    String firstName = firstNames[random.nextInt(firstNames.length)];
                    String lastName = lastNames[random.nextInt(lastNames.length)];
                    String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
                    int age = random.nextInt(41) + 20; // Generate random age between 20 and 60
                    String gender = random.nextBoolean() ? "Male" : "Female";
                    String cin = generateRandomString(8);

                    // Create and save employee entity
                    Employee employee = Employee.builder()
                            .FirstName(firstName)
                            .LastName(lastName)
                            .email(email)
                            .age(age)
                            .gender(gender)
                            .cin(cin)
                            .agency(agency)
                            .build();

                    employeeRepository.save(employee);
                }
            });



            System.out.println("\n </> By Mehdi NJEMA \n");
        };
    }
    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }
}