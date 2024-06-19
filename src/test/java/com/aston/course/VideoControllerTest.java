package com.aston.course;

import com.aston.course.controller.VideoController;
import com.aston.course.dto.VideoDto;
import com.aston.course.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class VideoControllerTest {

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetVideoDetails() {
        VideoDto videoDto = new VideoDto(1L, "title", "description", 1L, List.of());
        when(videoService.getVideoById(1L)).thenReturn(videoDto);

        ResponseEntity<VideoDto> response = videoController.getVideoDetails("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(videoDto, response.getBody());
    }

    @Test
    void testGetVideoDetails_NotFound() {
        when(videoService.getVideoById(1L)).thenThrow(new NoSuchElementException());

        ResponseEntity<VideoDto> response = videoController.getVideoDetails("1");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllVideos() {
        List<VideoDto> videos = List.of(new VideoDto(1L, "title", "description", 1L, List.of()));
        when(videoService.getAllVideos(1L)).thenReturn(videos);

        ResponseEntity<List<VideoDto>> response = videoController.getAllVideos("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(videos, response.getBody());
    }

    @Test
    void testUploadVideo() {
        when(videoService.uploadVideo(1L, "title", "description")).thenReturn(1L);

        ResponseEntity<Long> response = videoController.uploadVideo("1", "title", "description");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testDeleteVideo() {
        when(videoService.deleteVideoById(1L)).thenReturn(1L);

        ResponseEntity<Long> response = videoController.deleteVideo("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }
}
