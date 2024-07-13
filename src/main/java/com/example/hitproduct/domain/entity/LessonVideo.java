package com.example.hitproduct.domain.entity;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 11:13 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "lesson_video")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    String description;
    String url;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    Lesson lesson;
}
