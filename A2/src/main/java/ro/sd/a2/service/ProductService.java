package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.ProductDTO;
import ro.sd.a2.dto.UnsoldProductDTO;
import ro.sd.a2.dtoValidator.ProductDTOValidator;
import ro.sd.a2.entity.Product;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.ProductRepository;
import ro.sd.a2.validator.Validator;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private final ProductRepository productRepository;

    /**
     * Add a new product. If product already exists, but its status is deleted,
     * this status is modified. If product already exists and its id is equals with
     * id of product from database, product details will be updated. If product already
     * exists, but its id is not equals with id of product from database, then an exception
     * is thrown. Otherwise, the new product is added in database.
     * @param productDTO data about new product
     * @throws InvalidDataException if product data are invalid or if already exists a product
     * with the same name.
     */
    public void add(ProductDTO productDTO) {

        ProductDTOValidator.validate(productDTO);

        String name = productDTO.getName();

        Product product = productDTO.toEntity();
        Product existingProduct = productRepository.findByName(name);

        if(ObjectUtils.isNotEmpty(existingProduct)) {
            //update
            if((product.getId()).equals(existingProduct.getId())) {
                productRepository.save(product);
                logger.log(Level.INFO, "Product with id " + product.getId() +
                                        " successfully edited");
                return ;
            }

            //add
            boolean deleted = existingProduct.isDeleted();

            if(BooleanUtils.isTrue(deleted)) {
                product.setId(existingProduct.getId());
                productRepository.save(product);
                logger.log(Level.INFO, "Product with id " + product.getId() +
                                    " successfully added; modified status");

                return ;
            }
            else {
                throw new InvalidDataException(WrongMessage.WRONG_PRODUCT_NAME);
            }
        }

        productRepository.save(product);
        logger.log(Level.INFO, "Product with id " + product.getId() + " successfully added");
    }

    /**
     * Find product with specified id
     * @throws InvalidDataException if product id is invalid
     * @param id id of product to be found
     * @return a ProductDTO containing necessary information about the found product
     */
    public ProductDTO find(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search product with id " + id);

        Product product = productRepository.getOne(id);

        if(ObjectUtils.isEmpty(product)) {
            logger.log(Level.INFO, "Product with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "Product with id " + id + " found");

        return ProductDTO.fromEntity(product);
    }

    /**
     * Find all products which have quantity greater than 0
     * @return a list of ProductDTO which contains necessary information about the
     * found products
     */
    public List<ProductDTO> findAll() {

        logger.log(Level.INFO, "Search all products (only products with quantity > 0)");

        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOs = products.stream()
                                            .map(p -> ProductDTO.fromEntity(p))
                                            .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all products (only products with quantity > 0)");

        return productDTOs;
    }

    /**
     * Find all products
     * @return a list of ProductDTO which contains necessary information about the
     * found products
     */
    public List<ProductDTO> findAllAdmin() {

        logger.log(Level.INFO, "Search all products");

        List<Product> products = productRepository.findAllAdmin();

        List<ProductDTO> productDTOs = products.stream()
                                            .map(p -> ProductDTO.fromEntity(p))
                                            .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all products");

        return  productDTOs;
    }


    /**
     * Find all products from the category with specified id
     * @param id_category id of product category to which the products belong
     * @throws InvalidDataException if category id is invalid
     * @return a list of ProductDTO which contains necessary information about the
     * found products
     */
    public List<ProductDTO> findByCategory(String id_category) {

        Validator.isNotEmpty(id_category);

        logger.log(Level.INFO, "Search all products corresponding to" +
                                "category " + id_category);

        List<Product> products = productRepository.findByCategoryId(id_category);

        List<ProductDTO> productDTOs = products.stream()
                                                .map(p -> ProductDTO.fromEntity(p))
                                                .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all products corresponding to" +
                                "category " + id_category);

        return productDTOs;
    }

    /**
     * Delete product with specified id
     * @param id id of product to be deleted
     * @throws InvalidDataException if product id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        productRepository.deleteById(id);

        logger.log(Level.INFO, "Product with id " + id + " successfully deleted");
    }

    public List<UnsoldProductDTO> unsoldForAtLeast(Duration duration) {

        LocalDateTime date = (LocalDateTime.now()).minus(duration);
        Instant instant = date.atZone(ZoneId.of("Europe/Bucharest")).toInstant();

        List<Product> products = productRepository.findByLastSaleBefore(Date.from(instant));

        List<UnsoldProductDTO> unsoldProductDTOs = products.stream()
                                                .map(p -> UnsoldProductDTO.fromEntity(p))
                                                .collect(Collectors.toList());

        return unsoldProductDTOs;
    }
}
