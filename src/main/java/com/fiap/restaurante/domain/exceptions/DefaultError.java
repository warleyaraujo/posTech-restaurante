package com.fiap.restaurante.domain.exceptions;

import java.time.Instant;

public class DefaultError {
    private Instant timestamp;
    private Integer status;
    private String error;

    public DefaultError() {}

    public DefaultError(Instant timestamp, Integer status, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
