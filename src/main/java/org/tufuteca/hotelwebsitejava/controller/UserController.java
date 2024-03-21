package org.tufuteca.hotelwebsitejava.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.repository.RoleRepository;
import org.tufuteca.hotelwebsitejava.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/delete-profile")
    public String deleteProfile(@RequestParam("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin-panel";
    }

    @GetMapping("/edit-profile/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        var user = userService.getUserById(id);
        model.addAttribute("user", user);
        var role = roleRepository.findAll();
        model.addAttribute("role",role);
        return "edit-profile";
    }

    @PostMapping("/edit-profile-confirm")
    public String editProfileConfirm(@ModelAttribute User editedUser, Model model) {
        // Получаем идентификатор пользователя из формы редактирования
        Long userId = editedUser.getId();

        // Получаем текущего пользователя из базы данных по его идентификатору
        User currentUser = userService.getUserById(userId);

        // Обновляем данные пользователя
        currentUser.setName(editedUser.getName());
        currentUser.setSurname(editedUser.getSurname());
        currentUser.setPatronymic(editedUser.getPatronymic());
        currentUser.setUsername(editedUser.getUsername());
        currentUser.setEmail(editedUser.getEmail());
        currentUser.setPhonenumber(editedUser.getPhonenumber());
        currentUser.setPassword(editedUser.getPassword());

        // Проверяем, была ли изменена роль пользователя
        if (editedUser.getRole() != null) {
            currentUser.setRole(editedUser.getRole());
        }

        // Сохраняем обновленные данные пользователя в базе данных
        userService.addUser(currentUser);

        // Возвращаем пользователя на страницу редактирования с сообщением об успешном изменении профиля
        model.addAttribute("successMessage", "Профиль успешно обновлен!");
        return "redirect:/admin-panel";
    }


}
