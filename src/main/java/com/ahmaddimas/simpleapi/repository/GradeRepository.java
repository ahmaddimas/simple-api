package com.ahmaddimas.simpleapi.repository;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
