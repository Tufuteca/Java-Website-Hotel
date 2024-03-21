package org.tufuteca.hotelwebsitejava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tufuteca.hotelwebsitejava.model.Role;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.repository.RoleRepository;
import org.tufuteca.hotelwebsitejava.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class RegistrationController {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(RoleRepository roleRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam("registerUsername") String username,
                               @RequestParam("registerPassword") String password,
                               @RequestParam("registerEmail") String email,
                               @RequestParam("registerPhone") String phoneNumber,
                               @RequestParam("registerSurname") String surname,
                               @RequestParam("registerName") String name,
                               @RequestParam("registerPatronymic") String patronymic) {
        // Логика регистрации пользователя
        Role defaultRole = roleRepository.findByTitle("ROLE_USER");
        var newUser = new User();
        newUser.setRole(defaultRole);
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setPatronymic(patronymic);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setPhonenumber(phoneNumber);

        try {
            userService.addUser(newUser);
            return "redirect:/login";
        } catch (DataIntegrityViolationException e) {
            // Обработка ошибки дублирования записи
            String errorMessage = determineErrorMessage(e);
            if (errorMessage.contains("email")) {
                return "redirect:/registration?errorMail=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            } else if (errorMessage.contains("username")) {
                return "redirect:/registration?errorUsername=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            } else if (errorMessage.contains("phone")) {
                return "redirect:/registration?errorPhone=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            } else {
                return "redirect:/registration?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            }
        }

    }

    private String determineErrorMessage(DataIntegrityViolationException e) {
        String errorMessage = "Произошла ошибка при регистрации.";

        if (e.getMessage().contains("UK_6dotkott2kjsp8vw4d0m25fb7")) {
            errorMessage = "Пользователь с таким email уже зарегистрирован.";
        } else if (e.getMessage().contains("UK_r43af9ap4edm43mmtq01oddj6")) {
            errorMessage = "Пользователь с таким username уже зарегистрирован.";
        } else if (e.getMessage().contains("UK_70jmct5ej765l57mlcrdhxn1c")) {
            errorMessage = "Пользователь с таким phone уже зарегистрирован.";
        }

        return errorMessage;
    }


}
