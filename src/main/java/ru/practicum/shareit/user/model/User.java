package ru.practicum.shareit.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.validation.annotation.CustomValidEmail;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_EMPTY__NAME;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID__EMAIL;

@Data
@Builder
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private final List<Item> items = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
