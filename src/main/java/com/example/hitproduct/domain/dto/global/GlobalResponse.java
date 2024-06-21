package com.example.hitproduct.domain.dto.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GlobalResponse<Meta, Data>(
        Meta meta,
        Data data
) {
}
