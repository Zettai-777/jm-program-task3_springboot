package ru.zettai.jmprogramtask3_springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zettai.jmprogramtask3_springboot.dao.UserDAO;
import ru.zettai.jmprogramtask3_springboot.entities.User;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserDAO userDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public boolean saveUser(User user){
        User userFromDB = userDAO.findByUsername(user.getUsername());
        if( userFromDB != null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return true;
    }

    public void updateUser(User user){
        userDAO.save(user);
    }

    public User findUserById(long id){
        return userDAO.findById(id).get();
    }

    public void deleteUserById(long id){
        userDAO.deleteById(id);
    }

    public User getUserByUsername(String username){
        return userDAO.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User currentUser = getUserByUsername(username);
        if(currentUser == null){
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
        return currentUser;
    }
}
