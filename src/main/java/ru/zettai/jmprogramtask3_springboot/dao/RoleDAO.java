package ru.zettai.jmprogramtask3_springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zettai.jmprogramtask3_springboot.entities.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
