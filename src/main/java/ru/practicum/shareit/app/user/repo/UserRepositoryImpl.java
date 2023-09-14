package ru.practicum.shareit.app.user.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.app.user.model.User;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static Long id = 1L;
    private static final Set<String> dataEmail = new TreeSet<>();
    private static final Map<Long, User> data = new HashMap<>();

    @Override
    public Boolean checkEmailDuplicate(String email) {
        return dataEmail.contains(email);
    }

    @Override
    public Boolean checkId(Long id) {
        return data.containsKey(id);
    }

    @Override
    public Collection<User> findAll() {
        return data.values();
    }

    @Override
    public User findById(Long userId) {
        return data.get(userId);
    }

    @Override
    public User save(User user) {
        user.setId(id++);
        dataEmail.add(user.getEmail());
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteById(User user) {
        data.remove(user.getId());
        dataEmail.remove(user.getEmail());
    }

    @Override
    public void addDataEmail(String email) {
        dataEmail.add(email);
    }

    @Override
    public void removeDataEmail(String email) {
        dataEmail.remove(email);
    }
}
