package ru.practicum.shareit.item;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, @NotNull RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getItems(long userId) {
        Map<String, Object> parameters = Map.of();
        return get("", userId, parameters);
    }

    public ResponseEntity<Object> getItemById(long userId, long itemId) {
        Map<String, Object> parameters = Map.of();
        return get("/" + itemId, userId, parameters);
    }

    public ResponseEntity<Object> getAllToSearchText(long userId, String text) {
        Map<String, Object> parameters = Map.of(
                "X-Sharer-User-Id", userId
        );
        return get("/search?text=" + text, userId);
    }

    public ResponseEntity<Object> add(long userId, ItemDto itemDto) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> add(long userId, long itemId, CommentDto commentDto) {
        return post(String.format("/%s/comment", itemId), userId, commentDto);
    }

    public ResponseEntity<Object> update(long userId, long itemId, ItemDto itemDto) {
        Map<String, Object> parameters = Map.of(
                "X-Sharer-User-Id", userId
        );
        return patch("/" + itemId, userId, parameters, itemDto);
    }
}
