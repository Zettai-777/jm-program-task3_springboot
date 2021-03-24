package ru.zettai.jmprogramtask3_springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zettai.jmprogramtask3_springboot.entities.User;

public interface UserDAO extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
