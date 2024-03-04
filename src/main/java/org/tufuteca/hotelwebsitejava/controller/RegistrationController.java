package org.tufuteca.hotelwebsitejava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tufuteca.hotelwebsitejava.model.Role;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.repository.RoleRepository;
import org.tufuteca.hotelwebsitejava.repository.UserRepository;
import org.tufuteca.hotelwebsitejava.service.UserService;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/registration")
    public String getregistrationPage(Model model){
        return "/registration";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam("registerUsername") String username,
                               @RequestParam("registerPassword") String password,
                               @RequestParam("registerEmail") String email,
                               @RequestParam("registerPhone") String phoneNumber,
                               @RequestParam("registerSurname") String surname,
                               @RequestParam("registerName") String name,
                               @RequestParam("registerPatronymic") String patronymic,
                               Model model) {
        // Логика регистрации пользователя
        Role defaultRole = roleRepository.findByUserRole("USER"); // Предположим, что у вас есть роль "USER" по умолчанию
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
            model.addAttribute("error", errorMessage);
            return "/login";
        }
    }

    private String determineErrorMessage(DataIntegrityViolationException e) {
        String errorMessage = "Произошла ошибка при регистрации.";

        if (e.getMessage().contains("UK_6dotkott2kjsp8vw4d0m25fb7")) {
            errorMessage = "Пользователь с таким email уже зарегистрирован.";
        } else if (e.getMessage().contains("UK_2aels1eehgg2k96e0mcw12c2m")) {
            errorMessage = "Пользователь с таким логином уже зарегистрирован.";
        }

        return errorMessage;
    }

}
