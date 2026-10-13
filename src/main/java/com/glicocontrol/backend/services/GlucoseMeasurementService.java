package com.glicocontrol.backend.services;

import com.glicocontrol.backend.models.GlucoseMeasurement;
import com.glicocontrol.backend.repositories.GlucoseMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GlucoseMeasurementService {

    @Autowired
    private GlucoseMeasurementRepository repository;

    // Salvar nova medição (a categoria já é calculada automaticamente na entidade via @PrePersist)
    public GlucoseMeasurement salvarMedicao(GlucoseMeasurement measurement) {
        if (measurement.getMeasurementDateTime() == null) {
            measurement.setMeasurementDateTime(LocalDateTime.now());
        }
        return repository.save(measurement);
    }

    // Buscar histórico de um usuário específico ordenado do mais recente para o mais antigo
    public List<GlucoseMeasurement> obterHistoricoPorUsuario(Long userId) {
        return repository.buscarPorUsuarioOrdenado(userId);
    }

    // Buscar histórico de um usuário filtrado por período (intervalo de datas)
    public List<GlucoseMeasurement> obterHistoricoPorPeriodo(Long userId, LocalDateTime inicio, LocalDateTime fim) {
        return repository.buscarPorPeriodoOrdenado(userId, inicio, fim);
    }

    // Calcular média glicêmica do usuário com base no histórico
    public double calcularMediaGlicemica(Long userId) {
        List<GlucoseMeasurement> medicoes = obterHistoricoPorUsuario(userId);
        if (medicoes.isEmpty()) {
            return 0.0;
        }

        double soma = medicoes.stream().mapToInt(GlucoseMeasurement::getGlucoseValue).sum();
        return (double) soma / medicoes.size();
    }
}