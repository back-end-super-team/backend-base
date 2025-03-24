package backend.base.entity.converter;

import backend.base.utility.PasswordEncryption;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

@Converter
public class EncryptConverter implements AttributeConverter<String, String> {

    @Value("${config.encrypt.secretKey}")
    private String secretKey;

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return PasswordEncryption.encrypt(s, secretKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if (s!= null && !s.isEmpty()) {
            try {
                return PasswordEncryption.decrypt(s, secretKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }



}
