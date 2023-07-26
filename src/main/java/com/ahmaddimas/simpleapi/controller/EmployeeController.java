package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("list")
    public List<Employee> getList()
    {
        return this.employeeService.getList();
    }

    @GetMapping("show/{employeeId}")
    public Optional<Employee> show(@PathVariable(name = "employeeId") Long employeeId)
    {
        return this.employeeService.getEmployeeById(employeeId);
    }

    @PostMapping("add")
    public Employee add(@RequestBody Employee employee)
    {
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping("update")
    public Employee update(@RequestBody Employee employee)
    {
        return this.employeeService.editEmployee(employee);
    }

    @DeleteMapping("{employeeId}")
    public String delete(@PathVariable(name = "employeeId") Long employeeId)
    {
        this.employeeService.deleteEmployee(employeeId);
        return "Success";
    }
}
