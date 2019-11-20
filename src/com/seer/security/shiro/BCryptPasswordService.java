package com.seer.security.shiro;

import org.apache.shiro.authc.credential.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This PasswordService implementation uses BCRypt as underlying tool.
 * It enables working with salted hashes without implementing HashedPasswordService.
 * BCrypt hash format already contains salt information and there is no need for providing it through Shiro Hash object.
 */
public class BCryptPasswordService implements PasswordService {

    public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        return BCrypt.hashpw(plaintextPassword.toString(), BCrypt.gensalt());
    }

    /**
     * Matches plain password with stored salted hash.
     *
     * @param submittedPlaintext
     * @param savedPasswordHash BCRypt generated string that contains concatenated salt and password hash
     * @return
     */
    public boolean passwordsMatch(Object submittedPlaintext, String savedPasswordHash) {
        return BCrypt.checkpw(submittedPlaintext.toString(), savedPasswordHash);
    }
}
