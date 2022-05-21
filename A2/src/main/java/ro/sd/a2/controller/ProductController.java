package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.*;
import ro.sd.a2.dtoValidator.ProductDTOValidator;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.service.CategoryService;
import ro.sd.a2.service.OrderItemService;
import ro.sd.a2.service.ProductService;
import ro.sd.a2.service.UserAuthentication;
import ro.sd.a2.service.document.invoice.InvoiceService;
import ro.sd.a2.service.document.soldProductReport.PdfReport;
import ro.sd.a2.service.document.soldProductReport.ReportService;
import ro.sd.a2.service.document.soldProductReport.TxtReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private PdfReport pdfReport;

    @Autowired
    private TxtReport txtReport;

    @Autowired
    private UserAuthentication userAuthentication;

    /**
     *
     * @return a page containing all products
     */
    @GetMapping("/products")
    public ModelAndView getAllProducts() {
        logger.log(Level.INFO, "/admin/products page accessed");

        ModelAndView mav = new ModelAndView();

        List<ProductDTO> productDTOs = productService.findAllAdmin();
        mav.addObject("products", productDTOs);
        UserDTO user = userAuthentication.getAuthenticatedUser();

        mav.addObject("user", user);

        mav.setViewName("products");

        return mav;
    }

    /**
     *
     * @return a form for adding a new product
     */
    @GetMapping("/addProductForm")
    public ModelAndView getAddProductForm() {
        logger.log(Level.INFO, "/admin/addProductForm page accessed");

        List<CategoryDTO> categoryDTOs = categoryService.findAll();

        ModelAndView mav = new ModelAndView();
        mav.addObject("categories", categoryDTOs);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("save_product");
        return mav;
    }

    /**
     *
     * @param id id of product to be edited
     * @return a form for editing product with desired id. Forms fields contain
     * current product details.
     */
    @GetMapping("/editProductForm/{id}")
    public ModelAndView getEditProductForm(@PathVariable("id") String id) {
        logger.log(Level.INFO, "/admin/editProductForm " + id + " page accessed");

        ProductDTO productDTO = productService.find(id);
        List<CategoryDTO> categoryDTOs = categoryService.findAll();

        ModelAndView mav = new ModelAndView();
        mav.addObject("product", productDTO);
        mav.addObject("categories", categoryDTOs);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("save_product");

        return mav;
    }


    /**
     * Add/edit a product
     * @param productDTO new data about product
     * @return a page containing all products if product was successfully created/updated. Otherwise
     * if new data related to product contains id_product this method returns an edit form
     * containing initial details about product or an add form if id_product is null
     */
    @PostMapping("/saveProduct")
    public RedirectView saveProduct(RedirectAttributes redir,
                                    ProductDTO productDTO) {

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to add/edit product");

        RedirectView redirectView;

        try {
            ProductDTOValidator.validate(productDTO);

            productService.add(productDTO);

            redirectView = new RedirectView("/admin/products",true);
        }
        catch(InvalidDataException e) {

            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot add/edit product " + productDTO.getId() +
                    " " + e.getMessage());

            String id_product = productDTO.getId();

            if(StringUtils.isEmpty(id_product))
                redirectView = new RedirectView("/admin/addProductForm", true);
            else
                redirectView = new RedirectView("/admin/editProductForm/" + id_product, true);

            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Delete product with desired id
     * @param id id of product to be deleted
     * @return a page containing all products
     */
    @GetMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to delete product " + id);

        productService.delete(id);

        return new ModelAndView("redirect:/admin/products");
    }

    /**
     * Generate a PDF report with sold products
     * @param redir redirect atributtes
     * @param periodDTO startDate and endDate necessary for generating the report
     * @param response http response
     * @return products page with error message if an error occurred
     */
    @PostMapping(value = "/download-sold-products-report", params = "pdf")
    public RedirectView downloadSoldProductReportPdf(RedirectAttributes redir,
                                                     PeriodDTO periodDTO, HttpServletResponse response) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to download a PDF with sold products");

        try {
            if(periodDTO.compareDates() > 0)
                throw new InvalidDataException(WrongMessage.INVALID_DATES);

            SoldProductsDTO soldProductsDTO = orderItemService.findAllSoldProducts(periodDTO);

            ReportService reportService = new ReportService(pdfReport);
            Path file = Paths.get(reportService.generateReport(soldProductsDTO).getAbsolutePath());

            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }

            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " successfully downloaded a PDF with sold products");

            return null;
        }
        catch (InvalidDataException e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot download a PDF with sold products " + e.getMessage());

            RedirectView redirectView= new RedirectView("/admin/products",true);
            redir.addFlashAttribute("exception", e.getMessage());
            return redirectView;
        }
        catch(Exception e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot download a PDF with sold products " + e.getMessage());

            return null;
        }

    }

    /**
     * Generate a TXT report with sold products
     * @param redir redirect atributtes
     * @param periodDTO startDate and endDate necessary for generating the report
     * @param response http response
     * @return products page with error message if an error occurred
     */
    @PostMapping(value = "/download-sold-products-report", params = "txt")
    public RedirectView downloadSoldProductReportTxt(RedirectAttributes redir,
                                             PeriodDTO periodDTO, HttpServletResponse response) {

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                " is trying to download a TXT with sold products");

        try {
            if(periodDTO.compareDates() > 0)
                throw new InvalidDataException(WrongMessage.INVALID_DATES);

            SoldProductsDTO soldProductsDTO = orderItemService.findAllSoldProducts(periodDTO);

            ReportService reportService = new ReportService(txtReport);
            Path file = Paths.get(reportService.generateReport(soldProductsDTO).getAbsolutePath());

            if (Files.exists(file)) {
                response.setContentType("application/txt");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }

            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " successfully downloaded a TXT with sold products");

            return null;
        }
        catch (InvalidDataException e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot download a TXT with sold products " + e.getMessage());

            RedirectView redirectView= new RedirectView("/admin/products",true);
            redir.addFlashAttribute("exception", e.getMessage());
            return redirectView;
        }
        catch(Exception e) {
            logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                    " cannot download a TXT with sold products " + e.getMessage());

            return null;
        }
    }


}
