package org.tufuteca.hotelwebsitejava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tufuteca.hotelwebsitejava.model.Role;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.repository.RoleRepository;
import org.tufuteca.hotelwebsitejava.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("patronymic") String patronymic,
                               @RequestParam("email") String email,
                               @RequestParam("phone") String phone) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setEmail(email);
        user.setPhonenumber(phone);
        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/login";
    }

}
