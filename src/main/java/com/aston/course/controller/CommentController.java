package com.aston.course.controller;

import com.aston.course.dto.CommentDto;
import com.aston.course.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommentDto> getCommentById(@PathVariable String commentId) {
        try {
            Long id = Long.valueOf(commentId);
        CommentDto commentDto = commentService.getCommentById(id);
            return ResponseEntity.ok(commentDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CommentDto>> getAllComments() {
        try {
            List<CommentDto> comments = commentService.getAllComments();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> uploadAnswer(@PathVariable String videoId,
                                             @RequestParam("date_upload") String dateUpload,
                                             @RequestParam("author_id") String authorId,
                                             @RequestParam("comment_text") String commentText) {
        try {
            Long uploadAnswerVideoId = Long.valueOf(videoId);
            Timestamp date = Timestamp.valueOf(dateUpload);
            Long answerAuthorId = Long.valueOf(authorId);

            var createdCommentId = commentService.createComment(uploadAnswerVideoId, date, answerAuthorId, commentText);
            return ResponseEntity.ok(createdCommentId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCommentById(@PathVariable String commentId) {
        try {
            Long id = Long.valueOf(commentId);
            var deletedCommentId = commentService.deleteCommentById(id);
            return ResponseEntity.ok(deletedCommentId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
