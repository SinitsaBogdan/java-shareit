package ru.practicum.shareit.user.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exeptions.ShareitException;

import java.util.*;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_DUPLICATE__EMAIL;

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

        User update = data.get(user.getId());

        if (user.getName() != null && !user.getName().equals(update.getName())) update.setName(user.getName());
        if (user.getEmail() != null && !user.getEmail().equals(update.getEmail())) {
            if (dataEmail.contains(user.getEmail())) throw new ShareitException(USER_ERROR__VALID_DUPLICATE__EMAIL);
            dataEmail.remove(data.get(user.getId()).getEmail());
            dataEmail.add(user.getEmail());
            update.setEmail(user.getEmail());
        }
        data.put(update.getId(), update);
        return update;
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
}
