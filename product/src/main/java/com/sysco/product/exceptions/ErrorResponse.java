package com.sysco.product.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final Integer error;
    private final String message;
}
