package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import ru.practicum.shareit.item.model.Item;

import javax.persistence.*;
import java.time.Instant;

/**
 * TODO Sprint add-bookings.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "item_id")
    @Column(name = "item_id")
    private Long itemId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "time_start")
    private Instant start;

    @Column(name = "time_end")
    private Instant end;
}
