package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.util.validation.annotation.CustomValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID__EMAIL;

@Data
@Builder
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;

    @NotNull
    private final String name;

    @NotEmpty
    @CustomValidEmail(error = USER_ERROR__VALID__EMAIL)
    private final String email;
}
