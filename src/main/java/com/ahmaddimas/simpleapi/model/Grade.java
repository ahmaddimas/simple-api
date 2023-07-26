package com.ahmaddimas.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Integer bonusPercentage;

    public Grade() {}

    public Grade(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(Integer bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
