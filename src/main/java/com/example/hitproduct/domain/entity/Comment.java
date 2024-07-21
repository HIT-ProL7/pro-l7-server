package com.example.hitproduct.domain.entity;
/*
 * @author HongAnh
 * @created 21 / 07 / 2024 - 8:53 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String content;

    @ManyToOne
    User user;

    @ManyToOne
    Exercise exercise;

    @ManyToOne
    Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    List<Comment> replies = new ArrayList<>();

    public void addComment(Comment comment){
        if(replies == null){
            replies = new ArrayList<>();
        }

        replies.add(comment);
        comment.setParentComment(this);
    }
}
