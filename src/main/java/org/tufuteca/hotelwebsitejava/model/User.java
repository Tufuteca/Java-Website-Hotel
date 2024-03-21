package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;
    @Column(nullable = false, unique = false)
    private String surname;

    private String patronymic;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phonenumber;

    @Column(nullable = false)
    private String password;

    @ManyToOne // Одна роль к многим пользователям
    @JoinColumn(name = "role_id") // Наименование столбца в таблице users для хранения внешнего ключа
    private Role role;
}