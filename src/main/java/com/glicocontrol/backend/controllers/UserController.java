package com.glicocontrol.backend.controllers;
import com.glicocontrol.backend.services.UserService;
import org.springframework.validation.BindingResult;

import com.glicocontrol.backend.models.User;
import com.glicocontrol.backend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/users")
    @CrossOrigin(origins = "*") // Permite que o front-end acesse essa API
    public class UserController {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private UserService userService;


        // Rota POST para cadastrar um novo usuário
        // Adicione este import

        @PostMapping
        public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
            if (result.hasErrors()) {
                // Isso vai retornar exatamente qual campo está faltando ou inválido
                return ResponseEntity.badRequest().body("Erro de validação: " + result.getAllErrors());
            }


            User savedUser = userRepository.save(user);
            return ResponseEntity.status(201).body(savedUser);
        }

        // Rota GET para listar todos os usuários cadastrados
        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> atualizar(@PathVariable Long id, @RequestBody User usuarioAtualizado) {
            User usuario = userService.atualizar(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
            userService.deletar(id);
            return ResponseEntity.noContent().build();
        }
    }

