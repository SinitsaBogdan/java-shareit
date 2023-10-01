package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import java.time.LocalDateTime;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.COMMENT_ERROR__VALID_TEXT;

@Data
@Builder
public class CommentDto {

    private Long id;

    private String authorName;

    private LocalDateTime created;

    private String text;
}
