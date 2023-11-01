package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient client;

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        log.info("   GET [http://localhost:8080/users] : Запрос на получение всех пользователей");
        return client.get();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(
            @PathVariable @Positive long userId
    ) {
        log.info("   GET [http://localhost:8080/users] : Запрос на получение всех пользователей");
        return client.get(userId);
    }

    @PostMapping
    public ResponseEntity<Object> add(
            @RequestBody @Valid UserDto userDto
    ) {
        log.info("  POST [http://localhost:8080/users] : Запрос на добавление пользователя - {}", userDto);
        return client.add(userDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(
            @PathVariable @Positive long userId,
            @RequestBody UserDto userDto
    ) {
        log.info(" PATCH [http://localhost:8080/users/{}] : Запрос на обновление пользователя - {}", userId, userDto);
        return client.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(
            @PathVariable @Positive long userId
    ) {
        log.info(" DELETE [http://localhost:8080/users/{}] : Запрос на удаление пользователя id : {}", userId, userId);
        return client.delete(userId);
    }
}
