package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.util.exeptions.ShareitException;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

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
        if (!itemRepository.checkId(itemId)) throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        return ItemMapper.mapperItemToDto(itemRepository.findById(itemId));
    }

    @Override
    public ItemDto addToUser(Long userId, ItemDto item) {
        if (userId == null) throw new ShareitException(GLOBAL_ERROR__NOT_HEADER_IN_REQUEST);
        if (!userRepository.checkId(userId)) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        Validator.check(item);

        Item result = ItemMapper.mapperItemDtoToItem(item);
        result.setOwner(userId);
        result = itemRepository.save(result);
        return ItemMapper.mapperItemToDto(result);
    }

    @Override
    public ItemDto updateToUser(Long userId, ItemDto item) {
        if (userId == null) throw new ShareitException(GLOBAL_ERROR__NOT_HEADER_IN_REQUEST);
        if (!userRepository.checkId(userId)) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        if (!itemRepository.checkId(item.getId())) throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        Item result = ItemMapper.mapperItemDtoToItem(item);

        if (!itemRepository.findById(item.getId()).getOwner().equals(userId)) throw new ShareitException(ITEM_ERROR__VALID__ITEM__USER_NOT_OWNER);
        result.setOwner(userId);
        result = itemRepository.update(result);
        return ItemMapper.mapperItemToDto(result);
    }

    @Override
    public void deleteToUser(Long userId, Long itemId) {

    }
}
