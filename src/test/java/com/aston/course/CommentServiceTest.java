package com.aston.course;

import com.aston.course.dto.CommentDto;
import com.aston.course.model.Comment;
import com.aston.course.repository.CommentRepository;
import com.aston.course.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentById() {
        Comment comment = new Comment(1L, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDto commentDto = commentService.getCommentById(1L);

        assertNotNull(commentDto);
        assertEquals(1L, commentDto.getId());
        assertEquals("Test comment", commentDto.getCommentText());
    }

    @Test
    void testGetAllComments() {
        Comment comment = new Comment(1L, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        List<CommentDto> comments = commentService.getAllComments();

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test comment", comments.get(0).getCommentText());
    }

    @Test
    void testCreateComment() {
        Comment comment = new Comment(null, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment(1L, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment"));

        Long commentId = commentService.createComment(1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");

        assertNotNull(commentId);
        assertEquals(1L, commentId);
    }

    @Test
    void testDeleteCommentById() {
        doNothing().when(commentRepository).deleteById(1L);

        Long deletedCommentId = commentService.deleteCommentById(1L);

        assertNotNull(deletedCommentId);
        assertEquals(1L, deletedCommentId);
    }
}
