package com.aston.course.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;
}