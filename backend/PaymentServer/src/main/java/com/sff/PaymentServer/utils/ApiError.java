package com.sff.PaymentServer.utils;

import com.sff.PaymentServer.error.type.ServiceError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private final String message;
    private final int status;

    public ApiError(ServiceError error){
        this.message = error.getMessage();
        this.status = error.getCode();
    }

}