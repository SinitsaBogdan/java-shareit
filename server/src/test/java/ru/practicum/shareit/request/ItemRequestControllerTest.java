package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @MockBean
    private ItemRequestService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Сохранение нового запроса вещи")
    public void saveItemRequest() throws Exception {
        ItemRequestDto request = ItemRequestDto.builder().description("description").build();
        ItemRequestDto response = ItemRequestDto.builder().id(1L).description("description").created(LocalDateTime.of(2023, 10, 20, 10, 0)).build();
        when(service.save(anyLong(), any())).thenReturn(response);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId()), Long.class));
    }

    @Test
    @DisplayName("Запрос списка всех запросов всех пользователей")
    public void findAllItemRequest() throws Exception {
        List<ItemRequestDto> response = List.of(

                ItemRequestDto.builder().id(1L)
                        .description("description-1")
                        .created(LocalDateTime.of(2023, 10, 20, 10, 0))
                        .items(new ArrayList<>())
                        .build(),

                ItemRequestDto.builder().id(2L)
                        .description("description-2")
                        .created(LocalDateTime.of(2023, 10, 20, 12, 0))
                        .items(new ArrayList<>())
                        .build(),

                ItemRequestDto.builder().id(3L)
                        .description("description-3")
                        .created(LocalDateTime.of(2023, 10, 20, 14, 0))
                        .items(new ArrayList<>())
                        .build()
        );

        when(service.findAll(anyLong(), any())).thenReturn(response);

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Запрос списка запросов пользователя")
    public void findAllItemRequestByUser() throws Exception {
        List<ItemRequestDto> response = List.of(

                ItemRequestDto.builder().id(2L)
                        .description("description-2")
                        .created(LocalDateTime.of(2023, 10, 20, 12, 0))
                        .items(new ArrayList<>())
                        .build(),

                ItemRequestDto.builder().id(3L)
                        .description("description-3")
                        .created(LocalDateTime.of(2023, 10, 20, 14, 0))
                        .items(new ArrayList<>())
                        .build()
        );

        when(service.findAll(anyLong())).thenReturn(response);

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Запрос запроса по ID")
    public void findItemRequestByIdRequest() throws Exception {
        when(service.findOne(anyLong(), anyLong())).thenReturn(
                ItemRequestDto.builder()
                        .id(2L)
                        .description("description-2")
                        .created(LocalDateTime.of(2023, 10, 20, 12, 0))
                        .items(new ArrayList<>())
                        .build()
        );

        mvc.perform(get("/requests/2")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("description-2"))
                .andExpect(jsonPath("$.created").value("2023-10-20T12:00:00"))
                .andExpect(jsonPath("$.items.length()").value(0));
    }
}