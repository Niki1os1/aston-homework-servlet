package com.aston.course.controller;

import com.aston.course.dto.VideoDto;
import com.aston.course.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VideoDto> getVideoDetails(@PathVariable String videoId) {
        try {
            Long id = Long.valueOf(videoId);
            var video = videoService.getVideoById(id);
            return ResponseEntity.ok(video);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/in-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VideoDto>> getAllVideos(@PathVariable String courseId) {
        try {
            Long id = Long.valueOf(courseId);
            var videos = videoService.getAllVideos(id);
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> uploadVideo(@PathVariable String courseId, @RequestParam("title") String title,
                                            @RequestParam("description") String description) {
        try {
            Long id = Long.valueOf(courseId);
            var uploadVideoId = videoService.uploadVideo(id, title, description);
            return ResponseEntity.ok(uploadVideoId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteVideo(@PathVariable String videoId) {
        try {
            Long id = Long.valueOf(videoId);
            Long deletedVideoId = videoService.deleteVideoById(id);
            return ResponseEntity.ok(deletedVideoId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
