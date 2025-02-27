package backend.backendbase.annotation;

import backend.backendbase.config.jpa.ULIDGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@IdGeneratorType(ULIDGenerator.class)
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ULID {
}
