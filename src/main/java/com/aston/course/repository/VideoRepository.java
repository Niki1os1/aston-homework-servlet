package com.aston.course.repository;

import com.aston.course.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findVideoByCourseId(Long courseId);
}
