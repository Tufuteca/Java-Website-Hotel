package org.tufuteca.hotelwebsitejava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="room_number")
public class RoomNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floor;

    private boolean availability;

    @ManyToOne // Один к многим
    @JoinColumn(name = "status")
    private Status status;

    @ManyToOne // Один к многим
    @JoinColumn(name = "type_of_room")
    private RoomType roomType;
}
