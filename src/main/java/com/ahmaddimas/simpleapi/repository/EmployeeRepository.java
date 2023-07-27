package com.ahmaddimas.simpleapi.repository;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByName(String name);
}
