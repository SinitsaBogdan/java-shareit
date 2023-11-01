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
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(client.get(1L, 1L)).thenReturn(response);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение списка бронирований для всех вещей текущего пользователя")
    public void getByIdOwner() throws Exception {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(client.getOwner(1L, "ALL", 0, 4)).thenReturn(response);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "4")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление новой записи бронирования пользователя")
    public void save() throws Exception {
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(1L).start(LocalDateTime.now())
                .end(LocalDateTime.now()).build();
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(client.add(1L, request)).thenReturn(response);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Подтверждение бронирования")
    public void approvedUp() throws Exception {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(client.approvedUp(1L, 1L, true)).thenReturn(response);

        mvc.perform(patch("/bookings/1?approved=true")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}