package com.ignit.internship.dto;

import java.io.Serializable;

public class DefaultResponse<T> implements Serializable {

    private boolean success;

    private T payload;

    private ErrorResponse error;

    private DefaultResponse(boolean success, T payload, Exception error) {
        this.success = success;
        this.payload = payload;

        if (error != null) {
            this.error = new ErrorResponse(
                error.getClass().getName(),
                error.getMessage()
            );
        }
    }

    public static <G> DefaultResponse<G> success(G payload) {
        return new DefaultResponse<G>(true, payload, null);
    }

    public static <G> DefaultResponse<G> failed(G payload, Exception error) {  
        return new DefaultResponse<G>(false, payload, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getPayload() {
        return payload;
    }

    public ErrorResponse getError() {
        return error;
    }
}
