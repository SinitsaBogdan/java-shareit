package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Запрос списка всех вещей пользователя")
    public void findAllByUserId() throws Exception {
        List<ItemDto> response = List.of(
                ItemDto.builder().id(1L).name("item-1").description("description-1").available(true).build(),
                ItemDto.builder().id(2L).name("item-2").description("description-2").available(false).build()
        );

        when(service.getAllByUserId(1L)).thenReturn(response);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Запрос вещи пользователя по ID")
    public void findByItemId() throws Exception {
        ItemDto response = ItemDto.builder().id(1L).name("item-1").description("description-1").available(true).build();

        when(service.getById(1L, 1L)).thenReturn(response);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("item-1"))
                .andExpect(jsonPath("$.description").value("description-1"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    @DisplayName("Запрос записей вещей по поиску в text (название или описание)")
    public void findAllByText() throws Exception {
        List<ItemDto> response = List.of(
                ItemDto.builder().id(1L).name("item-1").description("description-1").available(true).build(),
                ItemDto.builder().id(2L).name("item-2").description("description-2").available(false).build()
        );

        when(service.getBySearchText("item")).thenReturn(response);

        mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1)
                        .param("text", "item")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Добавление новой записи вещи пользователя")
    public void saveItem() throws Exception {
        ItemDto request = ItemDto.builder().name("item-1").description("description-1").available(true).build();
        ItemDto response = ItemDto.builder().id(1L).name("item-1").description("description-1").available(true).build();
        when(service.saveItem(anyLong(), any())).thenReturn(response);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("item-1"))
                .andExpect(jsonPath("$.description").value("description-1"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    @DisplayName("Добавление комментария к вещи")
    public void saveComment() throws Exception {
        CommentDto request = CommentDto.builder().text("comment text").build();
        CommentDto response = CommentDto.builder().id(1L).text("comment text").authorName("user").created(LocalDateTime.of(2023, 10, 20, 10, 0)).build();
        when(service.saveComment(anyLong(), anyLong(), any())).thenReturn(response);

        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("comment text"))
                .andExpect(jsonPath("$.authorName").value("user"))
                .andExpect(jsonPath("$.created").value("2023-10-20T10:00:00"));
    }

    @Test
    @DisplayName("Обновление существующей записи вещи пользователя")
    public void updateItem() throws Exception {
        ItemDto request = ItemDto.builder().name("update").description("update").available(true).build();
        ItemDto response = ItemDto.builder().id(1L).name("update").description("update").available(false).build();
        when(service.updateItem(anyLong(), any())).thenReturn(response);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("update"))
                .andExpect(jsonPath("$.description").value("update"))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    @DisplayName("Удаление существующей записи вещи пользователя")
    public void deleteItem() throws Exception {
        mvc.perform(delete("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}