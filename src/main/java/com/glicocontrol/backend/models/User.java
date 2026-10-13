package com.glicocontrol.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "o nome é obrigatório")
        @Size(min = 3, max = 50, message = "o nome deve ter entre 3 e 50 caracteres")
        private String name;

        @Min(value = 0, message = "A idade deve ser válida")
        private int age;

        private double weight;
        private double height;

        @NotBlank(message = "o e-mail é obrigatório")
        @Email(message = "o e-mail deve ser válido e conter '@'")
        @Column(unique = true)
        private String email;


        @JsonProperty("medications")
        @Column(name = "medications_in_use")
        private String medications;

        private LocalDateTime createdAt;

        @PrePersist
        public void prePersist() {
                this.createdAt = LocalDateTime.now();
        }

        // Construtores
        public User() {}

        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }


        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public double getWeight() { return weight; }
        public void setWeight(double weight) { this.weight = weight; }

        public double getHeight() { return height; }
        public void setHeight(double height) { this.height = height; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getMedications() { return medications; }
        public void setMedications(String medications) { this.medications = medications; }

        public LocalDateTime getCreatedAt() { return createdAt; }
}