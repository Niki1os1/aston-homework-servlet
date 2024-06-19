package com.aston.course;

import com.aston.course.dto.CourseDto;
import com.aston.course.model.Course;
import com.aston.course.repository.CourseRepository;
import com.aston.course.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCourseById() {
        Course course = new Course(1L, 1L, "Category", "Title");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseDto courseDto = courseService.getCourseById(1L);

        assertNotNull(courseDto);
        assertEquals(1L, courseDto.getId());
        assertEquals("Title", courseDto.getTitle());
    }

    @Test
    void testGetAllCourses() {
        Course course = new Course(1L, 1L, "Category", "Title");
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(course));

        List<CourseDto> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Title", courses.get(0).getTitle());
    }

    @Test
    void testCreateCourse() {
        Course course = new Course(null, 1L, "Category", "Title");
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(1L, 1L, "Category", "Title"));

        Long courseId = courseService.createCourse(1L, "Category", "Title");

        assertNotNull(courseId);
        assertEquals(1L, courseId);
    }

    @Test
    void testDeleteCourseById() {
        doNothing().when(courseRepository).deleteById(1L);

        Long deletedCourseId = courseService.deleteCourseById(1L);

        assertNotNull(deletedCourseId);
        assertEquals(1L, deletedCourseId);
    }
}
