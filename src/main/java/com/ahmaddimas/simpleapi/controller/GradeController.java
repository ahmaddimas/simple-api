package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/grade")
public class GradeController {

    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("list")
    public List<Grade> getList()
    {
        return this.gradeService.getList();
    }

    @GetMapping("show/{gradeId}")
    public Optional<Grade> show(@PathVariable(name = "gradeId") Integer gradeId)
    {
        return this.gradeService.getGradeById(gradeId);
    }
}
