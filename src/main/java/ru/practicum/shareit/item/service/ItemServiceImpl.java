package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.util.exeptions.ShareitException;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.DEFAULT;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@Transactional
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Override
    public List<ItemDto> getAll() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getAllByUserId(Long userId) {
        return userRepository.getById(userId).getItems().stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailable(text, text, true).stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long itemId) {
        try {
            Optional<Item> optional = itemRepository.findById(itemId);
            return ItemMapper.mapperItemToDto(optional.get());
        } catch (NoSuchElementException exception) {
            throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        }
    }

    @Transactional(isolation = REPEATABLE_READ )
    @Override
    public ItemDto addToUser(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.mapperItemDtoToItem(itemDto);

        try {
            Optional<User> optional = userRepository.findById(userId);
            if (optional.isEmpty()) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
            item.setOwner(optional.get());
            item = itemRepository.save(item);
            return ItemMapper.mapperItemToDto(item);
        } catch (DataIntegrityViolationException exception) {
            throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }

    @Transactional
    @Override
    public ItemDto updateToUser(Long userId, ItemDto itemDto) {
        try {
            User user = userRepository.getById(userId);
            Item item = itemRepository.getById(itemDto.getId());

            if (!item.getOwner().getId().equals(user.getId())) throw new ShareitException(ITEM_ERROR__VALID__ITEM__USER_NOT_OWNER);

            if (itemDto.getName() != null && !itemDto.getName().equals(item.getName())) item.setName(itemDto.getName());
            if (itemDto.getDescription() != null && !itemDto.getDescription().equals(item.getDescription())) item.setDescription(itemDto.getDescription());
            if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());

            item = itemRepository.save(item);
            return ItemMapper.mapperItemToDto(item);

        } catch (DataIntegrityViolationException exception) {
            throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        }
    }
}
