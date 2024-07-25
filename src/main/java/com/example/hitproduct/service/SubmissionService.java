package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:34 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.SubmissionRequest;
import com.example.hitproduct.domain.dto.request.UpdateSubmissionRequest;
import com.example.hitproduct.domain.dto.response.SubmissionResponse;

import java.util.List;

public interface SubmissionService {
    GlobalResponse<Meta, SubmissionResponse> createSubmission(SubmissionRequest request, String studentCode);

    GlobalResponse<Meta, List<SubmissionResponse>> getSubmissions(Integer id, String username);

    GlobalResponse<Meta, Object> updateSubmission(Integer id, UpdateSubmissionRequest request, String username);

    GlobalResponse<Meta, String> deleteSubmission(Integer id, String username);
}
