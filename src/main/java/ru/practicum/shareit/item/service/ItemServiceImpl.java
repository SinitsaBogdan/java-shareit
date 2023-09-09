package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.Validator;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    @Override
    public List<ItemDto> getAll() {
        return null;
    }

    @Override
    public List<ItemDto> getAllByUser(Long userId) {
        return null;
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
        return null;
    }

    @Override
    public ItemDto getById(Long itemId) {
        return null;
    }

    @Override
    public ItemDto addToUser(Long userId, ItemDto item) {

        Validator.check(userId, itemRepository);
        Validator.check(item);

        Item result = ItemMapper.mapperItemDtoToItem(item);
        result.setOwner(userId);
        result = itemRepository.save(result);
        return ItemMapper.mapperItemToDto(result);
    }

    @Override
    public ItemDto updateToUser(Long userId, ItemDto item) {
        return null;
    }

    @Override
    public void deleteToUser(Long userId, Long itemId) {

    }
}
