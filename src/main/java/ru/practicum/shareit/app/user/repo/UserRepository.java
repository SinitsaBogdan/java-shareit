package ru.practicum.shareit.app.user.repo;

import ru.practicum.shareit.app.user.model.User;

import java.util.Collection;

public interface UserRepository {

    Boolean checkEmailDuplicate(String email);

    Boolean checkId(Long id);

    Collection<User> findAll();

    User findById(Long userId);

    User save(User user);

    User update(User user);

    void deleteAll();

    void deleteById(User user);

    void addDataEmail(String email);

    void removeDataEmail(String email);
}
