package ru.zettai.jmprogramtask3_springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zettai.jmprogramtask3_springboot.dao.RoleDAO;
import ru.zettai.jmprogramtask3_springboot.entities.Role;


import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleDAO roleDAO;

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    @Override
    public void saveOrUpdateRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public Role findRoleById(long id) {
        return roleDAO.findById(id).get();
    }

    @Override
    public void deleteRoleById(long id) {
        roleDAO.deleteById(id);
    }

    @Override
    public Role getRoleByName(String role) {
        return roleDAO.findByRole(role);
    }
}
