package backend.base.unit.utility;

import backend.base.utility.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

    @Test
    public void generateRandomPassword() {
        int length = 10;
        String result = Utils.generateRandomPassword(length);
        assertEquals(length, result.length());
    }

    @ParameterizedTest
    @CsvSource({"this_is_snake_case, thisIsSnakeCase", "_is_snake_case, isSnakeCase"})
    public void toCamelCase(String text, String expectation) {
        String result = Utils.toCamelCase(text);
        assertEquals(expectation, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void toCamelCase_NullAndEmpty(String text) {
        String result = Utils.toCamelCase(text);
        assertEquals("", result);
    }

}
