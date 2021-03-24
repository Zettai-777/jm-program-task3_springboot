package ru.zettai.jmprogramtask3_springboot.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.zettai.jmprogramtask3_springboot.entities.Role;
import ru.zettai.jmprogramtask3_springboot.services.RoleService;


@Component
public class RoleToUserConverter implements Converter<String, Role> {

    RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String o) {
        Long id = Long.parseLong(o);
        Role role = roleService.findRoleById(id);
        return role;
    }
}
