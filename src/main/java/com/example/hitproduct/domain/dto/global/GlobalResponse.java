package com.example.hitproduct.domain.dto.global;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record GlobalResponse<Meta, Data>(
        Meta meta,
        Data data
) {
}
