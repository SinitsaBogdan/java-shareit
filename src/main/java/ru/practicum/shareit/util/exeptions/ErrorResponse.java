package ru.practicum.shareit.util.exeptions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class ErrorResponse {

    @NonNull
    private final String name;

    @NonNull
    private final String description;
}