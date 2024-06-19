package com.aston.course;

import com.aston.course.controller.CommentController;
import com.aston.course.dto.CommentDto;
import com.aston.course.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void testGetCommentById() throws Exception {
        CommentDto commentDto = new CommentDto(1L, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");

        when(commentService.getCommentById(1L)).thenReturn(commentDto);

        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.commentText").value("Test comment"));
    }

    @Test
    void testGetAllComments() throws Exception {
        CommentDto commentDto = new CommentDto(1L, 1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment");

        when(commentService.getAllComments()).thenReturn(Collections.singletonList(commentDto));

        mockMvc.perform(get("/api/comment/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].commentText").value("Test comment"));
    }

    @Test
    void testUploadAnswer() throws Exception {
        when(commentService.createComment(1L, new Timestamp(System.currentTimeMillis()), 1L, "Test comment")).thenReturn(1L);

        mockMvc.perform(post("/api/comment/1")
                        .param("date_upload", new Timestamp(System.currentTimeMillis()).toString())
                        .param("author_id", "1")
                        .param("comment_text", "Test comment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCommentById() throws Exception {
        when(commentService.deleteCommentById(1L)).thenReturn(1L);

        mockMvc.perform(delete("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
