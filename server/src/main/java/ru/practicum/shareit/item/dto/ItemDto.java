package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank
    private final String name;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String description;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Boolean available;

    private LocalBooker lastBooking;

    private LocalBooker nextBooking;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestId;

    private final List<CommentDto> comments;

    @Data
    public static class LocalBooker {
        private final Long id;
        private final Long bookerId;
    }
}
