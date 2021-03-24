package ru.zettai.jmprogramtask3_springboot.services;

import ru.zettai.jmprogramtask3_springboot.entities.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    boolean saveUser(User user);

    void updateUser(User user);

    User findUserById(long id);

    void deleteUserById(long id);

    User getUserByUsername(String username);


}
