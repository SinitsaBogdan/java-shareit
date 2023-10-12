package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.ServiceException;

import java.util.List;
import java.util.Optional;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemRequestDto> findAll(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        itemRequestRepository.findByItemRequestByUser(optionalUser.get());
        return null;
    }

    @Override
    public List<ItemRequestDto> findAll(long userId, int from, int size) {
        Pageable pagingSort = PageRequest.of(from, size);
        itemRequestRepository.findByItemRequest(pagingSort);
        return null;
    }

    @Override
    public ItemRequestDto findOne(long userId, long requestId) {
        return null;
    }

    @Override
    public ItemRequestDto add(long userId, ItemRequestDto requestDto) {
        return null;
    }
}