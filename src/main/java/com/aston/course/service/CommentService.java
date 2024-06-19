package com.aston.course.service;

import com.aston.course.dto.CommentDto;
import com.aston.course.model.Comment;
import com.aston.course.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find comment by id - " + commentId));

        return mapCommentToDto(comment);
    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(this::mapCommentToDto).collect(Collectors.toList());
    }

    @Transactional
    public Long createComment(Long videoId, Timestamp dateUpload, Long authorId, String commentText) {
        Comment comment = new Comment();
        comment.setVideoId(videoId);
        comment.setDateUpload(dateUpload);
        comment.setAuthorId(authorId);
        comment.setCommentText(commentText);

        return commentRepository.save(comment).getId();
    }

    @Transactional
    public Long deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
        return commentId;
    }

    private CommentDto mapCommentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getVideoId(), comment.getDateUpload(),
                comment.getAuthorId(), comment.getCommentText());
    }
}