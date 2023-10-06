package ru.practicum.shareit.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.validation.annotation.CustomValidEmail;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_EMPTY__NAME;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID__EMAIL;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CustomValidNotBlank(error = USER_ERROR__VALID_EMPTY__NAME)
    @Column(name = "name", length = 20)
    private String name;

    @CustomValidEmail(error = USER_ERROR__VALID__EMAIL)
    @Column(name = "email", length = 36, unique = true)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private final List<Item> items = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private final List<Booking> bookings = new ArrayList<>();
}
