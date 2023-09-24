package ru.practicum.shareit.booking.model;

import javax.persistence.*;

/**
 * TODO Sprint add-bookings.
 */

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
