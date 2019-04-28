package learningapp.mappers;

import java.util.UUID;

import learningapp.exceptions.base.InvalidFormatException;
import lombok.experimental.UtilityClass;

import static learningapp.exceptions.ExceptionMessages.STRING_TO_UUID_ERROR;

@UtilityClass
public class GeneralMapper {

    public static UUID uuidFromString(String value) {
        if (value == null) {
            throw new InvalidFormatException(STRING_TO_UUID_ERROR);
        }

        if (value.equals("")) {
            throw new InvalidFormatException(STRING_TO_UUID_ERROR);
        }

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(STRING_TO_UUID_ERROR);
        }
    }

}
