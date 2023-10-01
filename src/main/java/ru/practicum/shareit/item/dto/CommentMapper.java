package ru.practicum.shareit.item.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

public class CommentMapper {

    public static CommentDto mapperCommentToDto(@NotNull Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorName(comment.getUser().getName())
                .created(comment.getCreated())
                .text(comment.getText())
                .build();
    }

    public static Comment mapperCommentDtoToComment(@NotNull CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .build();
    }
}
