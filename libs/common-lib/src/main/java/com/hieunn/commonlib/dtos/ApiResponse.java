package com.hieunn.commonlib.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message", "data", "error", "timestamp"})
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private ApiError error;
    private LocalDateTime timestamp;

    // ---------- SUCCESS ----------
    public static <T> ApiResponse<T> success(HttpStatus status, T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();

        response.setStatus(status.value());
        response.setMessage(message != null ? message : status.getReasonPhrase());
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());

        return response;
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(HttpStatus.OK, data, "Success");
    }

    public static <T> ApiResponse<T> success(String message) {
        return success(HttpStatus.OK, null, message);
    }

    public static <T> ApiResponse<T> success() {
        return success(HttpStatus.OK, null, "Success");
    }

    // ---------- ERROR ----------
    public static <T> ApiResponse<T> error(HttpStatus status, String message, Object details) {
        ApiError apiError = new ApiError();
        apiError.setCode(status.value());
        apiError.setReason(status.getReasonPhrase());
        apiError.setDetails(details);

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(status.value());
        response.setMessage(message != null ? message : status.getReasonPhrase());
        response.setError(apiError);
        response.setTimestamp(LocalDateTime.now());

        return response;
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return error(status, message, null);
    }
}