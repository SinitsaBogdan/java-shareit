package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @MockBean
    private BookingClient client;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Запрос всех записей бронирования пользователя")
    public void getAll() throws Exception {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(client.get(1L, "ALL", 1, 2)).thenReturn(response);

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Запрос записи бронирования по ID")
    public void getById() throws Exception {
    }

    @Test
    @DisplayName("Получение списка бронирований для всех вещей текущего пользователя")
    public void getByIdOwner() throws Exception {
    }

    @Test
    @DisplayName("Добавление новой записи бронирования пользователя")
    public void save() throws Exception {
    }

    @Test
    @DisplayName("Подтверждение бронирования")
    public void approved() throws Exception {
    }
}