package com.glicocontrol.backend.controllers;

import com.glicocontrol.backend.dtos.GlucoseMeasurementRequestDTO;
import com.glicocontrol.backend.models.GlucoseMeasurement;
import com.glicocontrol.backend.models.User;
import com.glicocontrol.backend.repositories.UserRepository;
import com.glicocontrol.backend.services.GlucoseMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/measurements")
public class GlucoseMeasurementController {

    @Autowired
    private GlucoseMeasurementService measurementService;

    @Autowired
    private UserRepository userRepository;

     //1. Endpoint para cadastrar uma nova medição de glicose
    @PostMapping
    public ResponseEntity<Object> createMeasurement(@RequestBody GlucoseMeasurementRequestDTO requestDTO) {

        // 1. Busca o usuário pelo ID que veio dentro do DTO
        Optional<User> userOpt = userRepository.findById(requestDTO.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado com o ID informado.");
        }

        // 2. Cria a entidade GlucoseMeasurement e preenche com os dados do DTO
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setUser(userOpt.get());

        // Ajuste para o nome do método setter do valor na sua entity (ex: setValue ou setValor)
        measurement.setGlucoseValue(requestDTO.getValue().intValue());


        // 3. Define a data/hora (se vier nula, usa o momento atual)
        if (requestDTO.getMeasurementDateTime() == null) {
            measurement.setMeasurementDateTime(LocalDateTime.now());
        } else {
            measurement.setMeasurementDateTime(requestDTO.getMeasurementDateTime());
        }


        // 4. Salva usando o service
        GlucoseMeasurement savedMeasurement = measurementService.salvarMedicao(measurement);

        // 5. Retorna sucesso com status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeasurement);
    }

    // 2. Endpoint para listar todas as medições de um usuário específico (ordenadas)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GlucoseMeasurement>> getMeasurementsByUser(@PathVariable("userId") Long userId) {
        List<GlucoseMeasurement> measurements = measurementService.obterHistoricoPorUsuario(userId);
        return ResponseEntity.ok(measurements);
    }

    // 3. Endpoint para buscar a média glicêmica do usuário
    @GetMapping("/user/{userId}/media")
    public ResponseEntity<Double> getAverageGlucose(@PathVariable("userId") Long userId) {
        double media = measurementService.calcularMediaGlicemica(userId);
        return ResponseEntity.ok(media);
    }
     //4. Endpoint para atualizar uma medição existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMeasurement(@PathVariable Long id, @RequestBody GlucoseMeasurementRequestDTO requestDTO) {

        Optional<GlucoseMeasurement> measurementOpt = measurementService.buscarPorId(id);
        if (measurementOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medição não encontrada com o ID informado.");
        }

        GlucoseMeasurement measurement = measurementOpt.get();

        if (requestDTO.getValue() != null) {
            measurement.setGlucoseValue(requestDTO.getValue().intValue());
        }

        if (requestDTO.getMeasurementDateTime() != null) {
            measurement.setMeasurementDateTime(requestDTO.getMeasurementDateTime());
        }

        GlucoseMeasurement updatedMeasurement = measurementService.salvarMedicao(measurement);

        return ResponseEntity.ok(updatedMeasurement);
    }

    // 5. Endpoint para excluir uma medição (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMeasurement(@PathVariable Long id) {

        Optional<GlucoseMeasurement> measurementOpt = measurementService.buscarPorId(id);
        if (measurementOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medição não encontrada com o ID informado.");
        }

        measurementService.deletarMedicao(id);

        return ResponseEntity.noContent().build();

    }
}