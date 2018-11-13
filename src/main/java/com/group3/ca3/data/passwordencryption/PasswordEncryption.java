package com.group3.ca3.data.passwordencryption;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {

    private String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private Boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
