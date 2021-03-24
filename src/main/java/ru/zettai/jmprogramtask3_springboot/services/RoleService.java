package ru.zettai.jmprogramtask3_springboot.services;

import ru.zettai.jmprogramtask3_springboot.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveOrUpdateRole(Role role);

    Role findRoleById(long id);

    void deleteRoleById(long id);

    Role getRoleByName(String role);

}
