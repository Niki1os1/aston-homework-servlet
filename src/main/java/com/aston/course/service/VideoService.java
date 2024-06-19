package com.aston.course.service;

import com.aston.course.dto.VideoDto;
import com.aston.course.model.Video;
import com.aston.course.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoDto> getAllVideos(Long courseId) {
        return videoRepository.findVideoByCourseId(courseId).stream().map(this::mapToVideoDto).collect(Collectors.toList());
    }

    public VideoDto getVideoById(Long videoId) {
        Video resultVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + videoId));
        return mapToVideoDto(resultVideo);
    }

    @Transactional
    public Long uploadVideo(Long courseId, String title, String description) {

        var video = new Video();
        video.setCourseId(courseId);
        video.setTitle(title);
        video.setDescription(description);

        return videoRepository.save(video).getId();
    }

    @Transactional
    public Long deleteVideoById(Long videoId) {
        videoRepository.deleteById(videoId);
        return videoId;
    }

    private VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());

        return videoDto;
    }

}