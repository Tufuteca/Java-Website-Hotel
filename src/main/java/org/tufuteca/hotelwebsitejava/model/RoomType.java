package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private float price;

    @Column(nullable = false, unique = false)
    private float area;

    @Column(nullable = false, unique = false)
    private int places;
}
