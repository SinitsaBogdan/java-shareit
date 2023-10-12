package ru.practicum.shareit.request.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long>  {

    Optional<ItemRequest> findById(long requestId);

    Optional<ItemRequest> findByItemRequestByIdAndUser(long requestId, User user);

    List<ItemRequest> findByItemRequestByUser(User user);

    List<ItemRequest> findByItemRequest(Pageable pageable);
}