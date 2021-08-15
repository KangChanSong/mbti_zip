package com.mbtizip.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

public class EncryptHelper{
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static Boolean isMatch(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
