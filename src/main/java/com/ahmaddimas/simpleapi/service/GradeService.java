package com.ahmaddimas.simpleapi.service;

import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getList()
    {
        return this.gradeRepository.findAll();
    }

    public Optional<Grade> getGradeById(Integer gradeId)
    {
        return this.gradeRepository.findById(gradeId);
    }
}
