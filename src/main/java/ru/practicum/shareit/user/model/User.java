package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.util.validation.annotation.CustomValidEmail;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import javax.persistence.*;

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
    @Column(name = "email", length = 36, unique=true)
    private String email;
}
