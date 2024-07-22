package com.example.hitproduct.repository;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:32 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.Exercise;
import com.example.hitproduct.domain.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByExercise(Exercise exercise);
}
