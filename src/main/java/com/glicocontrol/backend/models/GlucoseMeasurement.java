package com.glicocontrol.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_glucose_measurements")
public class GlucoseMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int glucoseValue; // Ex: 110 (mg/dL)

    @Column(nullable = false)
    private LocalDateTime measurementDateTime;

    private String mealContext; // Ex: JEJUM, POS_ALMOCO, ANTES_DORMIR

    // Novo campo para armazenar a categoria da glicemia
    @Enumerated(EnumType.STRING)
    private GlucoseCategory category;

    // Relacionamento: Muitas medições pertencem a um único usuário
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtor vazio (obrigatório para o JPA)
    public GlucoseMeasurement() {
    }

    // Construtor com parâmetros úteis
    public GlucoseMeasurement(int glucoseValue, LocalDateTime measurementDateTime, String mealContext, User user) {
        this.glucoseValue = glucoseValue;
        this.measurementDateTime = measurementDateTime;
        this.mealContext = mealContext;
        this.user = user;
        this.calculateCategory(); // Calcula ao instanciar
    }

    // A nossa "calculadora": roda automaticamente antes de salvar ou atualizar no banco
    @PrePersist
    @PreUpdate
    public void calculateCategory() {
        if (this.glucoseValue < 70) {
            this.category = GlucoseCategory.HIPOGLICEMIA;
        } else if (this.glucoseValue > 180) {
            this.category = GlucoseCategory.HIPERGLICEMIA;
        } else {
            this.category = GlucoseCategory.NORMAL;
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGlucoseValue() {
        return glucoseValue;
    }

    public void setGlucoseValue(int glucoseValue) {
        this.glucoseValue = glucoseValue;
        this.calculateCategory(); // Recalcula se o valor mudar
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    public String getMealContext() {
        return mealContext;
    }

    public void setMealContext(String mealContext) {
        this.mealContext = mealContext;
    }

    public GlucoseCategory getCategory() {
        return category;
    }

    public void setCategory(GlucoseCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}