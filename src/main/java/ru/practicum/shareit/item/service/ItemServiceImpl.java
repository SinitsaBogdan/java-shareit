package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
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
//        return itemRepository.findAllIsUser(userId).stream()
//                .map(ItemMapper::mapperItemToDto)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
//        if (text.isEmpty()) return new ArrayList<>();
//        return itemRepository.findAllIsText(text).stream()
//                .map(ItemMapper::mapperItemToDto)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public ItemDto getById(Long itemId) {
        return ItemMapper.mapperItemToDto(itemRepository.findByIdContaining(itemId));
    }

    @Override
    public ItemDto addToUser(Long userId, ItemDto item) {
//        Item result = ItemMapper.mapperItemDtoToItem(item);
//        result.setOwner(userId);
//        result = itemRepository.save(result);
//        return ItemMapper.mapperItemToDto(result);
        return null;
    }

    @Override
    public ItemDto updateToUser(Long userId, ItemDto item) {
//        Item itemDto = ItemMapper.mapperItemDtoToItem(item);
//
//        Validator.checkValidItemOwner(item.getId(), userId, itemRepository);
//        itemDto.setOwner(userId);
//
//        Item update = itemRepository.findById(item.getId());
//        if (item.getName() != null && !item.getName().equals(update.getName())) update.setName(item.getName());
//        if (item.getDescription() != null && !item.getDescription().equals(update.getDescription())) update.setDescription(item.getDescription());
//        if (item.getAvailable() != null) update.setAvailable(item.getAvailable());
//
//        itemDto = itemRepository.update(update);
//        return ItemMapper.mapperItemToDto(itemDto);
        return null;
    }
}
