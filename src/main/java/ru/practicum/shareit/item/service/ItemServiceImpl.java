package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.util.exeptions.ShareitException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
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
        return itemRepository.findByOwner(userId).stream()
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

    @Override
    public ItemDto addToUser(Long userId, ItemDto item) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            Item result = ItemMapper.mapperItemDtoToItem(item);
            result.setOwner(userId);
            result = itemRepository.save(result);
            return ItemMapper.mapperItemToDto(result);
        } else {
            throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }

    @Override
    public ItemDto updateToUser(Long userId, ItemDto item) {
        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
            User resultUser = optionalUser.get();

            Optional<Item> optionalItem = itemRepository.findById(item.getId());
            if (optionalItem.isEmpty()) throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
            Item resultItem = optionalItem.get();

            if (item.getName() != null && !item.getName().equals(resultItem.getName())) resultItem.setName(item.getName());
            if (item.getDescription() != null && !item.getDescription().equals(resultItem.getDescription())) resultItem.setDescription(item.getDescription());
            if (item.getAvailable() != null) resultItem.setAvailable(item.getAvailable());

            resultItem = itemRepository.save(resultItem);
            return ItemMapper.mapperItemToDto(resultItem);

        } catch (NoSuchElementException exception) {
            throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        }
    }
}
