package ru.practicum.shareit.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, @NotNull RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> get() {
        return get("");
    }

    public ResponseEntity<Object> get(Long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> add(UserDto dto) {
        return post("/", dto);
    }

    public ResponseEntity<Object> update(long userId, UserDto dto) {
        return patch("/" + userId, dto);
    }

    public ResponseEntity<Object> delete(long userId) {
        return delete("/" + userId);
    }
}
