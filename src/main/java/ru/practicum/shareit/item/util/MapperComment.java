package ru.practicum.shareit.item.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.util.exeptions.CustomException;

public final class MapperComment {

    private MapperComment() {
        throw new CustomException("This is a utility class and cannot be instantiated", 500);
    }

    public static CommentDto mapperEntityToDto(@NotNull Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorName(comment.getUser().getName())
                .created(comment.getCreated())
                .text(comment.getText())
                .build();
    }

    public static Comment mapperDtoToEntity(@NotNull CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .build();
    }
}
