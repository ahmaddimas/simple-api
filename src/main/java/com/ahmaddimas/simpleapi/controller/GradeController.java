package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.service.GradeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/grade")
public class GradeController {

    protected GradeServiceInterface gradeService;

    @Autowired
    public GradeController(GradeServiceInterface gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<Grade> getList(@RequestParam(defaultValue = "") String name) {
        return this.gradeService.getList(name);
    }

    @GetMapping("{gradeId}")
    public Grade show(@PathVariable(name = "gradeId") Integer gradeId) throws Exception {
        return this.gradeService.getGradeById(gradeId);
    }

    @PostMapping
    public Grade add(@RequestBody Grade grade) {
        this.validateRequest(grade);
        return this.gradeService.addGrade(grade);
    }

    @PutMapping("{gradeId}")
    public Grade update(@PathVariable(name = "gradeId") Integer gradeId, @RequestBody Grade grade) {
        this.validateRequest(grade);
        return this.gradeService.editGrade(gradeId, grade);
    }

    @DeleteMapping("{gradeId}")
    public String delete(@PathVariable(name = "gradeId") Integer gradeId) {
        this.gradeService.deleteGrade(gradeId);
        return "Success";
    }

    private void validateRequest(Grade grade) {
        if (grade.getName() == null || grade.getName().isEmpty()) {
            throw new RuntimeException("name is required");
        }
    }
}
