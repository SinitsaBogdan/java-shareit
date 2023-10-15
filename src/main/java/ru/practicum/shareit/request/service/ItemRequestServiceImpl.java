package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.RepositoryException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID;
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
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        return itemRequestRepository.findByUser(optionalUser.get()).stream().map(ItemRequestMapper::mapperItemRequestToDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> findAll(long userId, Pageable pageable) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        return itemRequestRepository.findItemRequest(userId, pageable).stream().map(ItemRequestMapper::mapperItemRequestToDto).collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto findOne(long userId, long requestId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<ItemRequest> optionalItemRequest = itemRequestRepository.findById(requestId);
        if (optionalItemRequest.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID);

        return ItemRequestMapper.mapperItemRequestToDto(optionalItemRequest.get());
    }

    @Override
    public ItemRequestDto save(long userId, ItemRequestDto requestDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        ItemRequest request = ItemRequestMapper.mapperItemRequestDtoToItemRequest(requestDto);

        request.setUser(optionalUser.get());
        request.setCreated(LocalDateTime.now());
        request = itemRequestRepository.save(request);

        return ItemRequestMapper.mapperItemRequestToDto(request);
    }
}