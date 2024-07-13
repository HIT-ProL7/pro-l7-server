package com.example.hitproduct.repository;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 11:53 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.LessonVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonVideoRepository extends JpaRepository<LessonVideo, Integer> {
}
