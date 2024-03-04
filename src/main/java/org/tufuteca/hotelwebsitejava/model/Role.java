package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userRole;

    @OneToMany(mappedBy = "role") // Обратное отношение к полю "role" в классе User
    private Set<User> users;
}