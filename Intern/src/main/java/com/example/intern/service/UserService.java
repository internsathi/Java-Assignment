package com.example.intern.service;


import com.example.intern.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    User findByEmail(String email);

    List<User> findAllUsers();

    User getUserById(Integer id);

    // Delete user By userid
    void deleteUser(Integer id);
    void updateUser(User user);
}
