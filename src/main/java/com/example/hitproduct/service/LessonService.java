package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 4:57 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.CreateLessonRequest;
import com.example.hitproduct.domain.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {

    GlobalResponse<Meta, LessonResponse> createLesson(CreateLessonRequest request, String studentCode);

    GlobalResponse<Meta, List<LessonResponse>> getLessons(Integer id);
}
