package ru.practicum.shareit.request.model;

import javax.persistence.*;

/**
 * TODO Sprint add-item-requests.
 */

@Entity
@Table(name = "item_request")
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
