package ru.practicum.shareit.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.validation.annotation.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_EMPTY__NAME;

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

    @NotBlank(error = USER_ERROR__VALID_EMPTY__NAME)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "email", length = 36, unique = true, nullable = false)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private final List<Item> items = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private final List<Booking> bookings = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();
}
