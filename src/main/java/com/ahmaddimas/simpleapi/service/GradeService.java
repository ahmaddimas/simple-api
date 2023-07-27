package com.ahmaddimas.simpleapi.service;

import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService implements GradeServiceInterface {

    protected GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Grade> getList(String name) {
        List<Grade> gradeList;
        if (!name.isEmpty()) {
            gradeList = this.gradeRepository.findAllByName(name);
        } else {
            gradeList = this.gradeRepository.findAll();
        }
        return gradeList;
    }

    @Override
    public Grade getGradeById(Integer gradeId) throws Exception {
        return this.gradeRepository.findById(gradeId).orElseThrow(() -> new Exception("Grade with id "+ gradeId +" does not exists"));
    }

    @Override
    public Grade addGrade(Grade grade) {
        grade.setId(null);
        return this.gradeRepository.save(grade);
    }

    @Override
    public Grade editGrade(Integer gradeId, Grade grade) {
        Grade savedGrade = this.gradeRepository.findById(gradeId).orElse(null);
        if (savedGrade == null) {
            throw new RuntimeException("Grade with id "+ gradeId +" does not exists");
        }
        savedGrade.setName(grade.getName());
        if (grade.getBonusPercentage() != null) {
            savedGrade.setBonusPercentage(grade.getBonusPercentage());
        }
        return this.gradeRepository.save(savedGrade);
    }

    @Override
    public void deleteGrade(Integer gradeId) {
        this.gradeRepository.deleteById(gradeId);
    }
}
