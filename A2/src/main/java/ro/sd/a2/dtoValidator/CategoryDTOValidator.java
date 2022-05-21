package ro.sd.a2.dtoValidator;

import ro.sd.a2.dto.CategoryDTO;
import ro.sd.a2.validator.Validator;

public class CategoryDTOValidator {

    public static void validate(CategoryDTO categoryDTO) {

        Validator.isNotEmpty(categoryDTO.getName());
    }

}
