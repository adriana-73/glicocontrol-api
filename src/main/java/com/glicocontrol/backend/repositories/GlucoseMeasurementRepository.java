package com.glicocontrol.backend.repositories;

import com.glicocontrol.backend.models.GlucoseMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {

    // Método customizado para buscar todas as medições de um usuário ordenadas
    @Query("SELECT g FROM GlucoseMeasurement g WHERE g.user.id = :userId ORDER BY g.measurementDateTime DESC")
    List<GlucoseMeasurement> buscarPorUsuarioOrdenado(@Param("userId") Long userId);

    // Buscar histórico de um usuário em um intervalo de datas
    @Query("SELECT g FROM GlucoseMeasurement g WHERE g.user.id = :userId AND g.measurementDateTime BETWEEN :startDate AND :endDate ORDER BY g.measurementDateTime DESC")
    List<GlucoseMeasurement> buscarPorPeriodoOrdenado(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}