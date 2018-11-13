package com.group3.ca3.repositories;

import com.group3.ca3.data.entities.User;

public interface UserRepository {

    public User create(String username, String password, String email);

    public User delete(User user);

    public User getById(Long id);

    public User getByEmail(String email);

}
