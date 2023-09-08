package ru.practicum.shareit.util;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.util.exeptions.ShareitException;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

public class Validator {

    public static void check(Long id, ItemRepository repository) {
        if (id == null) throw new ShareitException(GLOBAL_ERROR__NOT_HEADER_IN_REQUEST);
        if (!repository.checkId(id)) throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
    }

    public static void check(ItemDto item) {
        if (item.getAvailable() == null) throw new ShareitException(ITEM_ERROR__VALID__AVAILABLE);
        if (item.getName() == null) throw new ShareitException(ITEM_ERROR__VALID__NAME);
        if (item.getDescription() == null) throw new ShareitException(ITEM_ERROR__VALID__DESCRIPTION);
    }
}
