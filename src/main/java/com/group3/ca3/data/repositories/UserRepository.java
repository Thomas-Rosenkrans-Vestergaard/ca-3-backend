package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.jwt.Role;

public interface UserRepository
{

    User create(String username, String password, String email, Role role);

    User delete(User user);

    User getById(Long id);

    User getByEmail(String email);

}
