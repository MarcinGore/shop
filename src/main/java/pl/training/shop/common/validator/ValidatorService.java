package pl.training.shop.common.validator;

import javax.validation.*;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@RequiredArgsConstructor
public class ValidatorService {

    private final Validator validator;

    public <O,E extends RuntimeException> void validate(O object, Class<E> exceptionType) throws E{
        Set<ConstraintViolation<O>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            try{
                Constructor<E> exception = exceptionType.getDeclaredConstructor();
                throw exception.newInstance();
            } catch (InstantiationException e) {
               throw new IllegalArgumentException();
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException();
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException();
            }
        }
    }
}
