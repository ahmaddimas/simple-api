package com.ahmaddimas.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long salary;

    @Transient
    @JsonProperty(value = "grade_id")
    private Integer gradeId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
    private Grade grade;

    public Employee() {}

    public Employee(String name, Long salary, Grade grade) {
        this.name = name;
        this.salary = salary;
        this.grade = grade;
    }

    public Employee(String name, Long salary, Integer gradeId) {
        this.name = name;
        this.salary = salary;
        this.gradeId = gradeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @JsonIgnore
    public Integer getGradeId() {
        return this.gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @JsonProperty("salary_bonus")
    public Long getSalaryBonus() {
        long totalBonus = this.salary != null ? this.salary : 0;
        if (this.getGrade() != null && this.getGrade().getBonusPercentage() != null) {
            int bonusPercentage = this.getGrade().getBonusPercentage();
            totalBonus = this.salary + (this.salary * bonusPercentage / 100);
        }
        return totalBonus;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", gradeId=" + gradeId +
                ", grade=" + grade +
                '}';
    }
}
