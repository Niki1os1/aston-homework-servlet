package com.aston.course;

import com.aston.course.controller.CourseController;
import com.aston.course.dto.CourseDto;
import com.aston.course.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testGetCourseById() throws Exception {
        CourseDto courseDto = new CourseDto(1L, 1L, "Category", "Title");

        when(courseService.getCourseById(1L)).thenReturn(courseDto);

        mockMvc.perform(get("/api/course/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCourses() throws Exception {
        CourseDto courseDto = new CourseDto(1L, 1L, "Category", "Title");

        when(courseService.getAllCourses()).thenReturn(Collections.singletonList(courseDto));

        mockMvc.perform(get("/api/course/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateCourse() throws Exception {
        when(courseService.createCourse(1L, "Category", "Title")).thenReturn(1L);

        mockMvc.perform(post("/api/course")
                        .param("userId", "1")
                        .param("category", "Category")
                        .param("title", "Title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCourseById() throws Exception {
        when(courseService.deleteCourseById(1L)).thenReturn(1L);

        mockMvc.perform(delete("/api/course/1"))
                .andExpect(status().isOk());
    }
}
