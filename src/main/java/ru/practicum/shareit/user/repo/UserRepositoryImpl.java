package ru.practicum.shareit.user.repo;

import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private static final Map<Long, User> data = new HashMap<>();

    @Override
    public List<User> findAll() {
        return (List<User>) data.values();
    }

    @Override
    public User findById(Long userId) {
        return data.get(userId);
    }

    @Override
    public User save(User user) {
        return data.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        return data.put(user.getId(), user);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteById(Long userId) {
        data.remove(userId);
    }
}
