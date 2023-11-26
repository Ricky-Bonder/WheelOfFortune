package Database;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashing {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20 * 1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 128;

    /**
     * Computes a salted PBKDF2 hash of given plaintext password suitable for storing in a database.
     * Empty passwords are not supported.
     *
     * @param subject
     * @return
     * @throws java.lang.Exception
     */
    public static String getSaltedHash(String subject) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        System.out.println("la pw hashata in hashing è "+Base64.getEncoder().encodeToString(salt) + "$" + hash(subject, salt));
        return Base64.getEncoder().encodeToString(salt) + "$" + hash(subject, salt);
        //        return Base64.encodeBase64String(salt) + "$" + hash(subject, salt);
    }

    /**
     * Checks whether given plaintext password corresponds to a stored salted hash of the password.
     *
     * @param subject
     * @param stored
     * @return
     * @throws java.lang.Exception
     */
    public static boolean check(String subject, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        System.out.println("in check subject è "+subject+" e stored è "+stored);
        if (saltAndPass.length != 2) {
            throw new IllegalStateException("The stored subject have the form 'salt$hash'");
        }
        String hashOfInput = hash(subject, Base64.getDecoder().decode(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String subject, byte[] salt) throws Exception {
        if (subject == null || subject.length() == 0) {
            throw new IllegalArgumentException("Empty subject are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key =
                f.generateSecret(new PBEKeySpec(subject.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}

