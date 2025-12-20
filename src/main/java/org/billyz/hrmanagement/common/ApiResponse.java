package org.billyz.hrmanagement.common;

import lombok.Data;

@Data
public class ApiResponse<R> {
    private boolean success;
    private String message;
    private R data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setMessage("Success");
        resp.setData(data);
        return resp;
    }

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(false);
        resp.setMessage(message);
        resp.setData(null);
        return resp;
    }
}
