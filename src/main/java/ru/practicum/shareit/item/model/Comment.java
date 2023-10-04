package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.validation.annotation.CustomValidNotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.COMMENT_ERROR__VALID_TEXT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    @CustomValidNotBlank(error = COMMENT_ERROR__VALID_TEXT)
    private String text;

    @Column(name = "created")
    private LocalDateTime created;
}
