package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;
}
