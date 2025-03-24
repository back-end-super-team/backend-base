package backend.base.unit.utility;

import backend.base.utility.PasswordEncryption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class PasswordEncryptionTest {

    private final static String KEY = "TheSecretKeyLengthMustBe32Byte..";

    @Test
    public void encrypt() throws Exception {
        String password = "foobar";
        String encryptedPassword = PasswordEncryption.encrypt(password, KEY);
        assertNotEquals(password, encryptedPassword);
        assertFalse(encryptedPassword.contains(password));
    }

    @Test
    public void decrypt() throws Exception {
        String encryptedPassword = "El7EW2eY6WiJbGb8hHn4rB";
        String decryptedPassword = PasswordEncryption.decrypt(encryptedPassword, KEY);
        assertNotEquals(encryptedPassword, decryptedPassword);
        assertFalse(encryptedPassword.contains(decryptedPassword));
    }

}
