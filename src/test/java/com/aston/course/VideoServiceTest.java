package com.aston.course;

import com.aston.course.dto.VideoDto;
import com.aston.course.model.Video;
import com.aston.course.repository.VideoRepository;
import com.aston.course.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVideos() {
        List<Video> videos = List.of(new Video(1L, "title", "description", 1L, List.of()));
        when(videoRepository.findVideoByCourseId(1L)).thenReturn(videos);

        List<VideoDto> videoDtos = videoService.getAllVideos(1L);

        assertNotNull(videoDtos);
        assertEquals(1, videoDtos.size());
        assertEquals("title", videoDtos.get(0).getTitle());
    }

    @Test
    void testGetVideoById() {
        Video video = new Video(1L, "title", "description", 1L, List.of());
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        VideoDto videoDto = videoService.getVideoById(1L);

        assertNotNull(videoDto);
        assertEquals("title", videoDto.getTitle());
    }

    @Test
    void testUploadVideo() {
        Video video = new Video(1L, "title", "description", 1L, List.of());
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        Long videoId = videoService.uploadVideo(1L, "title", "description");

        assertNotNull(videoId);
        assertEquals(1L, videoId);
    }

    @Test
    void testDeleteVideoById() {
        doNothing().when(videoRepository).deleteById(1L);

        Long deletedVideoId = videoService.deleteVideoById(1L);

        assertNotNull(deletedVideoId);
        assertEquals(1L, deletedVideoId);
    }
}
