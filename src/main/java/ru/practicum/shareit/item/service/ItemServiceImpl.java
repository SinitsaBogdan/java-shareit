package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Override
    public List<ItemDto> getAll() {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.findAll()) result.add(ItemMapper.mapperItemToDto(item));
        return result;
    }

    @Override
    public List<ItemDto> getAllByUser(Long userId) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.findAllByUser(userId)) {
            if (item.getOwner().equals(userId)) result.add(ItemMapper.mapperItemToDto(item));
        }
        return result;
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
        List<ItemDto> result = new ArrayList<>();
        if (text.isEmpty()) return result;
        for (Item item : itemRepository.findAll()) {
            if (!item.getAvailable()) continue;
            if (item.getName().toLowerCase().contains(text)) result.add(ItemMapper.mapperItemToDto(item));
            else if (item.getDescription().toLowerCase().contains(text)) result.add(ItemMapper.mapperItemToDto(item));
        }
        return result;
    }

    @Override
    public ItemDto getById(Long itemId) {
        Validator.checkIdInUserRepo(itemId, userRepository);
        return ItemMapper.mapperItemToDto(itemRepository.findById(itemId));
    }

    @Override
    public ItemDto addToUser(Long userId, ItemDto item) {
        Validator.checkIdInUserRepo(userId, userRepository);
        Validator.checkValidItem(item);

        Item result = ItemMapper.mapperItemDtoToItem(item);
        result.setOwner(userId);
        result = itemRepository.save(result);
        return ItemMapper.mapperItemToDto(result);
    }

    @Override
    public ItemDto updateToUser(Long userId, ItemDto item) {
        Validator.checkIdInUserRepo(userId, userRepository);
        Validator.checkIdInItemRepo(item.getId(), itemRepository);

        Item result = ItemMapper.mapperItemDtoToItem(item);

        Validator.checkValidItemOwner(item.getId(), userId, itemRepository);
        result.setOwner(userId);

        result = itemRepository.update(result);
        return ItemMapper.mapperItemToDto(result);
    }

    @Override
    public void deleteToUser(Long userId, Long itemId) {

    }
}
