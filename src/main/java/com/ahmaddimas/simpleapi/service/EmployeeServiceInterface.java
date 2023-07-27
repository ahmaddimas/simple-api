package com.ahmaddimas.simpleapi.service;

import com.ahmaddimas.simpleapi.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceInterface {

    public List<Employee> getList(String name);

    public Employee getEmployeeById(Long employeeId);

    public Employee addEmployee(Employee employee);

    public Employee editEmployee(Long employeeId, Employee employee);

    public void deleteEmployee(Long employeeId);

}
