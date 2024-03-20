package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="booking_room")
public class BookingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private float price;

    @Column(nullable = false,unique = false,name = "check-in-date")
    private LocalDate checkInDate;

    @Column(nullable = false,unique = false,name = "departure-date")
    private LocalDate departureDate;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room-number")
    private RoomNumber roomNumber;
}
