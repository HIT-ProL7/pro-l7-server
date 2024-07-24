package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 22 / 07 / 2024 - 5:57 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.SubmissionRequest;
import com.example.hitproduct.domain.dto.response.SubmissionResponse;
import com.example.hitproduct.service.SubmissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class SubmissionController {
    SubmissionService submissionService;

    @PostMapping(Endpoint.V1.Submission.PREFIX)
    public ResponseEntity<GlobalResponse<Meta, SubmissionResponse>> createSubmit(@RequestBody SubmissionRequest request,
                                                                                 @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(submissionService.createSubmission(request, userDetails.getUsername()));
    }

    @GetMapping(Endpoint.V1.Submission.GET_SUBMIT)
    public ResponseEntity<GlobalResponse<Meta, List<SubmissionResponse>>> getSubmit(@PathVariable(name = "exerciseId") Integer id,
                                                                                    @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(submissionService.getSubmissions(id, userDetails.getUsername()));
    }

    @PutMapping(Endpoint.V1.Submission.UPDATE_SUBMIT)
    public ResponseEntity<GlobalResponse<Meta, Object>> updateSubmit(@PathVariable(name = "submitId") Integer id,
                                                                     @RequestParam(name = "content") String content,
                                                                     @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(submissionService.updateSubmission(id, content, userDetails.getUsername()));
    }

    @DeleteMapping(Endpoint.V1.Submission.DELETE_SUBMIT)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteSubmit(@PathVariable(name = "submitId") Integer id,
                                                                     @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(submissionService.deleteSubmission(id, userDetails.getUsername()));
    }
}
