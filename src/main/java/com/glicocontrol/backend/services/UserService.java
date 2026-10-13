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
}




