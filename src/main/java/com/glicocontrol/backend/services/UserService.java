package com.glicocontrol.backend.services;

import com.glicocontrol.backend.models.User;
import com.glicocontrol.backend.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Formata o nome para a primeira letra maiúscula e as restantes minúsculas
        if (user.getName() != null && !user.getName().isEmpty()) {
            user.setName(formatarNome(user.getName()));
        }

        return userRepository.save(user);
    }

     //Método auxiliar para formatar o nome//
     private String formatarNome(String nome) {
        String[] palavras = nome.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                sb.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Método para atualizar
    public User atualizar(Long id, User usuarioAtualizado) {
        return userRepository.findById(id).map(user -> {
            user.setName(usuarioAtualizado.getName());
            user.setEmail(usuarioAtualizado.getEmail());
            // Atualize outros campos necessários aqui...
            user.setMedications(usuarioAtualizado.getMedications());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    // Método para deletar
    public void deletar(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }
}




