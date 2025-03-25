package backend.base.unit.utility;

import backend.base.utility.EncryptionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EncryptionUtilsTest {

    private final static String KEY = "TheSecretKeyLengthMustBe32Byte..";
    private final static String plainText = "foobar";
    private final static String encryptedText = "FJE9XDOgDQxHtKT72NgZmRaykDlvU6VCMnYv52BBLpQO1g==";

    @Test
    public void encrypt() throws Exception {
        String result = EncryptionUtils.encrypt(plainText, KEY.getBytes(StandardCharsets.UTF_8));
        assertNotEquals(plainText, result);
        assertFalse(result.contains(plainText));
    }

    @Test
    public void decrypt() throws Exception {
        String result = EncryptionUtils.decrypt(encryptedText, KEY.getBytes(StandardCharsets.UTF_8));
        assertNotEquals(encryptedText, result);
        assertEquals(plainText, result);
    }

}
