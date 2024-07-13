package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 11:54 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.CreateVideoRequest;
import com.example.hitproduct.domain.dto.response.VideoResponse;

public interface LessonVideoService {

    GlobalResponse<Meta, VideoResponse> createLessonVideo(CreateVideoRequest request, String studentCode);
}
