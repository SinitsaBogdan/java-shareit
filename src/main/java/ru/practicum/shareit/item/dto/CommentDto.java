package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private final Long id;

    private final String authorName;

    private final LocalDateTime created;

    private final String text;
}
