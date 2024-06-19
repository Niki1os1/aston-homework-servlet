package com.aston.course.controller;

import com.aston.course.dto.CourseDto;
import com.aston.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDto> getCourseById(@PathVariable String courseId) {
        try {
            Long id = Long.valueOf(courseId);
            CourseDto course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        try {
            List<CourseDto> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createCourse(@RequestParam("userId") String userId,
                                             @RequestParam("category") String category,
                                             @RequestParam("title") String title) {
        try {
            Long createdCourseUserId = Long.valueOf(userId);

            var createdCourse = courseService.createCourse(createdCourseUserId, category, title);
            return ResponseEntity.ok(createdCourse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCourseById(@PathVariable String courseId) {
        try {
            Long id = Long.valueOf(courseId);
            var deletedCommentId = courseService.deleteCourseById(id);
            return ResponseEntity.ok(deletedCommentId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
