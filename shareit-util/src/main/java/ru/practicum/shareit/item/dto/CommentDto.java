package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@Validated
public class CommentDto {

    private final Long id;

    private final String authorName;

    private final LocalDateTime created;

    @NotBlank
    private final String text;
}
