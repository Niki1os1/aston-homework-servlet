package com.aston.course.service;

import com.aston.course.dto.CourseDto;
import com.aston.course.model.Course;
import com.aston.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find course by id - " + courseId));
        return mapCourseToDto(course);
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(this::mapCourseToDto).collect(Collectors.toList());
    }

    @Transactional
    public Long createCourse(Long userId, String category, String title) {
        var savedCourse = new Course();
        savedCourse.setUserId(userId);
        savedCourse.setCategory(category);
        savedCourse.setTitle(title);

        return courseRepository.save(savedCourse).getId();
    }

    @Transactional
    public Long deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
        return courseId;
    }

    private CourseDto mapCourseToDto(Course course) {
        return new CourseDto(course.getId(), course.getUserId(), course.getCategory(), course.getTitle());
    }
}