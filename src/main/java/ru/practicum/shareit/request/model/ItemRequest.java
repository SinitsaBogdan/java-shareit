package ru.practicum.shareit.request.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private LocalDateTime created;
}
