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
public class EmployeeService implements EmployeeServiceInterface {

    protected EmployeeRepository employeeRepository;
    protected GradeRepository gradeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, GradeRepository gradeRepository) {
        this.employeeRepository = employeeRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Employee> getList(String name) {
        List<Employee> employeeList;
        if (!name.isEmpty()) {
            employeeList = this.employeeRepository.findAllByName(name);
        } else {
            employeeList = this.employeeRepository.findAll();
        }
        return employeeList;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee with id "+ employeeId +" does not exists"));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        Optional<Grade> grade = this.gradeRepository.findById(employee.getGradeId());
        if (!grade.isPresent()) {
            throw new RuntimeException("Grade with id "+ employee.getGradeId() +" does not exists");
        }
        employee.setId(null);
        employee.setGrade(grade.get());
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee editEmployee(Long employeeId, Employee employee) {
        Employee savedEmployee = this.employeeRepository.findById(employeeId).orElse(null);
        if (savedEmployee == null) {
            throw new RuntimeException("Employee with id "+ employeeId +" does not exists");
        }
        Grade grade = this.gradeRepository.findById(employee.getGradeId()).orElse(null);
        if (grade == null) {
            throw new RuntimeException("Grade with id "+ employee.getGradeId() +" does not exists");
        }
        savedEmployee.setName(employee.getName());
        savedEmployee.setSalary(employee.getSalary());
        savedEmployee.setGrade(grade);
        return this.employeeRepository.save(savedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        this.employeeRepository.deleteById(employeeId);
    }
}
