package com.glicocontrol.backend.dtos;

import java.time.LocalDateTime;

public class GlucoseMeasurementRequestDTO {

    private Long userId;
    private Double value; // ou Integer, dependendo de como definiu no seu modelo
    private Long categoryId; // ou o nome da categoria / enum
    private LocalDateTime measurementDateTime;

    // Getters e Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }
}
