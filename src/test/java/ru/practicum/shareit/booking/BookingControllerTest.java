package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Запрос всех записей бронирования пользователя")
    public void findAllByUserIdAndStateInUser() throws Exception {
        List<BookingResponseDto> response = List.of(
                BookingResponseDto.builder().id(1L).build(),
                BookingResponseDto.builder().id(2L).build(),
                BookingResponseDto.builder().id(3L).build()
        );

        when(service.getAll(anyLong(), anyString(), any())).thenReturn(response);

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .param("state", "ALL")
                        .param("from", String.valueOf(1))
                        .param("size", String.valueOf(10))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
        ;
    }

    @Test
    @DisplayName("Запрос записи бронирования по ID")
    public void findByUserIdAndBookingId() throws Exception {
        BookingResponseDto response = BookingResponseDto.builder().id(1L).build();
        when(service.getById(1L, 1L)).thenReturn(response);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
        ;
    }

    @Test
    @DisplayName("Получение списка бронирований для всех вещей текущего пользователя")
    public void findAllByUserIdAndState() throws Exception {
        List<BookingResponseDto> response = List.of(
                BookingResponseDto.builder().id(1L).build(),
                BookingResponseDto.builder().id(2L).build(),
                BookingResponseDto.builder().id(3L).build()
        );

        when(service.getAllInItemOwner(anyLong(), anyString(), any())).thenReturn(response);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .param("state", "ALL")
                        .param("from", String.valueOf(1))
                        .param("size", String.valueOf(10))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
        ;
    }

    @Test
    @DisplayName("Добавление новой записи бронирования пользователя")
    public void saveBooking() throws Exception {
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                .end(LocalDateTime.of(2023, 10, 20, 10, 0))
                .build();

        BookingResponseDto response = BookingResponseDto.builder().id(1L).build();
        when(service.save(anyLong(), any())).thenReturn(response);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
        ;
    }

    @Test
    @DisplayName("Подтверждение бронирования")
    public void approveBooking() throws Exception {
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                .end(LocalDateTime.of(2023, 10, 20, 10, 0))
                .build();

        BookingResponseDto response = BookingResponseDto.builder().id(1L).build();
        when(service.updateApproved(anyLong(), anyLong(), anyBoolean())).thenReturn(response);

        mvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .param("approved", String.valueOf(true))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
        ;
    }
}