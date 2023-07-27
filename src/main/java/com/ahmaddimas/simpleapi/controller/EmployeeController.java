package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.service.EmployeeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    protected EmployeeServiceInterface employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceInterface employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getList(@RequestParam(defaultValue = "") String name) {
        return this.employeeService.getList(name);
    }

    @GetMapping("{employeeId}")
    public Employee show(@PathVariable(name = "employeeId") Long employeeId) {
        return this.employeeService.getEmployeeById(employeeId);
    }

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        this.validateRequest(employee);
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping("{employeeId}")
    public Employee update(@PathVariable(name = "employeeId") Long employeeId, @RequestBody Employee employee) {
        this.validateRequest(employee);
        return this.employeeService.editEmployee(employeeId, employee);
    }

    @DeleteMapping("{employeeId}")
    public String delete(@PathVariable(name = "employeeId") Long employeeId) {
        this.employeeService.deleteEmployee(employeeId);
        return "Success";
    }

    private void validateRequest(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new RuntimeException("name is required");
        }
        if (employee.getSalary() == null) {
            throw new RuntimeException("salary is required");
        }
        if (employee.getGradeId() == null) {
            throw new RuntimeException("grade_id is required");
        }
    }
}
