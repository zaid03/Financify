package com.financify.Security;

import org.mindrot.jbcrypt.BCrypt;
import java.security.SecureRandom;

public class PasswordUtils {
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRecoveryToken() {
        StringBuilder token = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
        }

        return token.toString();
    }

    public static String hashPassword(String password) {return BCrypt.hashpw(password, BCrypt.gensalt(12));}
    public static boolean checkPassword(String password, String hashedPassword) {return BCrypt.checkpw(password, hashedPassword);}
}