package com.ahmaddimas.simpleapi.service;

import com.ahmaddimas.simpleapi.model.Grade;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

public interface GradeServiceInterface {

    public List<Grade> getList(String name);

    public Grade getGradeById(Integer gradeId) throws Exception;

    public Grade addGrade(Grade grade);

    public Grade editGrade(Integer gradeId, Grade grade);

    public void deleteGrade(Integer gradeId);

}
