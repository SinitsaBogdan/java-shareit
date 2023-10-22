package ru.practicum.shareit.util;

import java.util.Optional;

public enum BookingState {

    ALL,        // все
    APPROVED,   // подтвержденный
    CURRENT,    // текущие
    PAST,       // завершённые
    FUTURE,     // будущие
    WAITING,    // ожидающие подтверждения
    REJECTED;    // отклонённые

    public static Optional<BookingState> from(String stringState) {
        for (BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
