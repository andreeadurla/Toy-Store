package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.CategoryDTO;
import ro.sd.a2.dto.UserDTO;
import ro.sd.a2.dtoValidator.CategoryDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.service.CategoryService;
import ro.sd.a2.service.UserAuthentication;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class CategoryController {

    private static final Logger logger = Logger.getLogger(CategoryController.class.getName());

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all product categories
     */
    @GetMapping("/categories")
    public ModelAndView getAllCategories() {
        logger.log(Level.INFO, "/admin/categories page accessed");

        ModelAndView mav = new ModelAndView();

        List<CategoryDTO> categoryDTOs = categoryService.findAll();
        mav.addObject("categories", categoryDTOs);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("categories");

        return mav;
    }

    /**
     *
     * @return a form for adding a new product category
     */
    @GetMapping("/addCategoryForm")
    public ModelAndView getAddCategoryForm() {
        logger.log(Level.INFO, "/admin/addCategoryForm page accessed");

        ModelAndView mav = new ModelAndView();

        mav.addObject("user", userAuthentication.getAuthenticatedUser());
        mav.setViewName("save_category");

        return mav;
    }

    /**
     *
     * @param id id of product category to be edited
     * @return a form for editing category with desired id. Forms fields contain
     * current category details.
     */
    @GetMapping("/editCategoryForm/{id}")
    public ModelAndView getEditCategoryForm(@PathVariable("id") String id) {
        logger.log(Level.INFO, "/admin/editCategoryForm " + id + " page accessed");

        CategoryDTO categoryDTO = categoryService.find(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("category", categoryDTO);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("save_category");

        return mav;
    }

    /**
     * Add/edit a product category
     * @param categoryDTO new data about product category
     * @return a page containing all product categories if category was successfully created/updated. Otherwise
     * if new data related to category contains id_category this method returns an edit form
     * containing initial details about category or an add form if id_category is null
     */
    @PostMapping("/saveCategory")
    public RedirectView saveCategory(RedirectAttributes redir,
                                     CategoryDTO categoryDTO) {

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                                " is trying to add/edit category");

        RedirectView redirectView;

        try{
            CategoryDTOValidator.validate(categoryDTO);

            categoryService.add(categoryDTO);

            redirectView = new RedirectView("/admin/categories",true);
        }
        catch (InvalidDataException e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                                        " cannot add/edit category " + categoryDTO.getId() +
                                        " " + e.getMessage());

            String id_category = categoryDTO.getId();

            if(StringUtils.isEmpty(id_category))
                redirectView = new RedirectView("/admin/addCategoryForm", true);
            else
                redirectView = new RedirectView("/admin/editCategoryForm/" + id_category, true);

            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Delete category with desired id
     * @param id id of category to be deleted
     * @return a page containing all product categories
     */
    @GetMapping("/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to delete category " + id);

        categoryService.delete(id);

        return new ModelAndView("redirect:/admin/categories");
    }

}
