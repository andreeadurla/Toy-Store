package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.ShoppingCartDetailsDTO;
import ro.sd.a2.dto.ShoppingCartItemDTO;
import ro.sd.a2.dtoValidator.ShoppingCartItemDTOValidator;
import ro.sd.a2.entity.Product;
import ro.sd.a2.entity.ShoppingCartItem;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.ProductRepository;
import ro.sd.a2.repository.ShoppingCartRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private static final Logger logger = Logger.getLogger(ShoppingCartService.class.getName());

    @Autowired
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private final ProductRepository productRepository;

    /**
     * Add a new product into shopping cart. If product stock is sufficient then the item
     * is added into cart.
     * @param shoppingCartItemDTO data about new shopping cart item
     * @throws InvalidDataException if shopping cart item is invalid or if product stock is
     * insufficient.
     */
    public void add(ShoppingCartItemDTO shoppingCartItemDTO) {

        ShoppingCartItemDTOValidator.validate(shoppingCartItemDTO);

        String clientId = shoppingCartItemDTO.getClient_id();
        String productId = shoppingCartItemDTO.getProduct_id();
        int wantedQuantity = shoppingCartItemDTO.getQuantity();

        ShoppingCartItem shoppingCartItem = shoppingCartRepository.findByClient_idAndProduct_id(clientId, productId);
        Product wantedProduct = productRepository.getOne(shoppingCartItemDTO.getProduct_id());
        int existingQuantity = wantedProduct.getQuantity();

        if(ObjectUtils.isNotEmpty(shoppingCartItem)) {
            wantedQuantity += shoppingCartItem.getQuantity();
        }

        if(wantedQuantity > existingQuantity)
            throw new InvalidDataException(WrongMessage.INSUFFICIENT_STOCK);

        if(ObjectUtils.isNotEmpty(shoppingCartItem)) {
            shoppingCartItem.setQuantity(wantedQuantity);
            shoppingCartRepository.save(shoppingCartItem);

            logger.log(Level.INFO, "Product " + productId +
                    " already exists in shopping cart" +
                    "; update wanted quantity");
            return ;
        }

        shoppingCartRepository.save(shoppingCartItemDTO.toEntity());
        logger.log(Level.INFO, "Product " + productId + " successfully added in shopping cart");
    }

    /**
     * Find all shopping cart items of the client with specified id
     * @param id_user id of client to which the cart belong
     * @throws InvalidDataException if client id is invalid
     * @return a list of ShoppingCartDetailsDTO which contains necessary information about the found items
     */
    public List<ShoppingCartDetailsDTO> findByUserId(String id_user) {

        logger.log(Level.INFO, "Search all shopping cart items of user with id " + id_user);

        List<ShoppingCartItem> shopping_cart_items = shoppingCartRepository.findByClient_id(id_user);

        List<ShoppingCartDetailsDTO> shoppingCartDetailsDTOs =  shopping_cart_items.stream()
                                                                    .map(i -> ShoppingCartDetailsDTO.fromEntity(i))
                                                                    .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all shopping cart items of user with id " + id_user);

        return shoppingCartDetailsDTOs;
    }

    /**
     * Update quantity of shopping cart item with specified id
     * @param id id of shopping cart item to be edited
     * @param quantity new quantity
     * @throws InvalidDataException if shopping cart item id is invalid or if quantity is less than or equals to 0
     */
    public void updateQuantity(String id, int quantity) {

        Validator.isNotEmpty(id);

        if(quantity <= 0)
            throw new InvalidDataException(WrongMessage.INVALID_QUANTITY);

        ShoppingCartItem shoppingCartItem = shoppingCartRepository.getOne(id);

        if(ObjectUtils.isEmpty(shoppingCartItem)) {
            logger.log(Level.INFO, "Shopping cart item with id" + id + " not found");
            return ;
        }

        Product productWanted = productRepository.getOne(shoppingCartItem.getProductId());

        if(ObjectUtils.isEmpty(productWanted)) {
            logger.log(Level.INFO, "Product with id" + shoppingCartItem.getProductId() + " not found");
            return ;
        }

        int existingQuantity = productWanted.getQuantity();

        if(quantity > existingQuantity)
            throw new InvalidDataException(productWanted.getName() + " " + WrongMessage.INSUFFICIENT_STOCK);

        shoppingCartItem.setQuantity(quantity);
        shoppingCartRepository.save(shoppingCartItem);

        logger.log(Level.INFO, "Quantity of shopping cart item with id " + id +
                " successfully updated");
    }

    /**
     * Delete shopping cart item with specified id
     * @param id id of shopping cart item to be deleted
     * @throws InvalidDataException if shopping cart item id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        shoppingCartRepository.deleteById(id);

        logger.log(Level.INFO, "Shopping cart item with id " + id + " successfully deleted");
    }

}
