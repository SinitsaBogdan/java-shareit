package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.validation.annotation.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.COMMENT_ERROR__VALID_TEXT;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false, length = 512)
    @NotBlank(error = COMMENT_ERROR__VALID_TEXT)
    private String text;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
