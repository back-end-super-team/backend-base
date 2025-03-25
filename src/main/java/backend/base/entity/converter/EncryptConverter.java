package backend.base.entity.converter;

import backend.base.utility.EncryptionUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

@Converter
public class EncryptConverter implements AttributeConverter<String, String> {

    @Value("${config.encrypt.secretKey}")
    private String secretKey;

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return EncryptionUtils.encrypt(s, secretKey.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if (s!= null && !s.isEmpty()) {
            try {
                return EncryptionUtils.decrypt(s, secretKey.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }



}
