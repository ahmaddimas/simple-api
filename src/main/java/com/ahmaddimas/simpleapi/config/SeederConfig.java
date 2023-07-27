package com.ahmaddimas.simpleapi.config;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.repository.EmployeeRepository;
import com.ahmaddimas.simpleapi.repository.GradeRepository;
import com.sun.tools.javac.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeederConfig {

    @Bean
    CommandLineRunner commandLineRunner(GradeRepository gradeRepository, EmployeeRepository employeeRepository) {
        return args -> {
            if (gradeRepository.findAll().size() == 0) {
                Grade manager = new Grade("Manager");
                manager.setBonusPercentage(10);

                Grade supervisor = new Grade("Supervisor");
                supervisor.setBonusPercentage(6);

                Grade staff = new Grade("Staff");
                staff.setBonusPercentage(3);

                gradeRepository.saveAll(List.of(manager, supervisor, staff));

                Employee employee1 = new Employee("Jonah Bluesky", 7563000L, manager);
                Employee employee2 = new Employee("Stevenson Black", 5124000L, supervisor);
                Employee employee3 = new Employee("Susan Random", 3980000L, staff);

                employeeRepository.saveAll(List.of(employee1, employee2, employee3));
            }
        };
    }
}
