package com.example.hitproduct.domain.dto.global;

import lombok.Builder;

@Builder
public record GlobalResponse<Meta, Data>(
        Meta meta,
        Data data
) {
}
