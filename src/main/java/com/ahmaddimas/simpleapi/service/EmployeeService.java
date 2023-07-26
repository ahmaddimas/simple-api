package com.ahmaddimas.simpleapi.service;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.repository.EmployeeRepository;
import com.ahmaddimas.simpleapi.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private GradeRepository gradeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, GradeRepository gradeRepository) {
        this.employeeRepository = employeeRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Employee> getList()
    {
        return this.employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId)
    {
        return this.employeeRepository.findById(employeeId);
    }

    public Employee addEmployee(Employee employee)
    {
        System.out.println(employee);
        if (employee.getGradeId() == null) {
            throw new IllegalStateException("grade_id is required");
        }
        Optional<Grade> grade = this.gradeRepository.findById(employee.getGradeId());
        if (!grade.isPresent()) {
            throw new IllegalStateException("Grade with id "+ employee.getGradeId() +" does not exists");
        }
        employee.setGrade(grade.get());
        return this.employeeRepository.save(employee);
    }

    public Employee editEmployee(Employee employee)
    {
        return this.employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId)
    {
        this.employeeRepository.deleteById(employeeId);
    }
}
