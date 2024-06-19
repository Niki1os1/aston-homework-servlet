package com.aston.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "video_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "date_upload")
    private Timestamp dateUpload;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "comment_text")
    private String commentText;
}
