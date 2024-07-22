package com.example.hitproduct.domain.mapper;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:44 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.request.SubmissionRequest;
import com.example.hitproduct.domain.dto.response.SubmissionResponse;
import com.example.hitproduct.domain.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SubmissionMapper {
    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);

    Submission toSubmission(SubmissionRequest request);
    SubmissionResponse toSubmissionResponse(Submission submission);
}
