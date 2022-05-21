package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.CategoryDTO;
import ro.sd.a2.dto.ProductDTO;
import ro.sd.a2.dtoValidator.CategoryDTOValidator;
import ro.sd.a2.entity.Category;
import ro.sd.a2.entity.Product;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.CategoryRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Add a new product category. If category already exists, but its status is deleted,
     * this status is modified. If category already exists and its id is equals with
     * id of category from database, category details will be updated. If category already
     * exists, but its id is not equals with id of category from database, then an exception
     * is thrown. Otherwise, the new product category is added in database.
     * @param categoryDTO data about new product category
     * @throws InvalidDataException if category data are invalid or if already exists a category
     * with the same name.
     */
    public void add(CategoryDTO categoryDTO) {

        CategoryDTOValidator.validate(categoryDTO);

        String name = categoryDTO.getName();

        Category category = categoryDTO.toEntity();
        Category existingCategory = categoryRepository.findByName(name);

        if(ObjectUtils.isNotEmpty(existingCategory)) {
            //update
            if((category.getId()).equals(existingCategory.getId())) {
                categoryRepository.save(category);
                logger.log(Level.INFO, "Category with id " + category.getId() +
                                        " successfully edited");
                return ;
            }

            //add
            boolean deleted = existingCategory.isDeleted();

            if(BooleanUtils.isTrue(deleted)) {
                category.setId(existingCategory.getId());
                categoryRepository.save(category);
                logger.log(Level.INFO, "Category with id " + category.getId() +
                                     " successfully added; modified status");
                return ;
            }
            else {
                throw new InvalidDataException(WrongMessage.WRONG_CATEGORY_NAME);
            }
        }

        categoryRepository.save(category);
        logger.log(Level.INFO, "Category with id " + category.getId() + " successfully added");
    }

    /**
     * Find category with specified id
     * @param id id of product category to be found
     * @throws InvalidDataException if category id is invalid
     * @return a CategoryDTO containing necessary information about the found category
     */
    public CategoryDTO find(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search category with id " + id);

        Category category = categoryRepository.getOne(id);

        if(ObjectUtils.isEmpty(category)) {
            logger.log(Level.INFO, "Category with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "Category with id " + id + " found");

        return CategoryDTO.fromEntity(category);
    }

    /**
     * Find all categories
     * @return a list of CategoryDTO which contains necessary information about the found categories
     */
    public List<CategoryDTO> findAll() {

        logger.log(Level.INFO, "Search all categories");

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoriesDTOs =  categories.stream()
                                                .map(c -> CategoryDTO.fromEntity(c))
                                                .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all categories");

        return categoriesDTOs;
    }

    /**
     * Delete category with specified id
     * @param id id of category to be deleted
     * @throws InvalidDataException if category id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        categoryRepository.deleteById(id);

        logger.log(Level.INFO, "Category with id " + id + " successfully deleted");
    }

}
