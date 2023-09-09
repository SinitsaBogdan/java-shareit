package ru.practicum.shareit.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.ShareitException;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

public class Validator {

    public static void check(Long id, ItemRepository repository) {
        if (id == null) throw new ShareitException(GLOBAL_ERROR__NOT_HEADER_IN_REQUEST);
        if (!repository.checkId(id)) throw new ShareitException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
    }

    public static void check(@NotNull ItemDto item) {
        if (item.getAvailable() == null) throw new ShareitException(ITEM_ERROR__VALID__AVAILABLE);
        if (item.getName() == null) throw new ShareitException(ITEM_ERROR__VALID__NAME);
        if (item.getDescription() == null) throw new ShareitException(ITEM_ERROR__VALID__DESCRIPTION);
    }

    public static void check(@NotNull UserDto user, UserRepository repository) {
        if (user.getName() == null || user.getName().isEmpty()) throw new ShareitException(USER_ERROR__VALID_EMPTY__NAME);
        if (user.getEmail() == null || user.getEmail().isEmpty()) throw new ShareitException(USER_ERROR__VALID_EMPTY__EMAIL);
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) throw new ShareitException(USER_ERROR__VALID__EMAIL);
        if (repository.checkEmailDuplicate(user.getEmail())) throw new ShareitException(USER_ERROR__VALID_DUPLICATE__EMAIL);
    }
}
