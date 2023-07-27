package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Grade;
import com.ahmaddimas.simpleapi.service.GradeServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GradeController.class)
class GradeControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GradeServiceInterface gradeService;

    private String getApiUrl() {
        return "/api/v1/grade";
    }

    @Test
    void testAddNewGradeShouldSuccess() throws Exception {
        Grade grade = new Grade("Test");
        grade.setId(1);
        grade.setBonusPercentage(10);

        Mockito.when(gradeService.addGrade(any())).thenReturn(grade);

        String request = objectMapper.writeValueAsString(grade);
        mockMvc.perform(post(getApiUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testAddNewGradeShouldFailed() throws Exception {
        Grade grade = new Grade();
        String request = objectMapper.writeValueAsString(grade);

        assertThatThrownBy(
                () -> mockMvc.perform(post(getApiUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                )
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("name is required");
    }

    @Test
    void testGetListShouldSuccess() throws Exception {
        mockMvc.perform(get(getApiUrl()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testShowShouldSuccess() throws Exception {
        Grade grade = new Grade("Test");
        grade.setId(1);
        grade.setBonusPercentage(10);

        Mockito.when(gradeService.getGradeById(1)).thenReturn(grade);

        mockMvc.perform(get(getApiUrl() + "/1"))
                .andDo(print())
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(grade))))
                .andExpect(status().isOk());
    }

    @Test
    void testShowShouldFailed() throws Exception {
        Mockito.when(gradeService.getGradeById(1)).thenThrow(Exception.class);

        assertThatThrownBy(() -> mockMvc.perform(get(getApiUrl() + "/1")))
                .hasCauseInstanceOf(Exception.class);
    }

    @Test
    void testUpdateGradeShouldSuccess() throws Exception {
        Grade grade = new Grade("Test");
        grade.setId(1);
        grade.setBonusPercentage(10);

        Mockito.when(gradeService.editGrade(anyInt(), any())).thenReturn(grade);

        String request = objectMapper.writeValueAsString(grade);
        mockMvc.perform(put(getApiUrl() + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGradeShouldFailed() throws Exception {
        Grade grade = new Grade();
        String request = objectMapper.writeValueAsString(grade);

        assertThatThrownBy(
                () -> mockMvc.perform(put(getApiUrl() + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
        )
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("name is required");
    }

    @Test
    void testDeleteGrade() throws Exception {
        mockMvc.perform(delete(getApiUrl() + "/1"))
                .andDo(print())
                .andExpect(content().string(equalTo("Success")))
                .andExpect(status().isOk());
    }
}